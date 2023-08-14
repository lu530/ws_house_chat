package cn.com.wanshi.chat.friendship.service;

import cn.com.wanshi.chat.friendship.entity.ImFriendshipRequest;
import cn.com.wanshi.chat.friendship.model.req.FriendRequestCountReq;
import cn.com.wanshi.chat.friendship.model.req.FriendRequestListReq;
import cn.com.wanshi.chat.friendship.model.req.FriendRequestReq;
import cn.com.wanshi.chat.friendship.model.resp.FriendRequestCountResp;
import cn.com.wanshi.chat.friendship.model.resp.FriendRequestListResp;
import cn.com.wanshi.chat.friendship.model.resp.FriendRequestResp;
import cn.com.wanshi.common.ResponseVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 好友申请信息表 服务类
 * </p>
 *
 * @author zzc
 * @since 2023-08-07
 */
public interface IImFriendshipRequestService extends IService<ImFriendshipRequest> {

    /**
     * 好友申请
     * @param req
     * @return
     */
    ResponseVO<FriendRequestResp> friendRequest(FriendRequestReq req);

    /**
     * 新好友统计
     * @param req
     * @return
     */
    ResponseVO<FriendRequestCountResp> friendRequestCount(FriendRequestCountReq req);

    /**
     * 新增好友
     * @param req
     * @return
     */
    ResponseVO<FriendRequestListResp> friendRequestList(FriendRequestListReq req);
}
