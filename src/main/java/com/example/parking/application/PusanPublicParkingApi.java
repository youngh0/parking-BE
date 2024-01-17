package com.example.parking.application;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PusanPublicParkingApi {

    private final String url =
            "http://apis.data.go.kr/6260000/BusanPblcPrkngInfoService/getPblcPrkngInfo?ServiceKey=ec3Qt4%2FbCRaddaK2FMoeVpVW2SqG%2B2cdn4Xdf1LpWru4O8opXbJc0wbbSdjXhQykcw2S8HmDuA%2BW3GiFFGDPgQ%3D%3D\n"
                    + "&pageNo=1&numOfRows=1000&resultType=json";

    public PusanPublicParkingResponse call() {
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, PusanPublicParkingResponse.class);
    }
}
