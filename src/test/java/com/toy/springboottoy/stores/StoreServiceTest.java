package com.toy.springboottoy.stores;

import com.toy.springboottoy.stores.domain.OpeningHours;
import com.toy.springboottoy.stores.domain.StoreCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class StoreServiceTest {

    @InjectMocks
    StoreService storeService;
    @Mock
    StoreRepository storeRepository;

    @Test
    public void registerStore() {
        Store store = Store.builder()
                .name("토끼정")
                .category(StoreCategory.FUSION)
                .location("서울특별시 강남")
                .openingHours(OpeningHours.of(LocalTime.of(9, 0), LocalTime.of(21, 0)))
                .phoneNumber("02-123-4569")
                .description("store detail description")
                .build();
        store.setId(1l);

        given(storeRepository.save(any())).willReturn(store);
        Store newStore = storeService.registerStore(store);

        assertThat(newStore.getId()).isNotNull();
        assertThat(newStore.getName()).isEqualTo("토끼정");
    }

}