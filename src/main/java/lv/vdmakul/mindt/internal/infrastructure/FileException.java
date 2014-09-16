package lv.vdmakul.mindt.internal.infrastructure;

public class FileException extends RuntimeException {

    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
