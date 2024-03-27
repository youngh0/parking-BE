package com.example.parking.application.parking;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.parking.domain.parking.BaseInformation;
import com.example.parking.domain.parking.FilterCondition;
import com.example.parking.domain.parking.OperationType;
import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.ParkingFeeCalculator;
import com.example.parking.domain.parking.ParkingType;
import com.example.parking.domain.parking.PayType;
import com.example.parking.domain.parking.PayTypes;
import com.example.parking.domain.searchcondition.FeeType;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class ParkingDomainServiceTest {

    private final ParkingDomainService parkingDomainService = new ParkingDomainService(
            new ParkingFeeCalculator());

    @Test
    void 조회조건에_따라_주차장을_필터링한다1() {
        // given
        ParkingType parkingTypeCondition = ParkingType.MECHANICAL;
        OperationType operationTypeCondition = OperationType.PUBLIC;
        PayType wantPayType = PayType.CASH;

        Parking wantParking = Parking.builder()
                .baseInformation(new BaseInformation("name", "tell", "address",
                        PayTypes.from(List.of(wantPayType)),
                        parkingTypeCondition,
                        operationTypeCondition))
                .build();

        Parking notWantParking1 = Parking.builder()
                .baseInformation(new BaseInformation("name", "tell", "address",
                        PayTypes.DEFAULT,
                        parkingTypeCondition,
                        OperationType.NO_INFO))
                .build();

        Parking notWantParking2 = Parking.builder()
                .baseInformation(new BaseInformation("name", "tell", "address",
                        PayTypes.DEFAULT,
                        ParkingType.OFF_STREET,
                        operationTypeCondition))
                .build();

        // when
        FilterCondition filterCondition = new FilterCondition(
                List.of(operationTypeCondition),
                List.of(parkingTypeCondition),
                List.of(wantPayType),
                FeeType.PAID);

        List<Parking> filterList = parkingDomainService.filterByCondition(
                List.of(wantParking, notWantParking1, notWantParking2),
                filterCondition,
                3,
                LocalDateTime.now()
        );

        // then
        assertThat(filterList).hasSize(1);
    }

    @Test
    void 조회조건에_따라_주차장을_필터링한다2() {
        // given
        ParkingType wantParkingTypeCondition = ParkingType.ON_STREET;
        OperationType wantOperationTypeCondition = OperationType.PUBLIC;
        PayType wantPayType = PayType.CARD;

        Parking wantParking = Parking.builder()
                .baseInformation(new BaseInformation("name", "tel", "address",
                        PayTypes.from(List.of(wantPayType)),
                        wantParkingTypeCondition,
                        wantOperationTypeCondition))
                .build();

        Parking notWantParking1 = Parking.builder()
                .baseInformation(new BaseInformation("name", "tel", "address",
                        PayTypes.DEFAULT,
                        ParkingType.MECHANICAL,
                        wantOperationTypeCondition))
                .build();

        Parking notWantParking2 = Parking.builder()
                .baseInformation(new BaseInformation("name", "tel", "address",
                        PayTypes.DEFAULT,
                        ParkingType.NO_INFO,
                        wantOperationTypeCondition))
                .build();

        // when
        FilterCondition filterCondition = new FilterCondition(
                List.of(wantOperationTypeCondition),
                List.of(wantParkingTypeCondition),
                List.of(wantPayType),
                FeeType.PAID);

        List<Parking> result = parkingDomainService.filterByCondition(
                List.of(wantParking, notWantParking1, notWantParking2),
                filterCondition,
                3,
                LocalDateTime.now()
        );

        // then
        assertThat(result).hasSize(1);
    }
}
