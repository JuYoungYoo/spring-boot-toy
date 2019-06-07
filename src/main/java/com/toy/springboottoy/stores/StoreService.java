package com.toy.springboottoy.stores;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Store> findAll(Pageable pageable) {
        return storeRepository.findAll(pageable);
    }
}
