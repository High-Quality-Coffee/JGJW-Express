package com.zgzg.hub.infrastructure.redis;

import com.zgzg.hub.application.route.dto.RedisRouteDTO;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisHashUtil {

  private final RedisTemplate<String, RedisRouteDTO> redisTemplate;

  public void save(String hashKey, Map<String, RedisRouteDTO> map) {
    redisTemplate.opsForHash().putAll(hashKey, map);
  }

  public Map<String, RedisRouteDTO> get(String hashKey) {
    HashOperations<String, String, RedisRouteDTO> hashOps = redisTemplate.opsForHash();
    return hashOps.entries(hashKey);
  }

  public void deleteKeysByPrefix(String prefix) {
    Set<String> keys = redisTemplate.keys(prefix + "*");
    if (keys != null && !keys.isEmpty()) {
      redisTemplate.delete(keys);
    }
  }
}
