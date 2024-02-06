package com.example.parking.application.parking;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.parking.domain.parking.BaseInformation;
import com.example.parking.domain.parking.OperationType;
import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.ParkingType;
import com.example.parking.domain.parking.PayTypes;
import com.example.parking.domain.parking.dto.ParkingQueryCondition;
import java.util.List;
import org.junit.jupiter.api.Test;

class ParkingDomainServiceTest {

    private final ParkingDomainService parkingDomainService = new ParkingDomainService();

    @Test
    void 조회조건에_따라_주차장을_필터링한다1() {
        // given
        ParkingType parkingTypeCondition = ParkingType.MECHANICAL;
        OperationType operationTypeCondition = OperationType.PUBLIC;

        Parking wantParking = Parking.builder()
                .baseInformation(new BaseInformation("name", "tell", "address",
                        PayTypes.DEFAULT,
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
        List<Parking> filterList = parkingDomainService.filterByCondition(
                List.of(wantParking, notWantParking1, notWantParking2),
                new ParkingQueryCondition(OperationType.PUBLIC, ParkingType.OFF_STREET, false)
        );

        // then
        assertThat(filterList).hasSize(1);
    }

    @Test
    void 조회조건에_따라_주차장을_필터링한다2() {
        // given
        ParkingType parkingTypeCondition = ParkingType.ON_STREET;
        OperationType operationTypeCondition = OperationType.PUBLIC;

        Parking wantParking = Parking.builder()
                .baseInformation(new BaseInformation("name", "tel", "address",
                        PayTypes.DEFAULT,
                        parkingTypeCondition,
                        operationTypeCondition))
                .build();

        Parking notWantParking1 = Parking.builder()
                .baseInformation(new BaseInformation("name", "tel", "address",
                        PayTypes.DEFAULT,
                        ParkingType.MECHANICAL,
                        operationTypeCondition))
                .build();

        Parking notWantParking2 = Parking.builder()
                .baseInformation(new BaseInformation("name", "tel", "address",
                        PayTypes.DEFAULT,
                        ParkingType.NO_INFO,
                        operationTypeCondition))
                .build();

        // when
        List<Parking> result = parkingDomainService.filterByCondition(
                List.of(wantParking, notWantParking1, notWantParking2),
                new ParkingQueryCondition(operationTypeCondition, parkingTypeCondition, false));

        // then
        assertThat(result).hasSize(1);
    }
}
