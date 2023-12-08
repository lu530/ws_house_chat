package cn.com.wanshi.chat.message.service.impl;

import cn.com.wanshi.chat.common.annotation.ValidateToken;
import cn.com.wanshi.chat.common.constants.General;
import cn.com.wanshi.chat.common.enums.MessageFromUserTypeEnum;
import cn.com.wanshi.chat.common.enums.MessageToUserTypeEnum;
import cn.com.wanshi.chat.common.enums.MessageTypeEnum;
import cn.com.wanshi.chat.friendship.entity.ImFriendshipRequest;
import cn.com.wanshi.chat.friendship.model.resp.FriendRequestCountResp;
import cn.com.wanshi.chat.message.entity.ImMessageData;
import cn.com.wanshi.chat.message.mapper.ImMessageDataMapper;
import cn.com.wanshi.chat.message.model.req.ImFriendMessagesReq;
import cn.com.wanshi.chat.message.model.req.ImMessageCountReq;
import cn.com.wanshi.chat.message.model.req.ImMessageListReq;
import cn.com.wanshi.chat.message.model.req.ImMessageReq;
import cn.com.wanshi.chat.message.model.resp.ImFriendMessagesResp;
import cn.com.wanshi.chat.message.model.resp.ImMessageCountResp;
import cn.com.wanshi.chat.message.model.resp.ImMessageResp;
import cn.com.wanshi.chat.message.service.IImMessageDataService;
import cn.com.wanshi.common.ResponseVO;
import cn.com.wanshi.common.enums.YesNoEnum;
import cn.com.wanshi.common.utils.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.channel.ChannelId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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


    @Autowired
    private ImMessageDataMapper imMessageDataMapper;


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
            case PICTURE:
                return picture(imMessageReq, channelId);
            default:
                return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addImMessageData(ImMessageResp imMessageResp) {
        MessageTypeEnum enumByType = MessageTypeEnum.getEnumByType(imMessageResp.getMessageType());
        //判断是否需要保存消息数据
        if(!enumByType.needPersistence){
            return;
        }
        ImMessageData toImMessageData = new ImMessageData();
        ImMessageData fromImMessageData = new ImMessageData();
        BeanUtils.copyProperties(imMessageResp, toImMessageData);
        BeanUtils.copyProperties(imMessageResp, fromImMessageData);
        Date now = new Date();
        toImMessageData.setCreateTime(now);
        fromImMessageData.setCreateTime(now);
        toImMessageData.setOwnerId(imMessageResp.getToId());
        fromImMessageData.setOwnerId(imMessageResp.getFromId());
        this.saveBatch(Arrays.asList(toImMessageData, fromImMessageData));
    }

    @Override
    public ResponseVO<List<ImMessageResp>> messageList(ImMessageListReq req) {
        LambdaQueryWrapper<ImMessageData> lqw = new LambdaQueryWrapper<>();

        if(StringUtils.isNotEmpty(req.getOwnerId())){
            lqw.eq(ImMessageData::getOwnerId, req.getOwnerId());
        }

        if(StringUtils.isNotEmpty(req.getToId())){
            lqw.eq(ImMessageData::getToId, req.getToId());
        }

        if(Objects.nonNull(req.getSendStatus())){
            lqw.eq(ImMessageData::getSendStatus, req.getSendStatus());
        }

        if(Objects.nonNull(req.getFromIdNEqOwerIdFlag()) && req.getFromIdNEqOwerIdFlag() && StringUtils.isNotEmpty(req.getOwnerId())){
            lqw.ne(ImMessageData::getFromId, req.getOwnerId());
        }
        List<ImMessageData> list = this.list(lqw);

        if(Objects.nonNull(req.getSendStatus()) && req.getSendStatus().equals(YesNoEnum.NO.value)){
            ImMessageData build = ImMessageData.builder().sendStatus(YesNoEnum.YES.value).build();
            //发送状态更新为已经发
            this.update(build, lqw);
        }
        return ResponseVO.successResponse(list);
    }

    @Override
    public ResponseVO<List<ImMessageCountResp>> messageCount(ImMessageCountReq req) {
        List<ImMessageCountResp> result = imMessageDataMapper.messageCount(req);
        return ResponseVO.successResponse(result);
    }

    @Override
    public ResponseVO<List<ImFriendMessagesResp>> friendMessages(ImFriendMessagesReq req) {
        List<ImFriendMessagesResp> list = imMessageDataMapper.friendMessages(req);
        Date now = new Date();
        list = list.stream().map(m -> {
            Date messageTime = m.getMessageTime();
            int messageTimedd = Integer.parseInt(DateUtil.getDateString(messageTime, "dd"));
            int dd = Integer.parseInt(DateUtil.getDateString(now, "dd"));
            int timeDiff = dd - messageTimedd;
            String messageTimeStr = DateUtil.getDateString(messageTime, "yy/MM/dd");
            //一天内显示时间 昨天起的显示
            if(timeDiff < 1){
                messageTimeStr = DateUtil.getDateString(messageTime, "HH:mm");
            }else if(timeDiff < 2){
                //昨天
                messageTimeStr = "昨天";
            }
            m.setMessageTimeStr(messageTimeStr);
            return m;
        }).collect(Collectors.toList());
        return ResponseVO.successResponse(list);
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


    private ResponseVO<ImMessageResp> picture(ImMessageReq imMessageReq, ChannelId channelId) {
        ImMessageResp build = ImMessageResp.builder()
                .messageType(MessageTypeEnum.PICTURE.getType())
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
