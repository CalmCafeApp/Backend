package kau.CalmCafe.global.api_payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseCode {
    // Common
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 에러, 서버 개발자에게 문의하세요."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_4041", "존재하지 않는 회원입니다."),
    USER_NOT_FOUND_BY_EMAIL(HttpStatus.NOT_FOUND, "USER_4042", "존재하지 않는 회원입니다.-EMAIL"),
    USER_NOT_FOUND_BY_USERNAME(HttpStatus.NOT_FOUND, "USER_4043", "존재하지 않는 회원입니다.-USERNAME"),
    ALREADY_USED_NICKNAME(HttpStatus.FORBIDDEN, "USER_4031", "이미 사용중인 닉네임입니다."),
    USER_ADDRESS_NULL(HttpStatus.BAD_REQUEST, "USER_4001", "주소값이 비었거나 NULL입니다."),
    ALREADY_SURVEY(HttpStatus.BAD_REQUEST, "USER_4002", "설문 조사에 이미 참여했습니다."),

    // Jwt
    WRONG_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "JWT_4041", "일치하는 리프레시 토큰이 없습니다."),
    TOKEN_INVALID(HttpStatus.FORBIDDEN, "JWT_4032", "유효하지 않은 토큰입니다."),
    TOKEN_NO_AUTH(HttpStatus.FORBIDDEN, "JWT_4033", "권한 정보가 없는 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT_4011", "토큰 유효기간이 만료되었습니다."),

    // Common
    INVALID_FILE_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "COMMON_4001", "잘못된 파일 형식입니다."),
    MISMATCH_IMAGE_FILE(HttpStatus.BAD_REQUEST, "COMMON_4002", "파일 형식은 이미지(JPEG 또는 PNG)여야 합니다."),

    // Store
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE_4041", "존재하지 않는 매장입니다."),
    STORE_ALREADY_FAVORITE(HttpStatus.BAD_REQUEST, "STORE_4001", "이미 즐겨찾기한 매장입니다."),
    STORE_FAVORITE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE_4042", "존재하지 않는 매장 즐겨찾기 입니다."),
    STORE_CONGESTION_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "STORE_4002", "AI를 통한 매장 혼잡도 갱신에 실패했습니다."),


    // Menu
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "MENU_4041", "존재하지 않는 메뉴입니다."),
    MENU_IMAGE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "MENU_5001", "메뉴 이미지 업로드에 실패하였습니다."),

    // Point
    POINT_BUY_FAILED(HttpStatus.BAD_REQUEST, "POINT_4001", "포인트가 부족합니다."),

    // Cafe
    CAFE_NOT_FOUND(HttpStatus.NOT_FOUND, "CAFE_4041", "존재하지 않는 카페입니다."),
    NO_CAFES_FOUND(HttpStatus.NOT_FOUND, "CAFE_4042", "등록된 카페가 없습니다."),
    CAFE_ALREADY_EXISTS(HttpStatus.CONFLICT, "CAFE_4043", "이미 존재하는 카페입니다."),

    // Promotion
    PROMOTION_NOT_FOUND(HttpStatus.NOT_FOUND, "PROMOTION_4041", "존재하지 않는 프로모션입니다."),
    PROMOTION_ALREADY_FAVORITE(HttpStatus.BAD_REQUEST, "PROMOTION_4001", "이미 이용 완료한 프로모션입니다."),

    // Research
    HOME_RESEARCH_NOT_FOUND(HttpStatus.NOT_FOUND, "RESEARCH_404", "검색 키워드에 해당하는 매장이 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .httpStatus(this.httpStatus)
                .isSuccess(false)
                .code(this.code)
                .message(this.message)
                .build();
    }
}
