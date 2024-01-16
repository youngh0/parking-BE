package com.example.parking.application.busan;

import static com.example.parking.domain.parking.FreeType.NO_INFO;

import com.example.parking.application.busan.dto.BusanParkingResponse.BusanParkingInfo.Body.Items.BusanParkingData;
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
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BusanParkingConverter {

    public List<Parking> convert(List<BusanParkingData> responses) {
        return responses.stream()
                .map(this::toParking)
                .toList();
    }

    private Parking toParking(BusanParkingData busanParkingData) {
        BaseInformation baseInformation = new BaseInformation(
                busanParkingData.getTponNum(),
                busanParkingData.getJibunAddr(),
                busanParkingData.getPkNam(),
                busanParkingData.getMgntNum());

        Type type = Type.find(busanParkingData.getPkFm());
        Location location = new Location(busanParkingData.getYCdnt(), busanParkingData.getXCdnt());

        boolean isProvidingCurParkingLots = !busanParkingData.getCurrava().equals("-");
        int curParking = 0;
        if (isProvidingCurParkingLots) {
            curParking = Integer.parseInt(busanParkingData.getCurrava());
        }
        Space space = new Space(
                isProvidingCurParkingLots,
                busanParkingData.getPkCnt(),
                curParking);

        OperatingTime operatingTime = new OperatingTime(
                new TimeInfo(LocalTime.parse(busanParkingData.getSvcSrtTe()),
                        LocalTime.parse(busanParkingData.getSvcEndTe())),
                new TimeInfo(LocalTime.parse(busanParkingData.getSatSrtTe()),
                        LocalTime.parse(busanParkingData.getSatEndTe())),
                new TimeInfo(LocalTime.parse(busanParkingData.getHldSrtTe()),
                        LocalTime.parse(busanParkingData.getHldEndTe()))
        );

        FreePolicy freePolicy = new FreePolicy(
                NO_INFO,
                NO_INFO,
                NO_INFO
        );

        FeePolicy feePolicy = new FeePolicy(
                Fee.from(0),
                Fee.from(busanParkingData.getFeeAdd()),
                TimeUnit.from(0),
                TimeUnit.from(busanParkingData.getPkAddTime()),
                Fee.from(0)
        );

        return new Parking(baseInformation, type, location, space, operatingTime, feePolicy, freePolicy);
    }
}
