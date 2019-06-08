package com.toy.springboottoy.stores;

import com.toy.springboottoy.common.BaseControllerTest;
import com.toy.springboottoy.common.TestDescription;
import com.toy.springboottoy.stores.domain.StoreCategory;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoreControllerTest extends BaseControllerTest {

    @Autowired
    StoreRepository storeRepository;

    @Test
    @TestDescription("10개 이벤트를 4개씩 두번째 페이지 조회")
    public void queryStores() throws Exception {
        mockMvc.perform(get("/stores")
                .param("page", "1")
                .param("size", "4")
                .param("sort", "name,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(4))
                .andExpect(jsonPath("$.content[0].id").value(26))
                .andExpect(jsonPath("pageable").exists())
        ;
    }

    @Test
    @TestDescription("카테고리 필터로 퓨전음식점 검색")
    public void queryStores_category_FUSION() throws Exception {
        String categoryFilter = StoreCategory.FUSION.name();
        String searchType = StoreSearchType.ALL.name();

        perform(categoryFilter, searchType, null)
                .andExpect(jsonPath("$.content[0].category").value(categoryFilter))
                .andExpect(jsonPath("pageable").exists())
        ;
    }

    @Test
    @TestDescription("이름 검색")
    public void queryStores_by_name() throws Exception {
        String searchType = StoreSearchType.NAME.name();
        String searchValue = "일식전문점1";

        perform(null, searchType, searchValue)
                .andExpect(jsonPath("$.content[0].name", Matchers.is(searchValue)))
                .andExpect(jsonPath("pageable").exists())
        ;
    }

    @Test
    @TestDescription("음식점 카테고리 필터와 이름 검색")
    public void queryStores_by_category() throws Exception {
        String categoryFilter = StoreCategory.FUSION.name();
        String searchType = StoreSearchType.NAME.name();
        String storeName = "토끼정";

        perform(categoryFilter, searchType, storeName)
                .andExpect(jsonPath("$.content[0].name", Matchers.is(storeName)))
                .andExpect(jsonPath("$.content[0].category", Matchers.is(categoryFilter)))
                .andExpect(jsonPath("pageable").exists())
        ;
    }

    private ResultActions perform(String filter,
                                  String searchType,
                                  String searchValue) throws Exception {
        if (searchType.isEmpty()) {
            searchType = StoreSearchType.ALL.name();
        }

        return mockMvc.perform(get("/stores/search")
                .param("page", "0")
                .param("size", "4")
                .param("filter", filter)
                .param("type", searchType)
                .param("value", searchValue))
                .andDo(print())
                .andExpect(status().isOk());
    }
}