package kau.CalmCafe.CafeCongestion.converter;

import kau.CalmCafe.CafeCongestion.domain.Congestion;
import kau.CalmCafe.CafeCongestion.dto.CongestionDto;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class CongestionConverter {
    public Congestion toEntity(CongestionDto.Request dto) {
        return Congestion.builder()
                .cafeName(dto.getCafeName())
                .congestionLevel(dto.getCongestionLevel())
                .build();
    }

    public CongestionDto.Response toDto(Congestion congestion) {
        return CongestionDto.Response.builder()
                .cafeName(congestion.getCafeName())
                .congestionLevel(congestion.getCongestionLevel())
                .build();
    }
}