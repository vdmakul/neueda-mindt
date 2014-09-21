package lv.vdmakul.mindt.rest.controller.response;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    public final String reasonPhrase;
    public final String errorCode;
    public final String message;

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.message = message;
        reasonPhrase = httpStatus.getReasonPhrase();
        errorCode = httpStatus.toString();
    }
}
