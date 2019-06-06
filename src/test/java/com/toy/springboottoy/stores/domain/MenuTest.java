package com.toy.springboottoy.stores.domain;

import com.toy.springboottoy.empty.Menu;
import com.toy.springboottoy.stores.Store;
import org.junit.Test;

import java.time.LocalTime;

public class MenuTest {

    @Test
    public void addFood() {
        Menu menu = Menu.builder().build();
        menu.init(createStore());
//        menu.addFood();
    }

    private Store createStore() {
        return Store.builder()
                .name("토끼정")
                .location("강남")
                .category(StoreCategory.JAPANESE)
                .phoneNumber("02-585-1242")
                .openingHours(OpeningHours.of(LocalTime.of(9, 0), LocalTime.of(21, 30)))
                .build();
    }

}