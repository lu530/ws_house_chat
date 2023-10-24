package cn.com.wanshi.chat.message.model.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhongzhicheng
 * 用户发消息请求类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImMessageResp {

    @ApiModelProperty(value = "发送方id")
    private String fromId;

    @ApiModelProperty(value = "接收方id")
    private String toId;

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

    @ApiModelProperty(value = "是否已读 0未读 1已读")
    private Integer realStatus;
}
