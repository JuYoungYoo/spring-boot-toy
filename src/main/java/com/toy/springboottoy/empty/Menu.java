package com.toy.springboottoy.empty;

import com.toy.springboottoy.stores.Store;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Menu {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne(mappedBy = "menu")
    private Store store;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Food> foods = new ArrayList<>();

    public void init(final Store store) {
        this.store = store;
    }

    public void addFood(final Food food) {
        this.foods.add(food);
    }
}
