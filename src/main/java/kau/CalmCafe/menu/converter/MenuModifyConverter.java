package kau.CalmCafe.menu.converter;

import kau.CalmCafe.menu.dto.DiscountedMenuDto;
import kau.CalmCafe.menu.dto.MenuModifyResponseDto;
import kau.CalmCafe.store.domain.Menu;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuModifyConverter {

    public MenuModifyResponseDto toDeleteResponseDto(Menu menu) {
        return MenuModifyResponseDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .build();
    }

    public MenuModifyResponseDto toUpdateResponseDto(Menu menu) {
        return MenuModifyResponseDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .image(menu.getImage())
                .price(menu.getPrice())
                .build();
    }

    public MenuModifyResponseDto toResponseDto(Menu menu) {
        return MenuModifyResponseDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .image(menu.getImage())
                .price(menu.getPrice())
                .pointPrice(menu.getPointPrice())
                .build();
    }

    public List<DiscountedMenuDto> toDiscountedMenuDtoList(List<Menu> menus) {
        return menus.stream()
                .map(menu -> DiscountedMenuDto.builder()
                        .name(menu.getName())
                        .pointDiscount(menu.getPointDiscount())
                        .build())
                .collect(Collectors.toList());
    }
}