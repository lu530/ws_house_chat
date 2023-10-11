package cn.com.wanshi.chat.message.service.impl;

import cn.com.wanshi.chat.common.annotation.ValidateToken;
import cn.com.wanshi.chat.common.constants.General;
import cn.com.wanshi.chat.common.enums.MessageTypeEnum;
import cn.com.wanshi.chat.message.entity.ImMessageData;
import cn.com.wanshi.chat.message.mapper.ImMessageDataMapper;
import cn.com.wanshi.chat.message.model.req.ImMessageReq;
import cn.com.wanshi.chat.message.model.req.ImMessageResp;
import cn.com.wanshi.chat.message.service.IImMessageDataService;
import cn.com.wanshi.common.ResponseVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.channel.ChannelId;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zzc
 * @since 2023-10-08
 */
@Service
public class ImMessageDataServiceImpl extends ServiceImpl<ImMessageDataMapper, ImMessageData> implements IImMessageDataService {


    /**
     * 消息处理类
     *
     * @param imMessageReq
     * @return
     */
    @Override
    @ValidateToken
    public ResponseVO<ImMessageResp> handler(ImMessageReq imMessageReq, ChannelId channelId) {
        MessageTypeEnum enumByType = MessageTypeEnum.getEnumByType(imMessageReq.getMessageType());
        switch (enumByType){
            /**
             * 登录消息处理
             * 需要维护userId 和channalId的关联
             */
            case LOGIN:
                return login(imMessageReq, channelId);
            case TEXT:
            default:
                return null;
        }
    }

    private ResponseVO<ImMessageResp> login(ImMessageReq imMessageReq, ChannelId channelId){
        General.userIdChannelIdHashMap.put(imMessageReq.getUserId(), channelId);
        General.channelIdUserIdHashMap.put(channelId, imMessageReq.getUserId());

        return ResponseVO.successResponse(ImMessageResp.builder().messageType(MessageTypeEnum.LOGIN.getType()).build());
    }

}
