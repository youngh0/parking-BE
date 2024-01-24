package com.example.parking.application.coordinate;

import com.example.parking.application.coordinate.dto.CoordinateResponse;
import com.example.parking.application.coordinate.dto.CoordinateResponse.Document;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CoordinateService {

    private static final String KAKAO_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    @Value("${kakao.key}")
    private String kakaoAuthHeader;
    private final RestTemplate restTemplate;
    private final Coordinate INVALID_COORDINATE = new Coordinate(0, 0);

    @Autowired
    public CoordinateService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .errorHandler(new CoordinateErrorHandler())
                .build();
    }

    public Coordinate extractCoordinateByAddress(String address) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", kakaoAuthHeader);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(KAKAO_URL)
                .queryParam("query", address)
                .build();

        ResponseEntity<CoordinateResponse> result = restTemplate.getForEntity(
                uriComponents.toString(),
                CoordinateResponse.class,
                entity
        );

        Integer matchingDataCount = result.getBody().getMeta().getTotalCount();
        if (matchingDataCount == 0) {
            return INVALID_COORDINATE;
        }

        List<Document> documents = result.getBody().getDocuments();
        Document document = documents.get(0);
        return new Coordinate(document.getX(), document.getY());
    }
}
