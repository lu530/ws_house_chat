package cn.com.wanshi.common.enums;


/**
 * @author zhongzhicheng
 * @date 2023/7/31
 */
public enum ApiCode {

    TOKEN_INVALID(1301) {
        @Override
        public String getMessage() {
            //原为：token失效
            return "登录超时或已在其他设备登录，请重新登陆";
        }
    },
    ;

    private final int value;

    private ApiCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public abstract String getMessage();

    public String getMessage(String errorMsg) {
        return errorMsg + getMessage();
    }

}
