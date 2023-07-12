package cn.com.wanshi.common.exception;

/**
 * @author li
 * @data 2023/4/9
 * @time 10:41
 */

/**
 * 全局异常处理类
 */
public class ApplicationException extends RuntimeException {
    // 状态码
    private int code;
    // error信息
    private String error;

    public ApplicationException(int code, String message){
        super(message);
        this.code = code;
        this.error = message;
    }

    public ApplicationException(ApplicationExceptionEnum exceptionEnum){
        super(exceptionEnum.getError());
        this.code = exceptionEnum.getCode();
        this.error = exceptionEnum.getError();
    }

    public int getCode(){
        return code;
    }

    public String getError(){
        return error;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
