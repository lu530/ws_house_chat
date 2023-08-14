package cn.com.wanshi.chat.friendship.model.resp;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendResp {


    /*@ApiModelProperty(value = " 0未请求 1同意 2拒绝 3 请求中 4 已经过期")
    private Integer approveStatus;*/

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户账号")
    private String account;


    @ApiModelProperty(value = "用户名称")
    private String nickName;

    @ApiModelProperty(value = "邮箱地址")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "用户地址")
    private String location;

    @ApiModelProperty(value = "生日")
    private Date birthDay;


    @ApiModelProperty(value = "头像url")
    private String photo;

    @ApiModelProperty(value = "性别（1：男，2：女）")
    private Integer userSex;

    @ApiModelProperty(value = "个性签名")
    private String selfSignature;

    @ApiModelProperty(value = "加好友验证类型（1需要验证 0不需要验证)")
    private Integer friendAllowType;

    @ApiModelProperty(value = "管理员禁止用户添加加好友：0 未禁用 1 已禁用")
    private Integer disableAddFriend;

    @ApiModelProperty(value = "禁用标识(0 未禁用 1 已禁用)")
    private Integer forbiddenFlag;

    @ApiModelProperty(value = "禁言标识(0 否 1 是)")
    private Integer silentFlag;

    @ApiModelProperty(value = "用户类型 1普通用户 2客服 3机器人")
    private Integer userType;


}
