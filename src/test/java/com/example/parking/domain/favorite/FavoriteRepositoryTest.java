package com.example.parking.domain.favorite;

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
        Long memberId = 1L;
        Long parkingId = 1L;
        Favorite favorite = new Favorite(memberId, parkingId);
        favoriteRepository.save(favorite);

        favoriteRepository.deleteByMemberIdAndParkingId(memberId, parkingId);
        assertThat(favoriteRepository.findByMemberId(memberId)).isEmpty();
    }

    @Test
    void 멤버의_즐겨찾기_주차장을_조회한다() {
        // given
        Long memberId = 1L;
        Long memberId2 = 2L;

        Long parkingId = 1L;
        Long parkingId2 = 2L;
        Long parkingId3 = 3L;
        Long parkingId4 = 4L;

        // when
        favoriteRepository.save(new Favorite(memberId, parkingId));
        favoriteRepository.save(new Favorite(memberId, parkingId2));
        favoriteRepository.save(new Favorite(memberId, parkingId3));

        favoriteRepository.save(new Favorite(memberId2, parkingId3));
        favoriteRepository.save(new Favorite(memberId2, parkingId4));

        // then
        assertThat(favoriteRepository.findByMemberId(1L)).hasSize(3);
    }
}
