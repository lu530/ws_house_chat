package cn.com.wanshi.chat.group.model.resp;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author admin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupInfoResp {
    @ApiModelProperty(value = "群主id")
    private String ownerId;

    @ApiModelProperty(value = "群类型 1私有群（类似微信） 2公开群(类似qq)")
    private Integer groupType;

    /**
     * 群名称
     */
    @ApiModelProperty(value = "群名称")
    private String groupName;


    @ApiModelProperty(value = "群头像")
    private String photo;


    @ApiModelProperty(value = "群id")
    private String groupId;


    @ApiModelProperty(value = "是否是自定义群名称 0 否 1 是")
    private Integer customNameFlag;

    @ApiModelProperty(value = "是否全员禁言，0 不禁言；1 全员禁言。")
    private Integer mute;

    @ApiModelProperty(value = "申请加群选项包括如下几种:0 表示禁止任何人申请加入、1 表示需要群主或管理员审批、2 表示允许无需审批自由加入群组")
    private Integer applyJoinType;

    @ApiModelProperty(value = "群简介")
    private String introduction;

    @ApiModelProperty(value = "群公告")
    private String notification;


    @ApiModelProperty(value = "群成员上限")
    private Integer maxMemberCount;

    @ApiModelProperty(value = "群状态 0正常 1解散")
    private Integer status;





}
