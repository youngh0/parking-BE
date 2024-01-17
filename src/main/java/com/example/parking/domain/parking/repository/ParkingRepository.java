package com.example.parking.domain.parking.repository;

import com.example.parking.domain.parking.Parking;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface ParkingRepository extends Repository<Parking, Long> {

    Optional<Parking> findById(Long id);

    default Parking getById(Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException("익셉션 !!"));
    }

    Iterable<Parking> saveAll(Iterable<Parking> parkingLots);
}
