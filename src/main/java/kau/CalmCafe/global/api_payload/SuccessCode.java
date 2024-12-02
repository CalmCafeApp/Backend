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
    USER_CHANGE_ROLE_SUCCESS(HttpStatus.OK, "USER_2005", "유저 역할 변경이 완료되었습니다."),

    // Store
    STORE_DETAIL_FROM_USER_SUCCESS(HttpStatus.OK, "STORE_2001", "유저 측 화면에서 매장 상세 정보 조회가 완료되었습니다."),
    STORE_DETAIL_FROM_CAFE_SUCCESS(HttpStatus.OK, "STORE_2002", "카페 측 화면에서 매장 상세 정보 조회가 완료되었습니다."),
    STORE_CONGESTION_GET_SUCCESS(HttpStatus.OK, "STORE_2003", "유저 측 화면에서 매장 혼잡도 조회가 완료되었습니다."),
    STORE_NEAR_LIST_SUCCESS(HttpStatus.OK, "STORE_2004", "주변 매장 좌표 조회가 완료되었습니다."),
    STORE_TIME_UPDATE_SUCCESS(HttpStatus.OK,"STORE_2005", "매장 이용 시간 수정이 완료되었습니다."),
    STORE_LAST_ORDER_TIME_UPDATE_SUCCESS(HttpStatus.OK,"STORE_2006","마지막 주문 시간 수정이 완료되었습니다."),
    STORE_CAPACITY_UPDATE_SUCCESS(HttpStatus.OK,"STORE_2007","매장의 최대 수용 인원 수정이 완료되었습니다."),
    STORE_MENU_GET_SUCCESS(HttpStatus.OK,"STORE_2008","매장 메뉴 조회가 완료되었습니다."),
    STORE_POINT_MENU_CREATE_SUCCESS(HttpStatus.OK,"STORE_2009","스토어 상품 등록이 완료되었습니다."),
    STORE_POINT_MENU_REMOVE_SUCCESS(HttpStatus.OK,"STORE_2010", "메뉴가 포인트 스토어에서 제거되었습니다."),

    // Store Favorite
    STORE_FAVORITE_SUCCESS(HttpStatus.OK, "FAVORITE_2001", "매장 즐겨찾기가 완료되었습니다."),
    STORE_UNFAVORITE_SUCCESS(HttpStatus.OK, "FAVORITE_2002", "매장 즐겨찾기 취소가 완료되었습니다."),
    STORE_FAVORITE_LIST_SUCCESS(HttpStatus.OK, "FAVORITE_2003", "사용자가 즐겨찾기한 매장 리스트 반환이 완료되었습니다."),

    // Ranking
    RANKING_CONGESTION_SUCCESS(HttpStatus.OK, "RANKING_2001", "실시간 방문자 수 TOP 100 매장 리스트 반환이 완료되었습니다."),
    RANKING_TOTAL_VISIT_SUCCESS(HttpStatus.OK, "RANKING_2002", "누적 방문자 수 TOP 100 매장 리스트 반환이 완료되었습니다."),
    RANKING_FAVORITE_SUCCESS(HttpStatus.OK, "RANKING_2003", "즐겨찾기 수 TOP 100 매장 리스트 반환이 완료되었습니다."),

    // Promotion
    PROMOTION_USE_SUCCESS(HttpStatus.OK, "PROMOTION_2001", "사용자의 특정 매장의 프로모션 이용이 완료되었습니다."),
    PROMOTION_UPDATE_SUCCESS(HttpStatus.OK, "PROMOTION_2002","프로모션 할인율 수정이 완료되었습니다."),
    PROMOTION_PERIOD_UPDATE_SUCCESS(HttpStatus.OK, "PROMOTION_2003", "프로모션 사용 기간 수정이 완료되었습니다."),
    PROMOTION_RETRIEVE_SUCCESS(HttpStatus.OK,"PROMOTION_2004", "매장의 프로모션 조회가 완료되었습니다."),
    PROMOTION_CREATE_SUCCESS(HttpStatus.OK,"PROMOTION_2005","프로모션 등록이 완료되었습니다."),
    PROMOTION_DELETE_SUCCESS(HttpStatus.OK,"PROMOTION_2006","프로모션 삭제가 완료되었습니다."),

    // Congestion
    CONGESTION_INPUT_SUCCESS(HttpStatus.OK, "CONGESTION_2011", "혼잡도 입력이 완료되었습니다."),

    // Point
    BUY_POINT_COUPON_SUCCESS(HttpStatus.OK, "POINT_2001", "포인트 스토어 내 상품 구매가 완료되었습니다."),
    MY_POINT_COUPON_LIST_SUCCESS(HttpStatus.OK, "POINT_2002", "사용자가 보유한 포인트 쿠폰 리스트가 완료되었습니다."),

    // Search
    SEARCH_HOME_SUCCESS(HttpStatus.OK, "SEARCH_2001", "홈 화면 검색 결과 반환이 완료되었습니다."),

    // Menu
    MENU_DELETE_SUCCESS(HttpStatus.OK,"MENU_2001","메뉴 삭제가 완료되었습니다."),
    MENU_UPDATE_SUCCESS(HttpStatus.OK,"MENU_2002", "메뉴 수정이 완료되었습니다."),
    MENU_REGISTER_SUCCESS(HttpStatus.OK,"MENU_2003","메뉴 등록이 완료되었습니다."),
    POINT_MENU_INQUIRY_SUCCESS(HttpStatus.OK,"MENU_2004","포인트 메뉴 조회가 완료되었습니다."),

    // Data Analysis
    DATA_ANALYSIS_ABOUT_VISIT(HttpStatus.OK, "ANALYSIS_2001", "카페 방문자 관련 데이터 분석 이미지를 반환이 완료되었습니다."),
    DATA_ANALYSIS_ABOUT_FAVORITE(HttpStatus.OK, "ANALYSIS_2002", "카페 즐겨찾기 관련 데이터 분석 이미지를 반환이 완료되었습니다."),
    DATA_ANALYSIS_ABOUT_CONGESTION(HttpStatus.OK, "ANALYSIS_2003", "카페 혼잡도 관련 데이터 분석 이미지를 반환이 완료되었습니다.")
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
