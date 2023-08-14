package cn.com.wanshi.chat.friendship.model.resp;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author admin
 */
@Data
public class FriendRequestListResp {

    /**
     * 近三天
     */
    @ApiModelProperty(value = "近三天")
    List<FriendRequestHistoryListResp> inThreeDaysList;


    /**
     * 近三天
     */
    @ApiModelProperty(value = "三天前")
    List<FriendRequestHistoryListResp> beforeThreeDaysList;













}
