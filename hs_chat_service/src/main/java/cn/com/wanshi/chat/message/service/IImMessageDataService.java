package cn.com.wanshi.chat.message.service;

import cn.com.wanshi.chat.message.entity.ImMessageData;
import cn.com.wanshi.chat.message.model.req.*;
import cn.com.wanshi.chat.message.model.resp.ImFriendMessagesResp;
import cn.com.wanshi.chat.message.model.resp.ImMessageCountResp;
import cn.com.wanshi.chat.message.model.resp.ImMessageResp;
import cn.com.wanshi.common.ResponseVO;
import com.baomidou.mybatisplus.extension.service.IService;
import io.netty.channel.ChannelId;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author zzc
 * @since 2023-10-08
 */
public interface IImMessageDataService extends IService<ImMessageData> {


    /**
     * 消息处理
     * @param imMessageReq
     * @return
     */
    ResponseVO<ImMessageResp> handler(ImMessageReq imMessageReq, ChannelId channelId);

    /**
     * 新增即时消息落库
     * @param imMessageResp
     */
    void addImMessageData(ImMessageResp imMessageResp);

    /**
     * 获取聊天消息列表
     * @param req
     * @return
     */
    ResponseVO<List<ImMessageResp>> messageList(ImMessageListReq req);


    /**
     * 获取群聊天消息列表
     * @param req
     * @return
     */
    ResponseVO<List<ImMessageResp>> groupMessageList(ImGroupMessageListReq req);

    /**
     * 获取聊天消息列表统计数
     * @param req
     * @return
     */
    ResponseVO<List<ImMessageCountResp>> messageCount(ImMessageCountReq req);

    /**
     * 获取好友消息列表接口
     * @param req
     * @return
     */
    ResponseVO<List<ImFriendMessagesResp>> friendMessages(ImFriendMessagesReq req);


}
