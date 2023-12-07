package cn.com.wanshi.chat.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author zzc
 * @since 2023-10-08
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ImMessageData对象", description="用户信息表")
public class ImMessageData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "发送方id")
    private String fromId;

    @ApiModelProperty(value = "接收方Id")
    private String toId;

    @ApiModelProperty(value = "消息数据拥有者（发送方和接收方都会有一条记录）")
    private String ownerId;

    @ApiModelProperty(value = "发送方类型(1 普通用户 2 系统公告 3 系统标识)")
    private Integer fromType;

    @ApiModelProperty(value = "接收方类型(1 普通用户 2 系统公告全部用户 3 群用户)")
    private Integer toType;

    @ApiModelProperty(value = "消息类型(1 文本 2 录音 3 图片 4 视频 5 语音通话  6 视频通话 7 登录 8 登出 9 心跳)")
    private Integer messageType;

    @ApiModelProperty(value = "消息内容")
    private String meesageData;

    @ApiModelProperty(value = "是否已读 0未读 1已读")
    private Integer realStatus;

    @ApiModelProperty(value = "是否已发 0未发 1已发")
    private Integer sendStatus;

    @ApiModelProperty(value = "消息发送时间")
    private Date messageTime;

    @ApiModelProperty(value = "生成时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
