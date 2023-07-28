package cn.com.wanshi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhongzhicheng
 */
@Getter
@AllArgsConstructor
public enum ReceiptCodeTypeEnum {

    /**
     * 注册邮箱验证码
     */
    SIGNUP_EMAIL_RECEIPTCODE(1, "注册邮箱验证码","尊敬的用户您好!\n\n感谢您注册***。\n\n尊敬的: {0}您的校验验证码为: {1},有效期5分钟，请不要把验证码信息泄露给其他人,如非本人请勿操作!" ),
    RE_SET_PASS_EMAIL_RECEIPTCODE(2, "重置密码邮箱验证码", "尊敬的用户您好!\n\n感谢您使用***。\n\n尊敬的: {0}您的校验验证码为: {1},有效期5分钟，请不要把验证码信息泄露给其他人,如非本人请勿操作!");

    private Integer receiptCodeType;


    private String receiptCodeTypeName;

    private String message;



    public static ReceiptCodeTypeEnum getEnumByReceiptCodeType(Integer receiptCodeType){
        for(ReceiptCodeTypeEnum e : ReceiptCodeTypeEnum.values()){
            if(e.receiptCodeType.equals(receiptCodeType) ){
                return e;
            }
        }
        return null;
    }




}
