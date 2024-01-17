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
import org.springframework.stereotype.Component;

@Component
public class SeoulCityParkingAdapter implements ParkingAdapter{

    private static final Set<String> TIMED_PARKING_RULES = Set.of("1", "3", "5");
    private static final String STREET_PARKING_TYPE = "노상 주차장";
    private static final String FREE = "무료";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");

    private final SeoulCityParkingApi api;

    public SeoulCityParkingAdapter(final SeoulCityParkingApi api) {
        this.api = api;
    }

    @Override
    public List<Parking> convert() {
        final List<SeoulCityParking> rows = api.call()
                .getParkingInfo()
                .getRows();

        final List<SeoulCityParking> seoulCityParkingLots = calculateCapacity(filterByOperation(rows));

        return seoulCityParkingLots.stream()
                .map(this::toParking)
                .collect(Collectors.toList());
    }

    private Parking toParking(final SeoulCityParking seoulCityParking) {
        return new Parking(getBaseInformation(seoulCityParking), getType(seoulCityParking),
                getLocation(seoulCityParking), getSpace(seoulCityParking), getOperatingTime(seoulCityParking),
                getFeePolicy(seoulCityParking), getFreePolicy(seoulCityParking));
    }

    private BaseInformation getBaseInformation(final SeoulCityParking seoulCityParking) {
        return new BaseInformation(seoulCityParking.getTel(), seoulCityParking.getAddr(),
                seoulCityParking.getParkingName(),
                seoulCityParking.getParkingCode());
    }

    private Type getType(final SeoulCityParking seoulCityParking) {
        return Type.find(seoulCityParking.getParkingTypeNM());
    }

    private Location getLocation(final SeoulCityParking seoulCityParking) {
        return new Location(seoulCityParking.getLng(), seoulCityParking.getLat());
    }

    private Space getSpace(final SeoulCityParking seoulCityParking) {
        return new Space(seoulCityParking.getQueStatus() == 1, seoulCityParking.getCapacity(),
                seoulCityParking.getCurParking());
    }

    private OperatingTime getOperatingTime(final SeoulCityParking seoulCityParking) {
        return new OperatingTime(
                new TimeInfo(parsingOperationTime(seoulCityParking.getWeekdayBeginTime()),
                        parsingOperationTime(seoulCityParking.getWeekdayEndTime())),
                new TimeInfo(parsingOperationTime(seoulCityParking.getWeekendBeginTime()),
                        parsingOperationTime(seoulCityParking.getWeekendEndTime())),
                new TimeInfo(parsingOperationTime(seoulCityParking.getHolidayBeginTime()),
                        parsingOperationTime(seoulCityParking.getHolidayEndTime()))
        );
    }

    private LocalTime parsingOperationTime(String time) {
        return LocalTime.parse(time, TIME_FORMATTER);
    }

    private FeePolicy getFeePolicy(final SeoulCityParking seoulCityParking) {
        return new FeePolicy(Fee.from(seoulCityParking.getRates()),
                Fee.from(seoulCityParking.getAddRates()),
                TimeUnit.from(seoulCityParking.getTimeRate()), TimeUnit.from(seoulCityParking.getAddTimeRate()),
                Fee.from(seoulCityParking.getDayMaximum()));
    }

    private FreePolicy getFreePolicy(final SeoulCityParking seoulCityParking) {
        return new FreePolicy(seoulCityParking.getPayNM().equals(FREE),
                seoulCityParking.getSaturdayPayNM().equals(FREE), seoulCityParking.getHolidayPayNM().equals(FREE));
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
}
