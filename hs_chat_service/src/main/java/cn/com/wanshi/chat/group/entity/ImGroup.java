package cn.com.wanshi.chat.group.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 群消息表
 * </p>
 *
 * @author zzc
 * @since 2023-12-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ImGroup对象", description="群消息表")
public class ImGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "群id")
    private String groupId;

    @ApiModelProperty(value = "群主id")
    private String ownerId;

    @ApiModelProperty(value = "群类型 1私有群（类似微信） 2公开群(类似qq)")
    private Boolean groupType;

    @ApiModelProperty(value = "群名称")
    private String groupName;

    @ApiModelProperty(value = "是否全员禁言，0 不禁言；1 全员禁言。")
    private Boolean mute;

    @ApiModelProperty(value = "申请加群选项包括如下几种:0 表示禁止任何人申请加入、1 表示需要群主或管理员审批、2 表示允许无需审批自由加入群组")
    private Boolean applyJoinType;

    @ApiModelProperty(value = "群简介")
    private String introduction;

    @ApiModelProperty(value = "群公告")
    private String notification;

    @ApiModelProperty(value = "群头像")
    private String photo;

    @ApiModelProperty(value = "群成员上限")
    private Integer maxMemberCount;

    @ApiModelProperty(value = "群状态 0正常 1解散")
    private Boolean status;

    @ApiModelProperty(value = "生成时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
