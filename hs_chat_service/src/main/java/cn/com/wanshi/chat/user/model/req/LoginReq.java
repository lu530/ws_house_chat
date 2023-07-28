package cn.com.wanshi.chat.user.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhongzhicheng
 */
@Data
public class LoginReq{


    /**
     * 登录账号
     */
    @ApiModelProperty(value = "登录账号")
    @NotBlank
    private String account;


    /**
     * 登录密码
     */
    @ApiModelProperty(value = "登录密码")
    @NotBlank
    private String password;


    @ApiModelProperty(value = "来源 IOS ANDROID  WX")
    private String clientType;

    @ApiModelProperty(value = "设备ID")
    private String cuid;

}
