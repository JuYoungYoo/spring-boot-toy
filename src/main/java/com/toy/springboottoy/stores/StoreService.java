package com.toy.springboottoy.stores;

import org.springframework.stereotype.Service;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store registerStore(Store store) {
        return storeRepository.save(store);
    }
}
