package lv.vdmakul.mindt.rest.controller.response;

public class MindtException extends RuntimeException {

    public MindtException(String message) {
        super(message);
    }

    public MindtException(String message, Throwable cause) {
        super(message, cause);
    }
}
