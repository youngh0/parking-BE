package com.example.parking.api.parking;

import com.example.parking.application.parking.ParkingService;
import com.example.parking.application.parking.dto.ParkingInfoResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ParkingController {

    private final ParkingService parkingService;

    @GetMapping("/parkings/{parkingId}")
    public ResponseEntity<ParkingInfoResponse> findParking(@PathVariable Long parkingId) {
        ParkingInfoResponse parkingInfoResponse = parkingService.findParking(parkingId, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(parkingInfoResponse);
    }
}
