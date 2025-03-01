package kau.CalmCafe.store.converter;

import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.store.dto.MenuResponseDto.PointMenuDetailResDto;
import kau.CalmCafe.store.dto.MenuResponseDto.MenuDetailResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuConverter {
    public static MenuDetailResDto menuDetailResDto(Menu menu) {

        return MenuDetailResDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .image(menu.getImage())
                .build();
    }

    public static PointMenuDetailResDto pointMenuDetailResDto(Menu menu) {

        return PointMenuDetailResDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .pointPrice(menu.getPointPrice())
                .pointDiscount(menu.getPointDiscount())
                .image(menu.getImage())
                .build();
    }
}
