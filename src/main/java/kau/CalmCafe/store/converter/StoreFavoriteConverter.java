package kau.CalmCafe.store.converter;

import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.domain.StoreFavorite;
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
}
