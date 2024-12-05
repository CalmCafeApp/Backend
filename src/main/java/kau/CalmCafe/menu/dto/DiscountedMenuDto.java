package kau.CalmCafe.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DiscountedMenuDto {
    private Long id;
    private String name;
    private Integer pointDiscount;
}