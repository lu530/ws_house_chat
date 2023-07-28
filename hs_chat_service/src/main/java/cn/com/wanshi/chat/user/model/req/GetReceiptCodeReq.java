package cn.com.wanshi.chat.user.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhongzhicheng
 * 获取验证码请求类
 */
@Data
public class GetReceiptCodeReq{


    /**
     * @cn.com.wanshi.common.enums.ReceiptCodeTypeEnum
     */
    @ApiModelProperty(value = "验证码类型 1 注册邮箱验证码 2 重置密码邮箱验证码")
    @NotNull
    private Integer receiptCodeType;



    @ApiModelProperty(value = "邮箱地址")
    @NotBlank
    private String email;




}
