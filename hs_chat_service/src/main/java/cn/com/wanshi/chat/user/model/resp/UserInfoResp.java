package cn.com.wanshi.chat.user.model.resp;

import cn.com.wanshi.chat.user.entity.ImUserData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhongzhicheng
 * @data 2023/7/5
 * @time 13:32
 */
@Data
public class UserInfoResp extends ImUserData {

    @ApiModelProperty(value = "登录令牌")
    private String token;


}
