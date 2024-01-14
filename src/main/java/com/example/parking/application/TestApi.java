package com.example.parking.application;

import com.example.parking.application.SeoulCityParkingResponse.ParkingInfo.SeoulCityParking;
import com.example.parking.domain.parking.BaseInformation;
import com.example.parking.domain.parking.Fee;
import com.example.parking.domain.parking.FeePolicy;
import com.example.parking.domain.parking.FreePolicy;
import com.example.parking.domain.parking.Location;
import com.example.parking.domain.parking.OperatingTime;
import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.Space;
import com.example.parking.domain.parking.TimeInfo;
import com.example.parking.domain.parking.TimeUnit;
import com.example.parking.domain.parking.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class TestApi {

    private static final Set<String> TIMED_PARKING_RULES = Set.of("1", "3", "5");
    private static final String STREET_PARKING_TYPE = "노상 주차장";
    private static final String FREE = "무료";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");

    private final String apiKey = "/7a7250537070617238334472465257";
    private final String apiType = "/json";

    @GetMapping("/test")
    public void send() {
        String startIndex = "/1";
        String endIndex = "/1000";
        String url = "http://openapi.seoul.go.kr:8088" + apiKey + apiType + "/GetParkingInfo" + startIndex + endIndex;

        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<SeoulCityParkingResponse> response = restTemplate.getForEntity(url,
                SeoulCityParkingResponse.class);
        final List<SeoulCityParking> rows = response.getBody().getParkingInfo().getRows();

        final List<SeoulCityParking> seoulCityParkingLots = calculateCapacity(filterByOperation(rows));

        final SeoulCityParking seoulCityParking = seoulCityParkingLots.get(0);

        BaseInformation baseInformation = new BaseInformation(seoulCityParking.getTel(), seoulCityParking.getAddr(),
                seoulCityParking.getParkingName(),
                seoulCityParking.getParkingCode());

        Type type = Type.find(seoulCityParking.getParkingTypeNM());
        Location location = new Location(seoulCityParking.getLng(), seoulCityParking.getLat());

        final Space space = new Space(seoulCityParking.getQueStatus() == 1, seoulCityParking.getCapacity(),
                seoulCityParking.getCurParking());

        final OperatingTime operatingTime = new OperatingTime(
                new TimeInfo(parsingOperationTime(seoulCityParking.getWeekdayBeginTime()),
                        parsingOperationTime(seoulCityParking.getWeekdayEndTime())),
                new TimeInfo(parsingOperationTime(seoulCityParking.getWeekendBeginTime()),
                        parsingOperationTime(seoulCityParking.getWeekendEndTime())),
                new TimeInfo(parsingOperationTime(seoulCityParking.getHolidayBeginTime()),
                        parsingOperationTime(seoulCityParking.getHolidayEndTime()))

        );
        final FreePolicy freePolicy = new FreePolicy(seoulCityParking.getPayNM().equals(FREE),
                seoulCityParking.getSaturdayPayNM().equals(FREE), seoulCityParking.getHolidayPayNM().equals(FREE));

        final FeePolicy feePolicy = new FeePolicy(Fee.from(seoulCityParking.getRates()),
                Fee.from(seoulCityParking.getAddRates()),
                TimeUnit.from(seoulCityParking.getTimeRate()), TimeUnit.from(seoulCityParking.getAddTimeRate()),
                Fee.from(seoulCityParking.getDayMaximum()));

        final Parking parking = new Parking(baseInformation, type, location, space, operatingTime, feePolicy,
                freePolicy);

        System.out.println("parking = " + parking);
    }

    private List<SeoulCityParking> filterByOperation(final List<SeoulCityParking> rows) {
        return rows.stream()
                .filter(result -> TIMED_PARKING_RULES.contains(result.getOperationRule()))
                .toList();
    }

    private List<SeoulCityParking> calculateCapacity(final List<SeoulCityParking> results) {
        final Map<String, List<SeoulCityParking>> collect = results.stream()
                .collect(Collectors.groupingBy(a -> a.getParkingCode()));

        return collect.values().stream().map(parkingLots -> {
            final SeoulCityParking parking = parkingLots.get(0);
            if (parking.getParkingTypeNM().equals(STREET_PARKING_TYPE)) {
                parking.setCapacity(parkingLots.size());
                return parking;
            }
            return parking;
        }).toList();
    }

    private LocalTime parsingOperationTime(String time) {
        return LocalTime.parse(time, TIME_FORMATTER);
    }

    /**
     * todo
     * 1. 노상 주차장의 경우
     * capacity 다 더해서 구해야함
     *
     * 2. OPERATION_RULE이 1이 아닌 경우
     * 버릴 것 (버스전용 주차장)
     *
     * SeoulCityParkingResponse -> Parking 객체로 변환
     */
}
