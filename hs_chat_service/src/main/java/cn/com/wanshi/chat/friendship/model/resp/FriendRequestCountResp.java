package cn.com.wanshi.chat.friendship.model.resp;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author admin
 */
@Data
@Builder
public class FriendRequestCountResp {


    @ApiModelProperty(value = "好友申请数")
    private Integer count;



}
