package com.example.parking.domain.favorite;

import com.example.parking.domain.AuditingEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "favorite",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"memberId", "parkingId"}
                )
        }
)
@Entity
public class Favorite extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    private Long parkingId;

    public Favorite(Long memberId, Long parkingId) {
        this.memberId = memberId;
        this.parkingId = parkingId;
    }
}
