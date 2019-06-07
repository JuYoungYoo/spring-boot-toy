package com.toy.springboottoy.stores;

import com.toy.springboottoy.stores.domain.StoreCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;
    private final StoreSearchService storeSearchService;

    public StoreController(StoreService storeService,
                           StoreSearchService storeSearchService) {
        this.storeService = storeService;
        this.storeSearchService = storeSearchService;
    }

    @GetMapping
    public Page<Store> queryStores(Pageable pageable) {
        return storeService.findAll(pageable);
    }

    @GetMapping("/search")
    public Page<Store> queryStores(@RequestParam(name = "filter", required = false) final StoreCategory category,
                                   @RequestParam("type") final StoreSearchType type,
                                   @RequestParam(name = "value", required = false) final String value,
                                   final Pageable pageable) {
        return storeSearchService.search(category, type, value, pageable);
    }

}
