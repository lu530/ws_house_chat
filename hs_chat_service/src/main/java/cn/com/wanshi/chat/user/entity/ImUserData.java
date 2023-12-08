package cn.com.wanshi.chat.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author zzc
 * @since 2023-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ImUserData对象", description="用户信息表")
public class ImUserData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    @ApiModelProperty(value = "密码")
    private String password;

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

    @ApiModelProperty(value = "是否删除 1是 0否")
    private Integer delFlag;


    @ApiModelProperty(value = "生成时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
