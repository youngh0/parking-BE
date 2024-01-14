package com.example.parking.domain.parking;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class FeePolicy {

    @Embedded
    @AttributeOverride(name = "fee", column = @Column(name = "base_fee"))
    private Fee baseFee;

    @Embedded
    @AttributeOverride(name = "fee", column = @Column(name = "extra_fee"))
    private Fee extraFee;

    @Embedded
    @AttributeOverride(name = "timeUnit", column = @Column(name = "base_time_unit"))
    private TimeUnit baseTimeUnit;

    @Embedded
    @AttributeOverride(name = "timeUnit", column = @Column(name = "extra_time_unit"))
    private TimeUnit extraTimUnit;

    @Embedded
    @AttributeOverride(name = "fee", column = @Column(name = "max_fee"))
    private Fee dayMaximumFee;

    public FeePolicy(final Fee baseFee, final Fee extraFee, final TimeUnit baseTimeUnit, final TimeUnit extraTimUnit,
                     final Fee dayMaximumFee) {
        this.baseFee = baseFee;
        this.extraFee = extraFee;
        this.baseTimeUnit = baseTimeUnit;
        this.extraTimUnit = extraTimUnit;
        this.dayMaximumFee = dayMaximumFee;
    }
}
