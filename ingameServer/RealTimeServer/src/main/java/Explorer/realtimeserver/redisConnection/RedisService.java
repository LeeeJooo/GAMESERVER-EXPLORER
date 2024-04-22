package Explorer.realtimeserver.redisConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /*
     * [데이터 저장]
     * key    connection.toString()
     * value  info
     */
    public void saveToRedis(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /*
     * [데이터 읽기]
     */
    public String readFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}