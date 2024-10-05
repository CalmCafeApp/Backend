package kau.CalmCafe.CafeCongestion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kau.CalmCafe.CafeCongestion.domain.CongestionLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CongestionDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @NotBlank(message = "카페 이름은 필수입니다.")
        @Schema(description = "카페 이름")
        private String cafeName;

        @NotNull(message = "혼잡도는 필수입니다.")
        @Schema(description = "혼잡도")
        private CongestionLevel congestionLevel;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        @Schema(description = "카페 이름")
        private String cafeName;

        @Schema(description = "혼잡도")
        private CongestionLevel congestionLevel;
    }
}