package com.example.parking.container;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class ContainerTest {

    private static final RedisContainer REDIS_CONTAINER;
    private static RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

    static {
        REDIS_CONTAINER = new RedisContainer(RedisContainer.DEFAULT_IMAGE_NAME.withTag(RedisContainer.DEFAULT_TAG))
                .withExposedPorts(6379);
        REDIS_CONTAINER.start();
        System.setProperty("spring.data.redis.host", REDIS_CONTAINER.getHost());
        System.setProperty("spring.data.redis.port", String.valueOf(REDIS_CONTAINER.getRedisPort()));
    }

    @Autowired
    private MySQLDataCleaner mySQLDataCleaner;

    @BeforeAll
    static void setUpBeforeClass() {
        String redisHost = REDIS_CONTAINER.getHost();
        Integer redisPort = REDIS_CONTAINER.getFirstMappedPort();

        LettuceConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(redisHost, redisPort);
        redisConnectionFactory.afterPropertiesSet();

        redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
    }

    @AfterEach
    void teardown() {
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
        mySQLDataCleaner.delete();
    }

    @AfterAll
    static void containerDown() {
        REDIS_CONTAINER.stop();
    }
}
