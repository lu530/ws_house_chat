package cn.com.wanshi.chat.user.service;


import cn.com.wanshi.chat.user.model.req.UserInfoReq;
import cn.com.wanshi.chat.user.model.resp.UserInfoResp;
import cn.com.wanshi.common.ResponseVO;

/**
 * @author zhongzhicheng
 * @data 2023/7/5
 * @time 11:22
 */
public interface ImUserService {

    /**
     * 获取用户信息接口
     * @param req
     * @return
     */
    ResponseVO<UserInfoResp> getUserInfo(UserInfoReq req);
}
