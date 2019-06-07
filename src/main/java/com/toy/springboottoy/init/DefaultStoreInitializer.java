package com.toy.springboottoy.init;


import com.toy.springboottoy.stores.Store;
import com.toy.springboottoy.stores.StoreService;
import com.toy.springboottoy.stores.domain.OpeningHours;
import com.toy.springboottoy.stores.domain.StoreCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.stream.IntStream;

@Slf4j
@Component
public class DefaultStoreInitializer implements ApplicationRunner {

    @Autowired
    StoreService storeService;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Init register start...");
        IntStream.rangeClosed(1, 15).forEach(num -> fixtureStore("일식전문점" + num, StoreCategory.JAPANESE));
        IntStream.rangeClosed(16, 30).forEach(num -> fixtureStore("퓨전요리전문점" + num, StoreCategory.FUSION));
        fixtureStore("토끼정", StoreCategory.FUSION);
        log.info("Init register success");
    }

    private Store fixtureStore(String name,
                               StoreCategory storeCategory) {
        OpeningHours openingHours = OpeningHours.of(LocalTime.of(9, 0), LocalTime.of(21, 0));
        Store store = Store.builder()
                .name(name)
                .category(storeCategory)
                .location("668, Seolleung-ro, Gangnam-gu, Seoul, Republic of Korea")
                .phoneNumber("02-123-4233")
                .openingHours(openingHours)
                .description("store description")
                .build();
        return storeService.registerStore(store);
    }
}
