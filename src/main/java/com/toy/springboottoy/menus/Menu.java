package com.toy.springboottoy.menus;

import com.toy.springboottoy.stores.Store;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;
    private String imageUrl;
    private String description;
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public void addStore(final Store store) {
        this.store = store;
    }
}
