package dy.utils.libhttp.exception;

public class ApiException extends Exception {
    private final int code;
    private String displayMessage;
    public static final int UNKNOWN = 1000;
    public static final int PARSE_ERROR = 1001;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.displayMessage = throwable.getMessage();
    }

    public int getCode() {
        return this.code;
    }

    public String getDisplayMessage() {
        return this.displayMessage;
    }

    public void setDisplayMessage(String msg) {
        this.displayMessage = msg + "(code:" + this.code + ")";
    }
}