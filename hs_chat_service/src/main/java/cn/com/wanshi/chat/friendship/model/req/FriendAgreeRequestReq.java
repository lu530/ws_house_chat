package cn.com.wanshi.chat.friendship.model.req;

import cn.com.wanshi.chat.common.model.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhongzhicheng
 */
@Data
public class FriendAgreeRequestReq extends BaseReq {

    @ApiModelProperty(value = "请求方用户id")
    private String toId;


    @ApiModelProperty(value = "好友备注名称")
    private String friendNickName;

    @ApiModelProperty(value = "备注")
    private String remark;

/*    @ApiModelProperty(value = "状态 1正常 2删除")
    private Integer status;

    @ApiModelProperty(value = "状态 1正常 2拉黑")
    private Integer black;*/

    @ApiModelProperty(value = "好友来源")
    private Integer addSource;

    @ApiModelProperty(value = "不让他看我 1 是 0 否")
    private Integer forbidFromSocialCircle;

    @ApiModelProperty(value = "不看他 1 是 0 否")
    private Integer forbidToSocialCircle;



    @ApiModelProperty(value = "好友申请ID")
    private Long friendshipRequestId;

}
