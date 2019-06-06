package com.toy.springboottoy.stores;

import com.toy.springboottoy.empty.Menu;
import com.toy.springboottoy.stores.domain.OpeningHours;
import com.toy.springboottoy.stores.domain.StoreCategory;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue
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
    @OneToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu;

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
        this.menu = menu;
        menu.init(this);
    }

}
