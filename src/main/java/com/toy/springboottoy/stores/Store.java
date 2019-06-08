package com.toy.springboottoy.stores;

import com.toy.springboottoy.menus.Menu;
import com.toy.springboottoy.stores.domain.OpeningHours;
import com.toy.springboottoy.stores.domain.StoreCategory;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String name;
    private String location;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private StoreCategory category;
    @Embedded
    private OpeningHours openingHours;
    @Lob
    private String description;
    @OneToMany(mappedBy = "store", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Menu> menus = new ArrayList<>();

    @Builder
    public Store(String name,
                 StoreCategory category,
                 String location,
                 String phoneNumber,
                 OpeningHours openingHours,
                 String description) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.category = category;
        this.openingHours = openingHours;
        this.description = description;
    }

    public void addMenu(final Menu menu) {
        menu.addStore(this);
        menus.add(menu);
    }

}
