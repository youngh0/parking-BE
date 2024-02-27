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

    static {
        REDIS_CONTAINER = new GenericContainer<>(DockerImageName.parse("redis:latest"))
                .withExposedPorts(6379);
        REDIS_CONTAINER.start();
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
}
