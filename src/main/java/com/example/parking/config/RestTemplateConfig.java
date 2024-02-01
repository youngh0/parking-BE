package com.example.parking.config;

import com.example.parking.application.coordinate.CoordinateErrorHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @Qualifier("coordinateRestTemplate")
    public RestTemplate coordinateRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .errorHandler(new CoordinateErrorHandler())
                .additionalInterceptors(List.of(coordinateRestTemplateInterceptor()))
                .build();
    }

    @Bean
    public ClientHttpRequestInterceptor coordinateRestTemplateInterceptor() {
        return new CoordinateRestTemplateInterceptor();
    }
}
