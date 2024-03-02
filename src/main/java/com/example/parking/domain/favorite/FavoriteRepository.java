package com.example.parking.domain.favorite;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface FavoriteRepository extends Repository<Favorite, Long> {

    Favorite save(Favorite favorite);

    void deleteByMemberIdAndParkingId(Long memberId, Long parkingId);

    List<Favorite> findByMemberId(Long memberId);
}
