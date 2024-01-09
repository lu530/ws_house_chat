package cn.com.wanshi.chat.message.model.resp;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhongzhicheng
 * 消息列表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImFriendMessagesResp {


    @ApiModelProperty(value = "用户账号")
    private String account;

    @ApiModelProperty(value = "头像url")
    private String photo;


    @ApiModelProperty(value = "用户名称")
    private String nickName;

    @ApiModelProperty(value = "好友备注名称")
    private String friendNickName;

    @ApiModelProperty(value = "发送方id")
    private String fromId;

    @ApiModelProperty(value = "发送方id")
    private String toId;


    @ApiModelProperty(value = "是否已读 0未读 1已读")
    private Integer realStatus;

    @ApiModelProperty(value = "接收方类型(1 普通用户 2 系统公告全部用户 3 群用户)")
    private Integer fromType;


    @ApiModelProperty(value = "消息发送时间")
    private Date messageTime;

    @ApiModelProperty(value = "消息发送时间")
    private String messageTimeStr;

    @ApiModelProperty(value = "消息内容")
    private String meesageData;

}
