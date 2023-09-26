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
 * TOKEN
 * </p>
 *
 * @author zzc
 * @since 2023-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ImFriendship对象", description="好友关系表")
public class ImFriendship implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "添加方用户id")
    private String fromId;

    @ApiModelProperty(value = "接受方用户id")
    private String toId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "好友备注名称")
    private String friendNickName;

    @ApiModelProperty(value = "状态 1正常 2删除")
    private Integer status;

    @ApiModelProperty(value = "状态 1正常 2拉黑")
    private Integer black;

    @ApiModelProperty(value = "不让他看我 1 是 0 否")
    private Integer forbidFromSocialCircle;

    @ApiModelProperty(value = "不看他 1 是 0 否")
    private Integer forbidToSocialCircle;

    @ApiModelProperty(value = "好友关系序列号")
    @TableField("friendSequence")
    private Long friendSequence;

    @ApiModelProperty(value = "黑名单关系序列号")
    @TableField("blackSequence")
    private Long blackSequence;

    @ApiModelProperty(value = "好友来源")
    @TableField("addSource")
    private Integer addSource;

    @ApiModelProperty(value = "生成时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
