package com.toy.springboottoy.init;


import com.toy.springboottoy.empty.FoodRepository;
import com.toy.springboottoy.stores.StoreRepository;
import com.toy.springboottoy.stores.StoreService;
import com.toy.springboottoy.stores.domain.OpeningHours;
import com.toy.springboottoy.stores.domain.StoreCategory;
import com.toy.springboottoy.stores.Store;
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
    public void run(ApplicationArguments args) throws Exception {
        log.info("Init register start...");
        IntStream.rangeClosed(1, 10).forEach(num -> fixtureStore(num));
        log.info("Init register success");
    }

    private Store fixtureStore(int i) {
        OpeningHours openingHours = OpeningHours.of(LocalTime.of(9, 0), LocalTime.of(21, 0));
        Store store = Store.builder()
                .name("store" + i)
                .category(StoreCategory.KOREAN)
                .location("강남" + i)
                .phoneNumber("02-123-4233")
                .openingHours(openingHours)
                .description("가게 설명")
                .build();
        return storeService.registerStore(store);
    }
}
