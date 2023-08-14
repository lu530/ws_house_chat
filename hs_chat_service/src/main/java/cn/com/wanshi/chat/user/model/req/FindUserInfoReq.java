package cn.com.wanshi.chat.user.model.req;

import cn.com.wanshi.chat.common.model.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhongzhicheng
 */
@Data
public class FindUserInfoReq extends BaseReq {

    @ApiModelProperty(value = "账号 邮箱 手机号 搜索 精准搜索字段")
    private String accountWords;


}
