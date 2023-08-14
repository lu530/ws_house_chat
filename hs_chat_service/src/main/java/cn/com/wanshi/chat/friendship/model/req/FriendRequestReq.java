package cn.com.wanshi.chat.friendship.model.req;

import cn.com.wanshi.chat.common.model.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhongzhicheng
 */
@Data
public class FriendRequestReq extends BaseReq {

    @ApiModelProperty(value = "被添加方用户id")
    @NotBlank
    private String toId;


    @ApiModelProperty(value = "好友名称备注")
    @NotBlank
    private String friendNickName;

    @ApiModelProperty(value = "申请说明")
    @NotBlank
    private String applyReason;

    @ApiModelProperty(value = "好友来源 1账号 2 邮箱 3 手机号 4推荐 5扫描")
    @NotNull
    private Integer addSource;

    @ApiModelProperty(value = "不让他看我 1 是 0 否")
    @NotNull
    private Boolean forbidFromSocialCircle;

    @ApiModelProperty(value = "不看他 1 是 0 否")
    @NotNull
    private Boolean forbidToSocialCircle;
}
