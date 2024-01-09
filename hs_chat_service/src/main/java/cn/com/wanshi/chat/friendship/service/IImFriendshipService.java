package cn.com.wanshi.chat.friendship.service;

import cn.com.wanshi.chat.friendship.entity.ImFriendship;
import cn.com.wanshi.chat.friendship.model.req.FriendAgreeRequestReq;
import cn.com.wanshi.chat.friendship.model.req.FriendsReq;
import cn.com.wanshi.chat.friendship.model.resp.FriendAgreeRequestResp;
import cn.com.wanshi.chat.friendship.model.resp.FriendResp;
import cn.com.wanshi.common.ResponseVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * TOKEN 服务类
 * </p>
 *
 * @author zzc
 * @since 2023-07-29
 */
public interface IImFriendshipService extends IService<ImFriendship> {

    ResponseVO<FriendAgreeRequestResp> friendAgreeApply(FriendAgreeRequestReq req);

    /**
     * 好友列表
     * @param req
     * @return
     */
    ResponseVO<List<FriendResp>> friends(FriendsReq req);


    List<FriendResp> getFriendList(String userId);
}
