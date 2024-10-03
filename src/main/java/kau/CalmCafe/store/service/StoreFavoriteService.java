package kau.CalmCafe.store.service;

import jakarta.transaction.Transactional;
import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.store.converter.StoreFavoriteConverter;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.domain.StoreFavorite;
import kau.CalmCafe.store.repository.StoreFavoriteRepository;
import kau.CalmCafe.store.repository.StoreRepository;
import kau.CalmCafe.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreFavoriteService {
    private final StoreRepository storeRepository;
    private final StoreFavoriteRepository storeFavoriteRepository;

    @Transactional
    public Integer createFavoriteAndRetrieveCount(Long storeId, User user) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.STORE_NOT_FOUND));

        StoreFavorite storeFavorite = storeFavoriteRepository.findByUserAndStore(user, store).orElse(null);

        // 이미 즐겨찾기 했다면,
        if (storeFavorite != null) {
            throw new GeneralException(ErrorCode.STORE_ALREADY_FAVORITE);
        }
        // 즐겨찾기 하지 않았다면
        else {
            storeFavoriteRepository.save(StoreFavoriteConverter.storeFavorite(user, store));
            return store.increaseFavoriteCount();
        }
    }

    @Transactional
    public Integer deleteFavoriteAndRetrieveCount(Long storeId, User user) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.STORE_NOT_FOUND));

        StoreFavorite storeFavorite = storeFavoriteRepository.findByUserAndStore(user, store).orElse(null);

        // 즐겨찾기 했었다면,
        if (storeFavorite != null) {
            storeFavoriteRepository.delete(storeFavorite);
            return store.decreaseFavoriteCount();
        }
        // 즐겨찾기 하지 않았다면
        else {
            throw new GeneralException(ErrorCode.STORE_FAVORITE_NOT_FOUND);
        }
    }
}
