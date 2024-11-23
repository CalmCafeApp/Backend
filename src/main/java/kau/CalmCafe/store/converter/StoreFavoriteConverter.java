package kau.CalmCafe.store.converter;

import java.util.List;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.domain.StoreFavorite;
import kau.CalmCafe.store.dto.StoreResponseDto.FavoriteStoreDetailListResDto;
import kau.CalmCafe.store.dto.StoreResponseDto.FavoriteStoreDetailResDto;
import kau.CalmCafe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreFavoriteConverter {

    public static StoreFavorite storeFavorite(User user, Store store) {
        return StoreFavorite.builder()
                .store(store)
                .user(user)
                .build();
    }

    public static FavoriteStoreDetailResDto favoriteStoreDetailResDto(Store store, User user) {
        Boolean isFavorite = user.getStoreFavoriteList().stream()
                .anyMatch(storeFavorite -> storeFavorite.getUser() == user);

        return FavoriteStoreDetailResDto.builder()
                .id(store.getId())
                .name(store.getName())
                .image(store.getImage())
                .isFavorite(isFavorite)
                .storeCongestionLevel(store.getStoreCongestionLevel())
                .userCongestionLevel(store.getUserCongestionLevel())
                .build();
    }

    public static FavoriteStoreDetailListResDto favoriteStoreDetailListResDto (List<StoreFavorite> storeFavoriteList, User user) {
        List<FavoriteStoreDetailResDto> favoriteStoreDetailResDtoList = storeFavoriteList.stream()
                .map(storeFavorite -> favoriteStoreDetailResDto(storeFavorite.getStore(), user))
                .toList();

        return FavoriteStoreDetailListResDto.builder()
                .favoriteStoreDetailResDtoList(favoriteStoreDetailResDtoList)
                .build();
    }

}
