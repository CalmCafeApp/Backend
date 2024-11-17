package kau.CalmCafe.menu.service;

import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.menu.converter.MenuModifyConverter;
import kau.CalmCafe.menu.dto.MenuModifyResponseDto;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.repository.MenuRepository;
import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuModifyService {

    private final MenuRepository menuRepository;
    private final MenuModifyConverter menuModifyConverter;
    private final StoreRepository storeRepository;

    @Transactional
    public MenuModifyResponseDto deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.MENU_NOT_FOUND));
        menuRepository.delete(menu);
        return menuModifyConverter.toDeleteResponseDto(menu);
    }

    @Transactional
    public MenuModifyResponseDto updateMenu(Long menuId, String image, Integer price) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.MENU_NOT_FOUND));

        if (image != null) {
            menu.setImage(image);
        }
        if (price != null) {
            menu.setPrice(price);
        }

        Menu updatedMenu = menuRepository.save(menu);
        return menuModifyConverter.toUpdateResponseDto(updatedMenu);
    }

    @Transactional
    public MenuModifyResponseDto registerMenu(Long storeId, String name, Integer price, String image) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.STORE_NOT_FOUND));

        Menu newMenu = Menu.builder()
                .store(store)
                .name(name)
                .price(price)
                .image(image)
                .pointPrice(0)
                .pointDiscount(0)
                .build();

        Menu savedMenu = menuRepository.save(newMenu);
        return menuModifyConverter.toResponseDto(savedMenu);
    }
}