package com.example.parking.domain.favorite;

import com.example.parking.domain.AuditingEntity;
import com.example.parking.domain.member.MemberId;
import com.example.parking.domain.parking.ParkingId;
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

    @Embedded
    private MemberId memberId;

    @Embedded
    private ParkingId parkingId;

    public Favorite(MemberId memberId, ParkingId parkingId) {
        this.memberId = memberId;
        this.parkingId = parkingId;
    }
}
