package cn.com.wanshi.chat.friendship.controller;


import cn.com.wanshi.chat.common.annotation.ValidateToken;
import cn.com.wanshi.chat.friendship.model.req.FriendAgreeRequestReq;
import cn.com.wanshi.chat.friendship.model.resp.FriendAgreeRequestResp;
import cn.com.wanshi.chat.friendship.service.IImFriendshipService;
import cn.com.wanshi.common.ResponseVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * TOKEN 前端控制器
 * </p>
 *
 * @author zzc
 * @since 2023-07-29
 */
@RestController
@RequestMapping("/im/friendship")
public class ImFriendshipController {


    IImFriendshipService iImFriendshipService;


    @ApiOperation("同意好友申请")
    @PostMapping("/friend/agree/apply")
    @ValidateToken
    public ResponseVO<FriendAgreeRequestResp> friendAgreeApply(@RequestBody @Validated FriendAgreeRequestReq req){
        ResponseVO<FriendAgreeRequestResp> result = iImFriendshipService.friendAgreeApply(req);
        return result;
    }




}