package kau.CalmCafe.analysis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kau.CalmCafe.analysis.converter.AnalysisConverter;
import kau.CalmCafe.analysis.dto.AnalysisResponseDto.AnalysisAboutCongestionResDto;
import kau.CalmCafe.analysis.dto.AnalysisResponseDto.AnalysisAboutFavoriteResDto;
import kau.CalmCafe.analysis.dto.AnalysisResponseDto.AnalysisAboutVisitResDto;
import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "데이터 분석", description = "데이터 분석 관련 api입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/data-analysis")
public class AnalysisController {
    @Operation(summary = "카페 방문자 관련 데이터 분석", description = "카페 방문자 관련 데이터 분석 이미지를 반환하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ANALYSIS_2001", description = "카페 방문자 관련 데이터 분석 이미지를 반환이 완료되었습니다.")
    })
    @GetMapping("/visit")
    public ApiResponse<AnalysisAboutVisitResDto> getDataAnalysisAboutVisit() {
        return ApiResponse.onSuccess(SuccessCode.DATA_ANALYSIS_ABOUT_VISIT, AnalysisConverter.analysisAboutVisitResDto());
    }

    @Operation(summary = "카페 방문자 관련 데이터 분석", description = "카페 즐겨찾기 관련 데이터 분석 이미지를 반환하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ANALYSIS_2002", description = "카페 즐겨찾기 관련 데이터 분석 이미지를 반환이 완료되었습니다.")
    })
    @GetMapping("/favorite")
    public ApiResponse<AnalysisAboutFavoriteResDto> getDataAnalysisAboutFavorite() {
        return ApiResponse.onSuccess(SuccessCode.DATA_ANALYSIS_ABOUT_FAVORITE, AnalysisConverter.analysisAboutFavoriteResDto());
    }

    @Operation(summary = "카페 방문자 관련 데이터 분석", description = "카페 혼잡도 관련 데이터 분석 이미지를 반환하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ANALYSIS_2003", description = "카페 혼잡도 관련 데이터 분석 이미지를 반환이 완료되었습니다.")
    })
    @GetMapping("/congestion")
    public ApiResponse<AnalysisAboutCongestionResDto> getDataAnalysisAboutCongestion() {
        return ApiResponse.onSuccess(SuccessCode.DATA_ANALYSIS_ABOUT_CONGESTION, AnalysisConverter.analysisAboutCongestionResDto());
    }
}
