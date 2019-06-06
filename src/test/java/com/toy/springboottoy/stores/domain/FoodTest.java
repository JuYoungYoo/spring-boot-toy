package com.toy.springboottoy.stores.domain;

import com.toy.springboottoy.empty.Food;
import com.toy.springboottoy.empty.Menu;
import com.toy.springboottoy.stores.Store;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FoodTest {

    @Test
    public void generate() {
        Food food = Food.builder()
                .name("떡볶이")
                .price(10_000)
                .build();
        food.registerMenu(Menu.builder().id(1l).store(Store.builder().build()).build());

        assertThat(food).isNotNull();
    }
}