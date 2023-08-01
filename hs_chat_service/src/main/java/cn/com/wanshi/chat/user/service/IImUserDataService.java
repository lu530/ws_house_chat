package cn.com.wanshi.chat.user.service;

import cn.com.wanshi.chat.user.entity.ImUserData;
import cn.com.wanshi.chat.user.model.req.*;
import cn.com.wanshi.chat.user.model.resp.UserInfoResp;
import cn.com.wanshi.common.ResponseVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author zzc
 * @since 2023-07-19
 */
public interface IImUserDataService extends IService<ImUserData> {

    /**
     * 获取用户信息接口
     * @param req
     * @return
     */
    ResponseVO<UserInfoResp> getUserInfo(UserInfoReq req);

    /**
     * 登录服务接口
     * @param req
     * @return
     */
    ResponseVO<UserInfoResp> loginIn(LoginReq req);

    /**
     * 获取验证码
     * @param req
     * @return
     */
    ResponseVO<Boolean> getReceiptCode(GetReceiptCodeReq req);

    /**
     * 用户注册
     * @param req
     * @return
     */
    ResponseVO<Boolean> signUp(SignUpReq req);

    /**
     * 重置密码
     * @param req
     * @return
     */
    ResponseVO<Boolean> reSetPassWord(ReSetPassWordReq req);

    /**
     * 根据userId获取用户数据
     * @param userId
     * @return
     */
    ImUserData getUserInfoByUserId(String userId);
}
