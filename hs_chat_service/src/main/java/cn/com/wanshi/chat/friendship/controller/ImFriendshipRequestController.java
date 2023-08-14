package cn.com.wanshi.chat.friendship.controller;


import cn.com.wanshi.chat.common.annotation.ValidateToken;
import cn.com.wanshi.chat.friendship.model.req.FriendRequestCountReq;
import cn.com.wanshi.chat.friendship.model.req.FriendRequestListReq;
import cn.com.wanshi.chat.friendship.model.req.FriendRequestReq;
import cn.com.wanshi.chat.friendship.model.resp.FriendRequestCountResp;
import cn.com.wanshi.chat.friendship.model.resp.FriendRequestListResp;
import cn.com.wanshi.chat.friendship.model.resp.FriendRequestResp;
import cn.com.wanshi.chat.friendship.service.IImFriendshipRequestService;
import cn.com.wanshi.common.ResponseVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 好友申请信息表 前端控制器
 * </p>
 *
 * @author zzc
 * @since 2023-08-07
 */
@RestController
@RequestMapping("/v1/im/request")
public class ImFriendshipRequestController {

    @Autowired
    IImFriendshipRequestService iImFriendshipRequestService;

    @ApiOperation("好友申请")
    @PostMapping("/friend/request")
    @ValidateToken
    public ResponseVO<FriendRequestResp> friendRequest(@RequestBody @Validated FriendRequestReq req){
        ResponseVO<FriendRequestResp> result = iImFriendshipRequestService.friendRequest(req);
        return result;
    }

    @ApiOperation("好友申请数")
    @PostMapping("/friend/request/count")
    @ValidateToken
    public ResponseVO<FriendRequestCountResp> friendRequestCount(@RequestBody @Validated FriendRequestCountReq req){
        ResponseVO<FriendRequestCountResp> result = iImFriendshipRequestService.friendRequestCount(req);
        return result;
    }


    @ApiOperation("新的朋友列表接口")
    @PostMapping("/friend/request/list")
    @ValidateToken
    public ResponseVO<FriendRequestListResp> friendRequestList(@RequestBody @Validated FriendRequestListReq req){
        ResponseVO<FriendRequestListResp> result = iImFriendshipRequestService.friendRequestList(req);
        return result;
    }






}
