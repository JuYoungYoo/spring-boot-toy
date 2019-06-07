package com.toy.springboottoy.stores.domain;

import com.toy.springboottoy.common.TestDescription;
import com.toy.springboottoy.menus.Menu;
import com.toy.springboottoy.stores.Store;
import org.junit.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreTest {

    @Test
    public void generatorStore() {
        String name = "토끼정";
        Store store = Store.builder()
                .name(name)
                .location("강남")
                .category(StoreCategory.JAPANESE)
                .phoneNumber("02-585-1242")
                .openingHours(OpeningHours.of(LocalTime.of(9, 0), LocalTime.of(21, 30)))
                .build();

        assertThat(store.getName()).isEqualTo(name);
        assertThat(store.getOpeningHours()).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    @TestDescription("영업시간 잘못 입력한 경우 Exception")
    public void generatorStore_fail() {
        OpeningHours wrongOpeningHours = OpeningHours.of(LocalTime.of(21, 0), LocalTime.of(9, 0));
        Store.builder()
                .name("토끼정")
                .location("강남")
                .category(StoreCategory.JAPANESE)
                .phoneNumber("02-585-1242")
                .openingHours(wrongOpeningHours)
                .build();
    }

    @Test
    @TestDescription("메뉴 추가")
    public void addMenu() {
        Store store = Store.builder()
                .name("토끼정")
                .location("강남")
                .category(StoreCategory.JAPANESE)
                .phoneNumber("02-585-1242")
                .openingHours(OpeningHours.of(LocalTime.of(9, 0), LocalTime.of(21, 30)))
                .build();

        Menu menu = Menu.builder().build();
        store.addMenu(menu);
        assertThat(store.getMenus()).isNotNull();
        assertThat(store.getMenus()).isNotNull();
    }
}