package com.example.parking.application;

import com.example.parking.application.PusanPublicParkingResponse.ParkingInfo.Item;
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
import org.springframework.stereotype.Component;

@Component
public class PusanPublicParkingAdapter implements ParkingAdapter {

    private static final String EMPTY = "-";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final String FREE = "무료";

    private final PusanPublicParkingApi api;

    public PusanPublicParkingAdapter(final PusanPublicParkingApi api) {
        this.api = api;
    }

    @Override
    public List<Parking> convert() {
        final List<Item> items = api.call().getGetParkingInfoDetails()
                .getBody().getItems().getItem();

        return items.stream().map(this::toParking)
                .toList();
    }

    private Parking toParking(final Item response) {
        return new Parking(getBaseInformation(response),
                getType(response),
                getLocation(response),
                getSpace(response),
                getOperatingTime(response),
                getFeePolicy(response),
                getFreePolicy(response)
        );
    }

    private BaseInformation getBaseInformation(final Item response) {
        return new BaseInformation(response.getTelephoneNumber(),
                filterAddress(response), response.getParkingName(), null);
    }

    private String filterAddress(Item response) {
        if (response.getOldAddress().equals(EMPTY)) {
            return response.getNewAddress();
        }
        return response.getOldAddress();
    }

    private Type getType(final Item response) {
        return Type.find(response.getParkingTypeNM());
    }

    private Location getLocation(final Item response) {
        return new Location(response.getLongitude(), response.getLatitude());
    }

    private Space getSpace(final Item response) {
        return new Space(parsingBoolean(response.getCurParking()), parsingInteger(response.getCapacity()),
                parsingInteger(response.getCurParking()));
    }

    private Integer parsingInteger(String target) {
        if (target.equals(EMPTY)) {
            return -1;
        }
        return Integer.parseInt(target);
    }

    private Boolean parsingBoolean(String target) {
        if (target.equals(EMPTY)) {
            return false;
        }
        return true;
    }

    private OperatingTime getOperatingTime(final Item response) {
        return new OperatingTime(
                new TimeInfo(parsingOperationTime(response.getWeekdayBeginTime()),
                        parsingOperationTime(response.getWeekdayEndTime())),
                new TimeInfo(parsingOperationTime(response.getWeekendBeginTime()),
                        parsingOperationTime(response.getWeekendEndTime())),
                new TimeInfo(parsingOperationTime(response.getHolidayBeginTime()),
                        parsingOperationTime(response.getHolidayEndTime()))
        );
    }

    private LocalTime parsingOperationTime(String time) {
        if (time.equals(EMPTY)) {
            return null;
        }
        return LocalTime.parse(time, TIME_FORMATTER);
    }

    private FeePolicy getFeePolicy(final Item response) {
        return new FeePolicy(Fee.from(parsingInteger(response.getRates())),
                Fee.from(parsingInteger(response.getAddRates())),
                TimeUnit.from(parsingInteger(response.getTimeRate())),
                TimeUnit.from(parsingInteger(response.getAddTimeRate())),
                Fee.from(parsingInteger(response.getDayMaximum())));
    }

    private FreePolicy getFreePolicy(final Item response) {
        final Boolean isFree = filterIsFree(response);
        return new FreePolicy(isFree, isFree, isFree);
    }

    private Boolean filterIsFree(Item response) {
        if (response.getPayNM().equals(FREE)) {
            return true;
        }
        return false;
    }
}
