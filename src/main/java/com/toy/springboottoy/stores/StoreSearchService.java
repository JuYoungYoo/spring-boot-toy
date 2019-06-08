package com.toy.springboottoy.stores;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.springboottoy.stores.domain.StoreCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreSearchService extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public StoreSearchService(JPAQueryFactory queryFactory) {
        super(Store.class);
        this.queryFactory = queryFactory;
    }

    public Page<Store> search(final StoreCategory category,
                              final StoreSearchType type,
                              final String value,
                              final Pageable pageable) {
        final QStore store = QStore.store;
        final JPQLQuery<Store> query;

        switch (type) {
            case NAME:
                query = from(store)
                        .where(store.name.likeIgnoreCase("%" + value + "%"));
                break;
            case ALL:
                query = from(store).fetchAll();
                break;
            default:
                throw new IllegalArgumentException("search no");
        }

        if (category != null) {
            query.where(store.category.eq(category));
        }

        final List<Store> stores = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(stores, pageable, query.fetchCount());
    }

}
