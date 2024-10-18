package kau.CalmCafe.global.api_payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode implements BaseCode {
    // Common
    OK(HttpStatus.OK, "COMMON_200", "Success"),
    CREATED(HttpStatus.CREATED, "COMMON_201", "Created"),

    // User
    USER_LOGIN_SUCCESS(HttpStatus.CREATED, "USER_2011", "회원가입& 로그인이 완료되었습니다."),
    USER_LOGOUT_SUCCESS(HttpStatus.OK, "USER_2001", "로그아웃 되었습니다."),
    USER_REISSUE_SUCCESS(HttpStatus.OK, "USER_2002", "토큰 재발급이 완료되었습니다."),
    USER_DELETE_SUCCESS(HttpStatus.OK, "USER_2003", "회원탈퇴가 완료되었습니다."),

    USER_NICKNAME_SUCCESS(HttpStatus.OK, "USER_2004", "닉네임 생성/수정이 완료되었습니다."),
    USER_INFO_UPDATE_SUCCESS(HttpStatus.OK, "USER_2005", "회원 정보 수정이 완료 되었습니다."),
    USER_INFO_VIEW_SUCCESS(HttpStatus.OK, "USER_2006", "회원 정보 조회가 완료 되었습니다."),
    USER_PROFILE_IMAGE_UPDATE_SUCCESS(HttpStatus.OK, "USER_2007", "프로필 사진 이미지 업로드가 완료 되었습니다."),

    // Store
    STORE_DETAIL_FROM_USER_SUCCESS(HttpStatus.OK, "STORE_2001", "유저 측 화면에서 매장 상세 정보 조회가 완료되었습니다."),
    STORE_DETAIL_FROM_CAFE_SUCCESS(HttpStatus.OK, "STORE_2002", "카페 측 화면에서 매장 상세 정보 조회가 완료되었습니다."),
    STORE_FAVORITE_SUCCESS(HttpStatus.OK, "STORE_2003", "매장 즐겨찾기가 완료되었습니다."),
    STORE_UNFAVORITE_SUCCESS(HttpStatus.OK, "STORE_2004", "매장 즐겨찾기 취소가 완료되었습니다."),
    STORE_CONGESTION_GET_SUCCESS(HttpStatus.OK, "STORE_2005", "유저 측 화면에서 매장 혼잡도 조회가 완료되었습니다."),
    STORE_NEAR_LIST_SUCCESS(HttpStatus.OK, "STORE_2006", "주변 매장 좌표 조회가 완료되었습니다."),
    STORE_BUY_COUPON_POINT_SUCCESS(HttpStatus.OK, "STORE_2007", "포인트 스토어 내 상품 구매가 완료되었습니다."),
    STORE_RANKING_CONGESTION_SUCCESS(HttpStatus.OK, "STORE_2008", "실시간 방문자 수 TOP 100 매장 리스트 반환이 완료되었습니다."),
    STORE_RANKING_TOTAL_VISIT_SUCCESS(HttpStatus.OK, "STORE_2009", "누적 방문자 수 TOP 100 매장 리스트 반환이 완료되었습니다."),
    STORE_RANKING_FAVORITE_SUCCESS(HttpStatus.OK, "STORE_2009", "즐겨찾기 수 TOP 100 매장 리스트 반환이 완료되었습니다."),

    // Congestion
    CONGESTION_INPUT_SUCCESS(HttpStatus.OK, "CONGESTION_2011", "혼잡도 입력이 완료되었습니다."),
    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .httpStatus(this.httpStatus)
                .isSuccess(true)
                .code(this.code)
                .message(this.message)
                .build();
    }
}
