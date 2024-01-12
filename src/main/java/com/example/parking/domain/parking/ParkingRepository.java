package com.example.parking.domain.parking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    default Parking getById(Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException("익셉션 !!"));
    }
}
