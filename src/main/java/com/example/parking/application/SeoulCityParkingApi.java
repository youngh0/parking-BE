package com.example.parking.application;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Component
public class SeoulCityParkingApi {

    private final String apiKey = "/7a7250537070617238334472465257";
    private final String apiType = "/json";

    public SeoulCityParkingResponse call() {
        String startIndex = "/1";
        String endIndex = "/1000";
        String url = "http://openapi.seoul.go.kr:8088" + apiKey + apiType + "/GetParkingInfo" + startIndex + endIndex;

        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, SeoulCityParkingResponse.class);
    }
}
