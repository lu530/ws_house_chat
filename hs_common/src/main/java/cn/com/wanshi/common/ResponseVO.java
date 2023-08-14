package cn.com.wanshi.common;

/**
 * @author li
 * @data 2023/4/9
 * @time 11:03
 */

import cn.com.wanshi.common.exception.ApplicationExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 公共返回实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public class ResponseVO<T> implements Serializable {
    // 状态码
    private int status;

    private String message;

    private T data;

    public static ResponseVO successResponse(Object data) {
        return new ResponseVO(200, "success", data);
    }

    public static ResponseVO successResponse() {
        return new ResponseVO(200, "success");
    }

    public static ResponseVO errorResponse() {
        return new ResponseVO(500, "系统内部异常");
    }

    public static ResponseVO errorResponse(int code, String msg) {
        return new ResponseVO(code, msg);
    }

    public static ResponseVO errorResponse(ApplicationExceptionEnum enums) {
        return new ResponseVO(enums.getCode(), enums.getError());
    }

    public boolean isOk(){
        return this.status == 200;
    }


    public ResponseVO(int code, String msg) {
        this.status = code;
        this.message = msg;
//		this.data = null;
    }

    public ResponseVO success(){
        this.status = 200;
        this.message = "success";
        return this;
    }

    public ResponseVO success(T data){
        this.status = 200;
        this.message = "success";
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "ResponseVO{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

