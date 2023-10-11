package cn.com.wanshi.chat.message.service;

import cn.com.wanshi.chat.message.entity.ImMessageData;
import cn.com.wanshi.chat.message.model.req.ImMessageReq;
import cn.com.wanshi.chat.message.model.req.ImMessageResp;
import cn.com.wanshi.common.ResponseVO;
import com.baomidou.mybatisplus.extension.service.IService;
import io.netty.channel.ChannelId;

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
}
