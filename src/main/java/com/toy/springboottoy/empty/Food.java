package com.toy.springboottoy.empty;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
public class Food {

    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;
    private String imageUrl;
    private String description;
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Builder
    public Food(String name,
                int price,
                String imageUrl,
                String description) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.menu = menu;
    }

    public void registerMenu(final Menu menu) {
        this.menu = menu;
    }
}
