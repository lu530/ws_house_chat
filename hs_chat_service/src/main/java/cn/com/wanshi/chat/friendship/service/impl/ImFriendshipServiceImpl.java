package cn.com.wanshi.chat.friendship.service.impl;

import cn.com.wanshi.chat.friendship.entity.ImFriendship;
import cn.com.wanshi.chat.friendship.mapper.ImFriendshipMapper;
import cn.com.wanshi.chat.friendship.model.req.FriendAgreeRequestReq;
import cn.com.wanshi.chat.friendship.model.resp.FriendAgreeRequestResp;
import cn.com.wanshi.chat.friendship.service.IImFriendshipService;
import cn.com.wanshi.common.ResponseVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * TOKEN 服务实现类
 * </p>
 *
 * @author zzc
 * @since 2023-07-29
 */
@Service
public class ImFriendshipServiceImpl extends ServiceImpl<ImFriendshipMapper, ImFriendship> implements IImFriendshipService {

    @Override
    public ResponseVO<FriendAgreeRequestResp> friendAgreeApply(FriendAgreeRequestReq req) {
        return null;
    }
}
