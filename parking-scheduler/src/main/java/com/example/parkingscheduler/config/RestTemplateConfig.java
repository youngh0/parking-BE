package com.example.parkingscheduler.config;

import com.example.parkingscheduler.coordinate.CoordinateErrorHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private static final String AUTH_HEADER = "Authorization";

    @Bean
    @Qualifier("coordinateRestTemplate")
    public RestTemplate coordinateRestTemplate(RestTemplateBuilder restTemplateBuilder,
                                               @Value("${kakao.key}") String kakaoUrl) {
        System.out.println("test");
        return restTemplateBuilder
                .errorHandler(new CoordinateErrorHandler())
                .defaultHeader(AUTH_HEADER, kakaoUrl)
                .build();
    }
}
