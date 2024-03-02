package com.example.parking.domain.favorite;

import com.example.parking.domain.member.MemberId;
import com.example.parking.domain.parking.ParkingId;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface FavoriteRepository extends Repository<Favorite, Long> {

    Favorite save(Favorite favorite);

    void deleteByMemberIdAndParkingId(MemberId memberId, ParkingId parkingId);

    List<Favorite> findByMemberId(MemberId memberId);
}
