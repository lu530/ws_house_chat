package cn.com.wanshi.common.enums;


/**
 * @author zhongzhicheng
 * @date 2023/7/31
 */
public enum ApiCode {

    /**
     * 用户相关
     */
    TOKEN_INVALID(1301) {
        @Override
        public String getMessage() {
            //原为：token失效
            return "登录超时或已在其他设备登录，请重新登陆";
        }
    },


    USER_NO_FIND(1302) {
        @Override
        public String getMessage() {
            //原为：token失效
            return "该用户不存在";
        }
    },



    // ################### 用户结束#########################
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
