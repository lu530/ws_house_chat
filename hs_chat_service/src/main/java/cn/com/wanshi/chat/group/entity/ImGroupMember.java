package cn.com.wanshi.chat.group.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 群成员关联表
 * </p>
 *
 * @author zzc
 * @since 2023-12-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ImGroupMember对象", description="群成员关联表")
public class ImGroupMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "群id")
    private String groupMemberId;

    @ApiModelProperty(value = "群id")
    private String groupId;

    @ApiModelProperty(value = "成员id")
    private String memberId;

    @ApiModelProperty(value = "头像url")
    private String photo;

    @ApiModelProperty(value = "是否全员禁言，0 不禁言；1 全员禁言。")
    private Integer mute;

    @ApiModelProperty(value = "群成员类型，0 普通成员, 1 管理员, 2 群主， 3 禁言")
    private Integer role;

    @ApiModelProperty(value = "群昵称")
    private String alias;

    @ApiModelProperty(value = "加入时间")
    private Date joinTime;

    @ApiModelProperty(value = "离开时间")
    private Date leaveTime;


    @ApiModelProperty(value = "是否已经离群 0 否 1 是")
    private Integer leaveFlag;

    @ApiModelProperty(value = "加入方式 1 搜索群, 2 好友拉, 3 扫描进群")
    private Integer joinType;

    @ApiModelProperty(value = "介绍人(被何人邀请)")
    private String introducer;

    @ApiModelProperty(value = "生成时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
