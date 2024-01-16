package com.example.parking.application.seoul;

import com.example.parking.application.dto.SeoulFinalResponse;
import com.example.parking.application.seoul.dto.SeoulCityParkingResponse;
import com.example.parking.application.seoul.dto.SeoulCityParkingResponse.ParkingInfo.SeoulCityParking;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

// 외부 api 호출 이후 각 api에 맞는 형태의 DTO로 파싱한다
@Component
public class SeoulParkingConnector {

    private final String apiKey = "/7a7250537070617238334472465257";
    private final String apiType = "/json";


    public List<SeoulCityParking> connect() {
        String startIndex = "/1";
        String endIndex = "/1000";
        String url = "http://openapi.seoul.go.kr:8088" + apiKey + apiType + "/GetParkingInfo" + startIndex + endIndex;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SeoulCityParkingResponse> response = restTemplate.getForEntity(url,
                SeoulCityParkingResponse.class);
        return response.getBody().getParkingInfo().getRows();
    }

    private SeoulFinalResponse toSeoulFinalResponse(List<SeoulCityParking> seoulCityParkingLots) {
        return new SeoulFinalResponse("seoul", seoulCityParkingLots);
    }
}
