package cn.com.wanshi.chat.common.model;

import cn.com.wanshi.chat.user.entity.ImUserData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 对接前端的基础入参
 * @author zhongzhicheng
 * @date 2023-05-05
 */
@Data
public class BaseReq {

    @ApiModelProperty(value = "登录凭证", required = true)
    @NotBlank(message = "登录凭证不能为空")
    private String token;

    @ApiModelProperty(value = "用户ID")
    private String userId;


    @ApiModelProperty(value = "用户数据")
    private ImUserData imUserData;


}
