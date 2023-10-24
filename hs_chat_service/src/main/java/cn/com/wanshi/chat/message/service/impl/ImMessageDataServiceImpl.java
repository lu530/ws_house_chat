package cn.com.wanshi.chat.message.service.impl;

import cn.com.wanshi.chat.common.annotation.ValidateToken;
import cn.com.wanshi.chat.common.constants.General;
import cn.com.wanshi.chat.common.enums.MessageFromUserTypeEnum;
import cn.com.wanshi.chat.common.enums.MessageToUserTypeEnum;
import cn.com.wanshi.chat.common.enums.MessageTypeEnum;
import cn.com.wanshi.chat.message.entity.ImMessageData;
import cn.com.wanshi.chat.message.mapper.ImMessageDataMapper;
import cn.com.wanshi.chat.message.model.req.ImMessageReq;
import cn.com.wanshi.chat.message.model.req.ImMessageResp;
import cn.com.wanshi.chat.message.service.IImMessageDataService;
import cn.com.wanshi.common.ResponseVO;
import cn.com.wanshi.common.enums.YesNoEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.channel.ChannelId;
import org.springframework.stereotype.Service;

import java.util.Date;

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
                return text(imMessageReq, channelId);
            default:
                return null;
        }
    }

    private ResponseVO<ImMessageResp> login(ImMessageReq imMessageReq, ChannelId channelId){
        General.userIdChannelIdHashMap.put(imMessageReq.getUserId(), channelId);
        General.channelIdUserIdHashMap.put(channelId, imMessageReq.getUserId());
        ImMessageResp build = ImMessageResp.builder()
                .messageType(MessageTypeEnum.LOGIN.getType())
                .fromType(MessageFromUserTypeEnum.NORMAL_USER.getType())
                .fromId(imMessageReq.getUserId())
                .toId(imMessageReq.getUserId())
                .realStatus(YesNoEnum.YES.value)
                .toType(MessageToUserTypeEnum.NORMAL_USER.getType())
                .meesageData("登录成功")
                .messageTime(new Date())
                .build();
        return ResponseVO.successResponse(build);
    }


    private ResponseVO<ImMessageResp> text(ImMessageReq imMessageReq, ChannelId channelId){
        ImMessageResp build = ImMessageResp.builder()
                .messageType(MessageTypeEnum.TEXT.getType())
                .fromType(MessageFromUserTypeEnum.NORMAL_USER.getType())
                .fromId(imMessageReq.getUserId())
                .toId(imMessageReq.getToId())
                .realStatus(YesNoEnum.NO.value)
                .toType(MessageToUserTypeEnum.NORMAL_USER.getType())
                .meesageData(imMessageReq.getMeesageData())
                .messageTime(new Date())
                .build();
        return ResponseVO.successResponse(build);
    }

}
