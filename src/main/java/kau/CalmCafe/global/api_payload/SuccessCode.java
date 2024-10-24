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
    USER_PROFILE_GET_SUCCESS(HttpStatus.OK, "USER_2003", "유저 프로필 정보 반환이 완료되었습니다."),
    USER_SURVEY_SUCCESS(HttpStatus.OK, "USER_2004", "설문 조사를 통한 사용자 정보 저장이 완료되었습니다."),

    // Store
    STORE_DETAIL_FROM_USER_SUCCESS(HttpStatus.OK, "STORE_2001", "유저 측 화면에서 매장 상세 정보 조회가 완료되었습니다."),
    STORE_DETAIL_FROM_CAFE_SUCCESS(HttpStatus.OK, "STORE_2002", "카페 측 화면에서 매장 상세 정보 조회가 완료되었습니다."),
    STORE_CONGESTION_GET_SUCCESS(HttpStatus.OK, "STORE_2003", "유저 측 화면에서 매장 혼잡도 조회가 완료되었습니다."),
    STORE_NEAR_LIST_SUCCESS(HttpStatus.OK, "STORE_2004", "주변 매장 좌표 조회가 완료되었습니다."),
    STORE_TIME_UPDATE_SUCCESS(HttpStatus.OK,"STORE_2005", "매장 이용 시간 수정이 완료되었습니다."),
    STORE_LAST_ORDER_TIME_UPDATE_SUCCESS(HttpStatus.OK,"STORE_2006","마지막 주문 시간 수정이 완료되었습니다."),
    STORE_CAPACITY_UPDATE_SUCCESS(HttpStatus.OK,"STORE_2007","매장의 최대 수용 인원 수정이 완료되었습니다."),

    // Strore Favorite
    STORE_FAVORITE_SUCCESS(HttpStatus.OK, "FAVORITE_2001", "매장 즐겨찾기가 완료되었습니다."),
    STORE_UNFAVORITE_SUCCESS(HttpStatus.OK, "FAVORITE_2002", "매장 즐겨찾기 취소가 완료되었습니다."),
    STORE_FAVORITE_LIST_SUCCESS(HttpStatus.OK, "FAVORITE_2003", "사용자가 즐겨찾기한 매장 리스트 반환이 완료되었습니다."),

    // Ranking
    RANKING_CONGESTION_SUCCESS(HttpStatus.OK, "RANKING_2001", "실시간 방문자 수 TOP 100 매장 리스트 반환이 완료되었습니다."),
    RANKING_TOTAL_VISIT_SUCCESS(HttpStatus.OK, "RANKING_2002", "누적 방문자 수 TOP 100 매장 리스트 반환이 완료되었습니다."),
    RANKING_FAVORITE_SUCCESS(HttpStatus.OK, "RANKING_2003", "즐겨찾기 수 TOP 100 매장 리스트 반환이 완료되었습니다."),

    // Promotion
    PROMOTION_USE_SUCCESS(HttpStatus.OK, "PROMOTION_2001", "사용자의 특정 매장의 프로모션 이용이 완료되었습니다."),

    // Congestion
    CONGESTION_INPUT_SUCCESS(HttpStatus.OK, "CONGESTION_2011", "혼잡도 입력이 완료되었습니다."),

    // Point
    BUY_POINT_COUPON_SUCCESS(HttpStatus.OK, "POINT_2001", "포인트 스토어 내 상품 구매가 완료되었습니다."),
    MY_POINT_COUPON_LIST_SUCCESS(HttpStatus.OK, "POINT_2002", "사용자가 보유한 포인트 쿠폰 리스트가 완료되었습니다."),

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
