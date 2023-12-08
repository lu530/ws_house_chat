package cn.com.wanshi.chat.message.model.req;


import cn.com.wanshi.chat.common.model.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zhongzhicheng
 * 消息列表请求类
 */
@Data
public class ImFriendMessagesReq extends BaseReq {


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

    @ApiModelProperty(value = "消息类型(1 文本 2 录音 3 图片 4 视频 5 语音通话 6 视频通话 7 登录 8 登出)")
    private Integer messageType;

    @ApiModelProperty(value = "消息内容")
    private String meesageData;

    @ApiModelProperty(value = "消息发送时间")
    private Date messageTime;


    @ApiModelProperty(value = "是否已发 0未发 1已发")
    private Integer sendStatus;


    @ApiModelProperty(value = "消息的发送者是否等于拥有者")
    private Boolean fromIdNEqOwerIdFlag;



}
