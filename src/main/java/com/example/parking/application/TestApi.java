package com.example.parking.application;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class TestApi {

    private final String apiKey = "/7a7250537070617238334472465257";
    private final String apiType = "/json";

    @GetMapping("/test")
    public void send() {
        String startIndex = "/1";
        String endIndex = "/1000";
        String url = "http://openapi.seoul.go.kr:8088" + apiKey + apiType + "/GetParkingInfo" + startIndex + endIndex;
        System.out.println("url = " + url);
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<FinalRes> results = restTemplate.getForEntity(url, FinalRes.class);
        System.out.println("results st = " + results.getStatusCode());
        final List<ApiResponse> responses = results.getBody().getGetParkingInfo().getRow();
        System.out.println("responses = " + responses);
        System.out.println("responses size = " + responses.size());
    }
}
