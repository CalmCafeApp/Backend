package kau.CalmCafe.menu.service;

import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.menu.converter.MenuModifyConverter;
import kau.CalmCafe.menu.dto.MenuModifyResponseDto;
import kau.CalmCafe.store.repository.MenuRepository;
import kau.CalmCafe.store.domain.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuModifyService {

    private final MenuRepository menuRepository;
    private final MenuModifyConverter menuModifyConverter;

    @Transactional
    public MenuModifyResponseDto deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.MENU_NOT_FOUND));
        menuRepository.delete(menu);
        return menuModifyConverter.toDeleteResponseDto(menu);
    }
}