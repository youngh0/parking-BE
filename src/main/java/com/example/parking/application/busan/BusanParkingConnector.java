package com.example.parking.application.busan;

import com.example.parking.application.busan.dto.BusanParkingResponse;
import com.example.parking.application.busan.dto.BusanParkingResponse.BusanParkingInfo.Body.Items.BusanParkingData;
import com.example.parking.application.dto.SeoulFinalResponse;
import com.example.parking.application.seoul.dto.SeoulCityParkingResponse.ParkingInfo.SeoulCityParking;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BusanParkingConnector {

    private final String apiKey = "ec3Qt4%2FbCRaddaK2FMoeVpVW2SqG%2B2cdn4Xdf1LpWru4O8opXbJc0wbbSdjXhQykcw2S8HmDuA%2BW3GiFFGDPgQ%3D%3D";
    private final String apiType = "/json";

    public List<BusanParkingData> connect() {
        String startIndex = "1";
        String rowCount = "10";
//        String url = "http://openapi.seoul.go.kr:8088" + apiKey + apiType + "/GetParkingInfo" + startIndex + endIndex;

        StringBuilder urlBuilder = new StringBuilder(
                "http://apis.data.go.kr/6260000/BusanPblcPrkngInfoService/getPblcPrkngInfo"); /*URL*/
        urlBuilder.append("?ServiceKey=").append(apiKey);
        urlBuilder.append("&pageNo=").append(startIndex);
        urlBuilder.append("&numOfRows=").append(rowCount);
        urlBuilder.append("&resultType=json");
        System.out.println(urlBuilder.toString());
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<BusanParkingResponse> response = restTemplate.getForEntity(urlBuilder.toString(),
                BusanParkingResponse.class);
        System.out.println(response.getStatusCode());
        return response.getBody().getBusanParkingInfo().getBody().getItems().getItem();
    }

    private SeoulFinalResponse toSeoulFinalResponse(List<SeoulCityParking> seoulCityParkingLots) {
        return new SeoulFinalResponse("seoul", seoulCityParkingLots);
    }
}
