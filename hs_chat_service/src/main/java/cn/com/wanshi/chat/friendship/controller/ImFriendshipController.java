package cn.com.wanshi.chat.friendship.controller;


import cn.com.wanshi.chat.common.annotation.ValidateToken;
import cn.com.wanshi.chat.friendship.model.req.FriendAgreeRequestReq;
import cn.com.wanshi.chat.friendship.model.req.FriendsReq;
import cn.com.wanshi.chat.friendship.model.resp.FriendAgreeRequestResp;
import cn.com.wanshi.chat.friendship.model.resp.FriendResp;
import cn.com.wanshi.chat.friendship.service.IImFriendshipService;
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
 * TOKEN 前端控制器
 * </p>
 *
 * @author zzc
 * @since 2023-07-29
 */
@RestController
@RequestMapping("/v1/im/friendship")
public class ImFriendshipController {


    @Autowired
    IImFriendshipService iImFriendshipService;


    @ApiOperation("同意好友申请")
    @PostMapping("/friend/agree")
    @ValidateToken
    public ResponseVO<FriendAgreeRequestResp> friendAgreeApply(@RequestBody @Validated FriendAgreeRequestReq req){
        ResponseVO<FriendAgreeRequestResp> result = iImFriendshipService.friendAgreeApply(req);
        return result;
    }


    @ApiOperation("好友列表")
    @PostMapping("/friends")
    @ValidateToken
    public ResponseVO<List<FriendResp>> friends(@RequestBody @Validated FriendsReq req){
        ResponseVO<List<FriendResp>> result = iImFriendshipService.friends(req);
        return result;
    }






}
