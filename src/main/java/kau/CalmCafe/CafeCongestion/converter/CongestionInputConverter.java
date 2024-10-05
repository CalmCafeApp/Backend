package kau.CalmCafe.CafeCongestion.converter;

import kau.CalmCafe.CafeCongestion.domain.Congestion;
import kau.CalmCafe.CafeCongestion.domain.CongestionInput;
import kau.CalmCafe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CongestionInputConverter{
    public static CongestionInput congestionInput(User user, Congestion congestion, int congestionInputValue){
        return CongestionInput.builder()
                .user(user)
                .congestion(congestion)
                .congestioninputvalue(congestionInputValue)
                .build();
    }
}
