package com.toy.springboottoy.stores.domain;

import com.toy.springboottoy.common.TestDescription;
import org.junit.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class OpeningHoursTest {

    @Test
    public void generated_success() {
        LocalTime openTime = LocalTime.of(9, 0);
        LocalTime closeTime = LocalTime.of(21, 0);

        OpeningHours openingHours = OpeningHours.of(openTime, closeTime);

        assertThat(openingHours.getOpenTime()).isEqualTo(openTime);
        assertThat(openingHours.getCloseTime()).isEqualTo(closeTime);
    }

    @Test(expected = IllegalArgumentException.class)
    @TestDescription("오픈시간이 마감시간 이후인 경우 Exception")
    public void generated_fail() {
        OpeningHours.of(LocalTime.of(21, 0), LocalTime.of(9, 0));
    }
}