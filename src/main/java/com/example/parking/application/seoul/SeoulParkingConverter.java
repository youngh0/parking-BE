package com.example.parking.application.seoul;

import com.example.parking.application.seoul.dto.SeoulCityParkingResponse.ParkingInfo.SeoulCityParking;
import com.example.parking.domain.parking.BaseInformation;
import com.example.parking.domain.parking.Fee;
import com.example.parking.domain.parking.FeePolicy;
import com.example.parking.domain.parking.FreePolicy;
import com.example.parking.domain.parking.FreeType;
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
import org.springframework.stereotype.Component;

@Component
public class SeoulParkingConverter {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
    private static final String FREE = "무료";

    public List<Parking> convert(List<SeoulCityParking> responses) {
        return responses.stream()
                .map(this::toParking)
                .toList();
    }

    private Parking toParking(SeoulCityParking seoulCityParking) {
        BaseInformation baseInformation = new BaseInformation(
                seoulCityParking.getTel(),
                seoulCityParking.getAddr(),
                seoulCityParking.getParkingName(),
                seoulCityParking.getParkingCode());

        Type type = Type.find(seoulCityParking.getParkingTypeNM());
        Location location = new Location(seoulCityParking.getLng(), seoulCityParking.getLat());

        Space space = new Space(seoulCityParking.getQueStatus() == 1, seoulCityParking.getCapacity(),
                seoulCityParking.getCurParking());

        OperatingTime operatingTime = new OperatingTime(
                new TimeInfo(parsingOperationTime(seoulCityParking.getWeekdayBeginTime()),
                        parsingOperationTime(seoulCityParking.getWeekdayEndTime())),
                new TimeInfo(parsingOperationTime(seoulCityParking.getWeekendBeginTime()),
                        parsingOperationTime(seoulCityParking.getWeekendEndTime())),
                new TimeInfo(parsingOperationTime(seoulCityParking.getHolidayBeginTime()),
                        parsingOperationTime(seoulCityParking.getHolidayEndTime()))

        );
        FreePolicy freePolicy = new FreePolicy(
                FreeType.find(seoulCityParking.getPayNM()),
                FreeType.find(seoulCityParking.getSaturdayPayNM()),
                FreeType.find(seoulCityParking.getHolidayPayNM())
        );

        FeePolicy feePolicy = new FeePolicy(
                Fee.from(seoulCityParking.getRates()),
                Fee.from(seoulCityParking.getAddRates()),
                TimeUnit.from(seoulCityParking.getTimeRate()),
                TimeUnit.from(seoulCityParking.getAddTimeRate()),
                Fee.from(seoulCityParking.getDayMaximum())
        );

        return new Parking(baseInformation, type, location, space, operatingTime, feePolicy, freePolicy);
    }

    private LocalTime parsingOperationTime(String time) {
        return LocalTime.parse(time, TIME_FORMATTER);
    }
}
