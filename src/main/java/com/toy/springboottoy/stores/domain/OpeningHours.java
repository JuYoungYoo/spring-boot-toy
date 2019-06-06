package com.toy.springboottoy.stores.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpeningHours {
    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;
    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    public static OpeningHours of(LocalTime openTime,
                                  LocalTime closeTime) {
        validatedOpeningHours(openTime, closeTime);
        return new OpeningHours(openTime, closeTime);
    }

    private static void validatedOpeningHours(LocalTime openTime,
                                              LocalTime closeTime) {
        if (openTime.isAfter(closeTime)) {
            throw new IllegalArgumentException("OpenTime can't be closeTime");
        }
    }

    private OpeningHours(LocalTime openTime,
                         LocalTime closeTime) {
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}