package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class LOException extends Exception {

    protected static final Logger logger = LoggerFactory.getLogger(LOException.class);

    private Integer code;
    private String message;

    public LOException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public LOException(Integer code, String message, Exception ex) {
        this.code = code;
        this.message = message;
        logger.error("Exception Cause : " + ex.getMessage());
        logger.error("Exception Stacktrace : " +  Arrays.asList(ex.getStackTrace()).toString());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
