package cn.com.wanshi.chat.friendship.model.req;

import cn.com.wanshi.chat.common.model.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhongzhicheng
 */
@Data
public class FriendRequestCountReq extends BaseReq {

    @ApiModelProperty(value = "是否已读 （1已读 0 未读)")
    private Integer readStatus = 0;

}
