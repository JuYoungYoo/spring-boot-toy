package com.toy.springboottoy.stores;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreSearchService storeSearchService;

    public StoreController(StoreSearchService storeSearchService) {
        this.storeSearchService = storeSearchService;
    }

    @GetMapping
    public Page<Store> queryStores(Pageable pageable) {
        return storeSearchService.all(pageable);
    }

}
