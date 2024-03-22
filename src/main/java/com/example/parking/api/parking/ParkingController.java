package com.example.parking.api.parking;

import com.example.parking.application.parking.ParkingService;
import com.example.parking.application.parking.dto.ParkingLotsResponse;
import com.example.parking.application.parking.dto.ParkingSearchConditionRequest;
import com.example.parking.config.argumentresolver.parking.ParkingSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ParkingController {

    private final ParkingService parkingService;

    @GetMapping("/parkings")
    public ResponseEntity<ParkingLotsResponse> find(
            @ParkingSearchCondition ParkingSearchConditionRequest parkingSearchConditionRequest) {
//        ParkingLotsResponse parkingLots = parkingService.findParkingLots(parkingQueryRequest,1L);
//        return ResponseEntity.ok(parkingLots);
//        @ParkingQuery ParkingQueryRequest parkingQueryRequest,
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
