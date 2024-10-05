package kau.CalmCafe.Congestion.converter;

import kau.CalmCafe.Congestion.domain.CongestionInput;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CongestionInputConverter{
    public static CongestionInput saveCongestionInput (User user, Integer congestionValue, Store store) {

        return CongestionInput.builder()
                .congestionInputValue(congestionValue)
                .user(user)
                .store(store)
                .build();
    }
}
