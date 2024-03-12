package com.example.parking.domain.parking;

import com.example.parking.support.exception.DomainException;
import com.example.parking.support.exception.ExceptionInformation;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.repository.Repository;

public interface ParkingRepository extends Repository<Parking, Long> {

    Optional<Parking> findById(Long id);

    default Parking getById(Long id) {
        return findById(id).orElseThrow(() -> new DomainException(ExceptionInformation.INVALID_PARKING));
    }

    Set<Parking> findAllByBaseInformationNameIn(Set<String> names);

    void saveAll(Iterable<Parking> parkingLots);
}
