package kau.CalmCafe.menu.converter;

import kau.CalmCafe.menu.dto.MenuModifyResponseDto;
import kau.CalmCafe.store.domain.Menu;
import org.springframework.stereotype.Component;

@Component
public class MenuModifyConverter {

    public MenuModifyResponseDto toDeleteResponseDto(Menu menu) {
        return MenuModifyResponseDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .build();
    }
}