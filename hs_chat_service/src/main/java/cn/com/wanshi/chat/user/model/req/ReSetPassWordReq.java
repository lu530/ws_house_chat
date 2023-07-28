package cn.com.wanshi.chat.user.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhongzhicheng
 * 获取验证码请求类
 */
@Data
public class ReSetPassWordReq {


    @ApiModelProperty(value = "邮箱地址")
    @NotBlank(message = "邮箱地址不能为空")
    private String email;



    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String receiptCode;


    @ApiModelProperty(value = "登录密码")
    @NotBlank(message = "登录密码不能为空")
    private String password;


}
