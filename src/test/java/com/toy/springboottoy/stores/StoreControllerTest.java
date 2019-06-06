package com.toy.springboottoy.stores;

import com.toy.springboottoy.common.BaseControllerTest;
import com.toy.springboottoy.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoreControllerTest extends BaseControllerTest {

    @Autowired
    StoreRepository storeRepository;

    @Override
    @Before
    public void setUp() throws Exception {
        storeRepository.deleteAll();
    }

    @Test
    @TestDescription("10개 이벤트를 4개씩 두번째 페이지 조회")
    public void queryStores() throws Exception {
        mockMvc.perform(get("/stores")
                .param("page", "1")
                .param("size", "4")
                .param("sort", "name,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("store5"))
                .andExpect(jsonPath("$.content[0].id").value(5))
                .andExpect(jsonPath("pageable").exists())
        ;
    }

    @Test
    @TestDescription("퓨전음식만 검색")
    public void queryStores_category_FUSION() throws Exception {
        mockMvc.perform(get("/stores")
                .param("page", "1")
                .param("size", "4")
                .param("sort", "name,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("store5"))
                .andExpect(jsonPath("$.content[0].id").value(5))
                .andExpect(jsonPath("pageable").exists())
        ;
    }

}