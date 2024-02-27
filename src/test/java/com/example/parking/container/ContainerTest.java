package com.example.parking.container;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ActiveProfiles("test")
public abstract class ContainerTest {

    private static final GenericContainer<?> REDIS_CONTAINER;
//    private static final MySQLContainer MYSQL_CONTAINER;

    static {
        REDIS_CONTAINER = new GenericContainer<>(DockerImageName.parse("redis:latest"))
                .withExposedPorts(6379);
        REDIS_CONTAINER.start();
        System.setProperty("spring.data.redis.host", REDIS_CONTAINER.getHost());
        System.setProperty("spring.data.redis.port", REDIS_CONTAINER.getMappedPort(6379).toString());
    }

    @Autowired
    private RedisDataCleaner redisDataCleaner;

    @Autowired
    private MySQLDataCleaner mySQLDataCleaner;

    @AfterEach
    void teardown() {
        redisDataCleaner.delete();
        mySQLDataCleaner.delete();
    }

//    @DynamicPropertySource
//    public static void overrideProps(DynamicPropertyRegistry registry) {
//        registry.add("redis.host", REDIS_CONTAINER::getHost);
//        registry.add("redis.port", () -> "" + REDIS_CONTAINER.getMappedPort(6379));
//    }
}
