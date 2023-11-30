package cn.com.wanshi.chat.friendship.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 好友申请信息表
 * </p>
 *
 * @author zzc
 * @since 2023-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ImFriendshipRequest对象", description="好友申请信息表")
public class ImFriendshipRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "appid")
    private Long appId;

    @ApiModelProperty(value = "添加方用户id")
    private String fromId;

    @ApiModelProperty(value = "被添加方用户id")
    private String toId;

    @ApiModelProperty(value = "好友名称备注")
    private String friendNickName;

    @ApiModelProperty(value = "申请说明")
    private String applyReason;

    @ApiModelProperty(value = "是否已读 （1已读 0 未读)")
    private Integer readStatus;

    @ApiModelProperty(value = "好友来源 1账号 2 邮箱 3 手机号 4推荐 5扫描")
    private Integer addSource;

    @ApiModelProperty(value = "审批状态 1同意 2拒绝 3拉入黑名单")
    private Integer approveStatus;

    @ApiModelProperty(value = "不让他看我 1 是 0 否")
    private Integer forbidFromSocialCircle;

    @ApiModelProperty(value = "不看他 1 是 0 否")
    private Integer forbidToSocialCircle;

    @ApiModelProperty(value = "是否删除1是 0否")
    private Integer delFlag;

    @ApiModelProperty(value = "生成时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
