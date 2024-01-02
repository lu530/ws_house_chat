package cn.com.wanshi.chat.message.controller;


import cn.com.wanshi.chat.common.annotation.ValidateToken;
import cn.com.wanshi.chat.friendship.model.req.FriendAgreeRequestReq;
import cn.com.wanshi.chat.friendship.model.resp.FriendAgreeRequestResp;
import cn.com.wanshi.chat.message.model.req.*;
import cn.com.wanshi.chat.message.model.resp.ImFriendMessagesResp;
import cn.com.wanshi.chat.message.model.resp.ImMessageCountResp;
import cn.com.wanshi.chat.message.model.resp.ImMessageResp;
import cn.com.wanshi.chat.message.service.IImMessageDataService;
import cn.com.wanshi.common.ResponseVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author zzc
 * @since 2023-10-08
 */
@RestController
@RequestMapping("/v1/im/message")
public class ImMessageDataController {

    @Autowired
    IImMessageDataService iImMessageDataService;


    @ApiOperation("获取消息列表接口")
    @PostMapping("/list")
    @ValidateToken
    public ResponseVO<List<ImMessageResp>> messageList(@RequestBody @Validated ImMessageListReq req){
        req.setOwnerId(req.getUserId());
        ResponseVO<List<ImMessageResp>>  result = iImMessageDataService.messageList(req);
        return result;
    }


    @ApiOperation("获取群的消息列表接口")
    @PostMapping("/group/list")
    @ValidateToken
    public ResponseVO<List<ImMessageResp>> groupMessageList(@RequestBody @Validated ImGroupMessageListReq req){
        ResponseVO<List<ImMessageResp>>  result = iImMessageDataService.groupMessageList(req);
        return result;
    }



    @ApiOperation("获取好友消息列表接口")
    @PostMapping("/friend/messages")
    @ValidateToken
    public ResponseVO<List<ImFriendMessagesResp>> friendMessages(@RequestBody @Validated ImFriendMessagesReq req){
        req.setOwnerId(req.getUserId());
        req.setToId(req.getUserId());
        req.setFromIdNEqOwerIdFlag(true);
        ResponseVO<List<ImFriendMessagesResp>>  result = iImMessageDataService.friendMessages(req);
        return result;
    }



    @ApiOperation("获取消息数量")
    @PostMapping("/count")
    @ValidateToken
    public ResponseVO<List<ImMessageCountResp>> messageCount(@RequestBody @Validated ImMessageCountReq req){
        req.setOwnerId(req.getUserId());
        ResponseVO<List<ImMessageCountResp>>  result = iImMessageDataService.messageCount(req);
        return result;
    }


}
