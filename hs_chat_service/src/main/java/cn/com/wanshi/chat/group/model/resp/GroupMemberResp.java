package cn.com.wanshi.chat.group.model.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zzc
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberResp implements Serializable {

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
    private String nickName;

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



}
