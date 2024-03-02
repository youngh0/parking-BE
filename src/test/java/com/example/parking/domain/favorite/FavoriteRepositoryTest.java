package com.example.parking.domain.favorite;

import com.example.parking.domain.member.MemberId;
import com.example.parking.domain.parking.ParkingId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    void 멤버의_즐겨찾기_주차장을_삭제한다() {
        MemberId memberId = new MemberId(1L);
        ParkingId parkingId = new ParkingId(1L);
        Favorite favorite = new Favorite(memberId, parkingId);
        favoriteRepository.save(favorite);

        favoriteRepository.deleteByMemberIdAndParkingId(memberId, parkingId);
        assertThat(favoriteRepository.findByMemberId(memberId)).isEmpty();
    }

    @Test
    void 멤버의_즐겨찾기_주차장을_조회한다() {
        // given
        MemberId memberId = new MemberId(1L);
        MemberId memberId2 = new MemberId(2L);

        ParkingId parkingId = new ParkingId(1L);
        ParkingId parkingId2 = new ParkingId(2L);
        ParkingId parkingId3 = new ParkingId(3L);
        ParkingId parkingId4 = new ParkingId(4L);

        // when
        favoriteRepository.save(new Favorite(memberId, parkingId));
        favoriteRepository.save(new Favorite(memberId, parkingId2));
        favoriteRepository.save(new Favorite(memberId, parkingId3));

        favoriteRepository.save(new Favorite(memberId2, parkingId3));
        favoriteRepository.save(new Favorite(memberId2, parkingId4));

        // then
        assertThat(favoriteRepository.findByMemberId(memberId)).hasSize(3);
    }
}
