package cn.com.wanshi.common.exception;

/**
 * @author li
 * @data 2023/4/9
 * @time 10:40
 */
/**
 * 全局异常处理的枚举
 */

public interface ApplicationExceptionEnum {
    // 状态码
    int getCode();
    // error信息
    String getError();
}
