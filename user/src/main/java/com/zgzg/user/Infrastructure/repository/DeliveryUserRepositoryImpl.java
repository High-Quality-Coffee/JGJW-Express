package com.zgzg.user.Infrastructure.repository;

import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;
import com.zgzg.user.domain.model.DeliveryStatus;
import com.zgzg.user.domain.model.DeliveryType;
import com.zgzg.user.domain.model.DeliveryUser;
import com.zgzg.user.domain.model.RefreshToken;
import com.zgzg.user.domain.repository.DeliveryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryUserRepositoryImpl implements DeliveryUserRepository {
    private final JpaDeliveryUserRepository jpaDeliveryUserRepository;

    @Override
    @Transactional
    public Optional<DeliveryUser> save(DeliveryUser deliveryUser) {
        return Optional.of(jpaDeliveryUserRepository.save(deliveryUser));
    }

    @Override
    public Optional<DeliveryUser> findHubDeliveryUser(@Param("status") DeliveryStatus deliveryStatus){
        return jpaDeliveryUserRepository.findHubDeliveryUser(deliveryStatus);
    }

    @Override
    public Optional<DeliveryUser> findByDeliveryUserId(Long deliveryUserId) {
        return jpaDeliveryUserRepository.findByDeliveryUserId(deliveryUserId);
    }

    @Override
    public List<DeliveryUser> findAllByOrderByDeliveryOrderAsc(){
        return jpaDeliveryUserRepository.findAllByOrderByDeliveryOrderAsc();
    }

    public List<DeliveryUser> findAllByDeliveryTypeOrderByDeliveryOrderAsc(DeliveryType deliveryType){
        return jpaDeliveryUserRepository.findAllByDeliveryTypeOrderByDeliveryOrderAsc(deliveryType);
    }

    public List<DeliveryUser> findStoreDeliveryUser(@Param("hubId") UUID id, @Param("deliveryType") DeliveryType deliveryType){
        return jpaDeliveryUserRepository.findStoreDeliveryUser(id, deliveryType);
    }

    public List<DeliveryUser> findAll(){
        return jpaDeliveryUserRepository.findAll();
    }

    public DeliveryUser findById(Long id){
        return jpaDeliveryUserRepository.findById(id).orElseThrow(()->new BaseException(Code.MEMBER_NOT_EXISTS));
    }

}

