package dy.utils.libhttp.httpservice.exception;

public class ResultException extends RuntimeException {
    private int errCode = 0;

    public ResultException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return this.errCode;
    }
}
