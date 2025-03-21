package com.zgzg.user.application.service;

import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;
import com.zgzg.user.application.dto.DeliveryUserResponseDTO;
import com.zgzg.user.domain.model.DeliveryStatus;
import com.zgzg.user.domain.model.DeliveryType;
import com.zgzg.user.domain.model.DeliveryUser;
import com.zgzg.user.domain.repository.DeliveryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryUserRepository deliveryUserRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisCacheManager cacheManager;

    private final String HUB_DELIVERY_CACHE_NAME = "hubDeliveryUserQueue";
    private final String HUB_DELIVERY_CACHE_KEY = "hubDeliveryUser";
    private final int TOTAL_DELIVERY_USERS = 10; //허브 담당자 전체 수
    private static final String LAST_ASSIGNED_KEY = "hub:delivery:lastAssigned"; // 직전 담당자 ID 저장용

    //배송담당자에 변경이 있을 경우나, 배송담당자 명단 업데이트가 필요한 경우 큐 초기화 (필요 시 호출)
    public void HubDeliveryUserInitializeQueue() {
        Long size = redisTemplate.opsForList().size(HUB_DELIVERY_CACHE_KEY);
        if (size == null || size == 0) {
            List<DeliveryUser> allUsers = deliveryUserRepository.findAllByDeliveryTypeOrderByDeliveryOrderAsc(DeliveryType.HUB_DELIVERY);
            if (allUsers.size() != TOTAL_DELIVERY_USERS) {
                throw new BaseException(Code.INTERNAL_SERVER_ERROR, "담당자 수가 예상과 다릅니다.");
            }
            for (DeliveryUser user : allUsers) {
                redisTemplate.opsForList().rightPush(HUB_DELIVERY_CACHE_KEY, user.getDeliveryUserId().toString());
//                user.setDeliveryStatus(DeliveryStatus.CAN_DELIVER);
            }
            //deliveryUserRepository.saveAll(allUsers);
        }
    }

    @Transactional
    public DeliveryUserResponseDTO updateHubDeliveryUser() {
        // 1. 직전에 할당된 담당자 상태를 배송가능으로 변경
        String lastAssignedUserIdStr = redisTemplate.opsForValue().get(LAST_ASSIGNED_KEY);
        if (lastAssignedUserIdStr != null) {
            Long lastAssignedUserId = Long.parseLong(lastAssignedUserIdStr);
            DeliveryUser previousUser = deliveryUserRepository.findByDeliveryUserId(lastAssignedUserId)
                    .orElseThrow(() -> new BaseException(Code.MEMBER_NOT_EXISTS));
            previousUser.setDeliveryStatus(DeliveryStatus.CAN_DELIVER);
            deliveryUserRepository.save(previousUser);
        }

        // 2. 큐에서 배송 가능한 담당자 찾기
        DeliveryUser newUser = null;
        String newUserIdStr = null;
        Long queueSize = redisTemplate.opsForList().size(HUB_DELIVERY_CACHE_KEY);
        //큐에 담당자들이 안들어가 있으면 허브배송담당자들 넣어주기
        if (queueSize == null || queueSize == 0) {
            HubDeliveryUserInitializeQueue();
        }

        for (int i = 0; i < queueSize; i++) {
            newUserIdStr = redisTemplate.opsForList().leftPop(HUB_DELIVERY_CACHE_KEY);
            if (newUserIdStr == null) break;

            Long newUserId = Long.parseLong(newUserIdStr);
            newUser = deliveryUserRepository.findByDeliveryUserId(newUserId)
                    .orElseThrow(() -> new BaseException(Code.DELIVERY_USER_NOT_ASSIGN));

            if (newUser.getDeliveryStatus() == DeliveryStatus.CAN_DELIVER) {
                // 상태가 CAN_DELIVER이면 할당 가능
                break;
            }
            // CAN_DELIVER가 아니면 다시 큐에 추가
            redisTemplate.opsForList().rightPush(HUB_DELIVERY_CACHE_KEY, newUserIdStr);
            newUser = null; // 다음 담당자를 찾기 위해 초기화
        }

        if (newUser == null) {
            throw new BaseException(Code.DELIVERY_USER_NOT_ASSIGN, "할당 가능한 배송 담당자가 없습니다.");
        }

        // 3. 새 담당자 상태 변경 및 저장
        newUser.setDeliveryStatus(DeliveryStatus.ASSIGNED);
        deliveryUserRepository.save(newUser);

        // 4. 상태 변경 전 DTO 생성
        DeliveryUserResponseDTO response = toDeliveryUserDTO(newUser);

        // 5. 새 담당자를 큐 맨 뒤로 추가
        redisTemplate.opsForList().rightPush(HUB_DELIVERY_CACHE_KEY, newUserIdStr);

        // 6. 직전 담당자로 기록
        redisTemplate.opsForValue().set(LAST_ASSIGNED_KEY, newUserIdStr);

        return response;


    }




    //캐시된 이전 허브배송담당자의 상태를 배송가능으로 변경
    @Transactional
    public void updateCachedDeliveryUserStatus(DeliveryUser deliveryUser){
        deliveryUser.setDeliveryStatus(DeliveryStatus.CAN_DELIVER);
        deliveryUserRepository.save(deliveryUser);
    }

    // 캐시에서 현재 담당자 조회
    public DeliveryUser getCachedHubDeliveryUser(Long deliveryUserId) {
        return deliveryUserRepository.findByDeliveryUserId(deliveryUserId).orElseThrow(()->new BaseException(Code.MEMBER_NOT_EXISTS));
    }

    public DeliveryUserResponseDTO toDeliveryUserDTO(DeliveryUser deliveryUser){
        return DeliveryUserResponseDTO.builder()
                .deliveryUserId(deliveryUser.getDeliveryUserId())
                .hubId(deliveryUser.getHubId())
                .deliverySlackUsername(deliveryUser.getDeliverySlackUsername())
                .deliveryType(deliveryUser.getDeliveryType())
                .deliveryOrder(deliveryUser.getDeliveryOrder())
                .deliveryStatus(deliveryUser.getDeliveryStatus())
                .build();
    }


}
