package org.jagadeesh.springsecuritypractise.service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisDemoService {

    private final RedisTemplate<String, Object> redis;

    public RedisDemoService(RedisTemplate<String, Object> redis) {
        this.redis = redis;
    }

    // String value with TTL
    public void saveOtp(String phone, String otp) {
        redis.opsForValue().set("otp:" + phone, otp, Duration.ofMinutes(2));
    }

    public String getOtp(String phone) {
        return (String) redis.opsForValue().get("otp:" + phone);
    }

    // Counter (atomic), e.g. rate limiting
    public Long incrementHits(String ip) {
        Long count = redis.opsForValue().increment("hits:" + ip);
        if (count != null && count == 1) {
            redis.expire("hits:" + ip, Duration.ofMinutes(1));
        }
        return count;
    }

    // Hash
    public void saveUserField(String userId, String field, Object value) {
        redis.opsForHash().put("user:" + userId, field, value);
    }

    // Delete / exists
    public void deleteOtp(String phone) { redis.delete("otp:" + phone); }
    public boolean hasOtp(String phone) { return Boolean.TRUE.equals(redis.hasKey("otp:" + phone)); }
}