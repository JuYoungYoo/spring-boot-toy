package com.toy.springboottoy.stores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreSearchService {

    private final StoreRepository storeRepository;

    public StoreSearchService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> all() {
        return storeRepository.findAll();
    }


    public Page<Store> all(Pageable pageable) {
        return storeRepository.findAll(pageable);
    }

    public Page<Store> search(StoreSearchType type,
                              String value,
                              Pageable pageable) {
        return null;
    }
}
