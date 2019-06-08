package com.toy.springboottoy.menus;


import org.springframework.stereotype.Service;

@Service
public class MenuRegisterService {

    private final MenuRepository menuRepository;

    public MenuRegisterService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Menu registerMenu(Menu menu) {
        return menuRepository.save(menu);
    }
}
