package kau.CalmCafe.CafeCongestion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kau.CalmCafe.CafeCongestion.dto.CongestionDto;
import kau.CalmCafe.CafeCongestion.service.CongestionService;
import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "카페 혼잡도", description = "카페 혼잡도 관련 API입니다.")
@RestController
@RequestMapping("/congestion")
@RequiredArgsConstructor
public class CongestionController {

    private final CongestionService congestionService;

    @Operation(summary = "카페 생성", description = "새로운 카페를 생성하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CAFE_2003", description = "카페 생성이 완료되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CAFE_4043", description = "이미 존재하는 카페입니다.")
    })
    @PostMapping
    public ApiResponse<CongestionDto.Response> createCafe(@RequestBody @Valid CongestionDto.Request request) {
        CongestionDto.Response response = congestionService.createCafe(request);
        return ApiResponse.onSuccess(SuccessCode.CAFE_CREATE_SUCCESS, response);
    }

    @Operation(summary = "혼잡도 업데이트", description = "카페의 혼잡도를 업데이트하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CAFE_2001", description = "혼잡도 업데이트가 완료되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CAFE_4041", description = "존재하지 않는 카페입니다.")
    })
    @PutMapping("")
    public ApiResponse<CongestionDto.Response> updateCongestion(@RequestBody @Valid CongestionDto.Request request) {
        CongestionDto.Response response = congestionService.updateCongestion(request);
        return ApiResponse.onSuccess(SuccessCode.CAFE_CONGESTION_UPDATE_SUCCESS, response);
    }

    @Operation(summary = "모든 카페 조회", description = "모든 카페의 혼잡도 정보를 조회하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CAFE_2002", description = "모든 카페 정보 조회가 완료되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CAFE_4042", description = "등록된 카페가 없습니다.")
    })
    @GetMapping
    public ApiResponse<List<CongestionDto.Response>> getAllCafes() {
        List<CongestionDto.Response> responses = congestionService.getAllCafes();
        return ApiResponse.onSuccess(SuccessCode.CAFE_GET_ALL_SUCCESS, responses);
    }
}