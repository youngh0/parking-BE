package com.example.parking.external.parkingapi.seoul;

import com.example.parking.domain.parking.Parking;
import com.example.parking.external.parkingapi.seoul.SeoulParkingResponse.ParkingInfo.SeoulParking;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class SeoulParkingApiService {

    private final RestTemplate restTemplate;
    private final String url = "http://openapi.seoul.go.kr:8088";
    private final String apiKey = "/7a7250537070617238334472465257";
    private final String contentType = "/json";
    private final String path = "/GetParkingInfo";
    private final int pageSize = 2;
    private final int chunkSize = 1000;

    public SeoulParkingApiService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Parking> read() {
        List<SeoulParking> rows = call();
        return SeoulParkingMapper.mapToParking(rows);
    }

    private List<SeoulParking> call() {
        List<SeoulParking> rows = new ArrayList<>();
        for (int startIdx = 1, page = 1; page <= pageSize; startIdx += chunkSize, page++) {
            ResponseEntity<SeoulParkingResponse> response = restTemplate.getForEntity(getRequestUrl(startIdx, startIdx + chunkSize - 1), SeoulParkingResponse.class);
            rows.addAll(response.getBody().getParkingInfo().getRows());
        }
        return rows;
    }

    private String getRequestUrl(int startIdx, int endIdx) {
        return url + apiKey + contentType + path + "/" + startIdx + "/" + endIdx;
    }
}
