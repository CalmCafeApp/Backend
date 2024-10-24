package kau.CalmCafe.ranking.converter;

import java.util.List;
import kau.CalmCafe.ranking.dto.RankingResponseDto.RankingResDto;
import kau.CalmCafe.ranking.dto.RankingResponseDto.RankingListResDto;
import kau.CalmCafe.store.converter.StoreConverter;
import kau.CalmCafe.store.domain.Store;

import kau.CalmCafe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RankingConverter {
    public static RankingResDto rankingResDto(Store store, User user) {

        return RankingResDto.builder()
                .id(store.getId())
                .name(store.getName())
                .storeCongestionLevel(store.getStoreCongestionLevel())
                .userCongestionLevel(store.getUserCongestionLevel())
                .isFavorite(StoreConverter.isFavorite(store, user))
                .image(store.getImage())
                .address(store.getAddress())
                .build();

    }

    public static RankingListResDto rankingListResDto(List<Store> storeList, User user) {

        List<RankingResDto> storeRankingResDtoList = storeList.stream()
                .map(store -> rankingResDto(store, user))
                .toList();

        return RankingListResDto.builder()
                .StoreRankingResDtoList(storeRankingResDtoList)
                .build();

    }

}
