package cn.com.wanshi.chat.user.controller;


import cn.com.wanshi.chat.user.model.req.FindUserInfoReq;
import cn.com.wanshi.chat.user.model.req.*;
import cn.com.wanshi.chat.user.model.resp.FindUserInfoResp;
import cn.com.wanshi.chat.user.model.resp.UserInfoResp;
import cn.com.wanshi.common.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.com.wanshi.chat.user.service.IImUserDataService;

/**
 * @author zhongzhicheng
 * @data 2023/7/5
 * @time 12:31
 */
@Api(tags = "UserDate")
@RestController
@RequestMapping("/v1/user/data")
public class ImUserDataController {
    @Autowired
    private IImUserDataService imUserService;

    @ApiOperation("获取用户信息接口")
    @PostMapping("/getUserInfo")
    public ResponseVO<UserInfoResp> getUserInfo(@RequestBody @Validated UserInfoReq req){
        ResponseVO<UserInfoResp> result = imUserService.getUserInfo(req);
        return result;
    }


    @ApiOperation("根据用户输入信息查找用户")
    @PostMapping("/findUserInfo")
    public ResponseVO<FindUserInfoResp> findUserInfo(@RequestBody @Validated FindUserInfoReq req){
        ResponseVO<FindUserInfoResp> result = imUserService.findUserInfo(req);
        return result;
    }




    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseVO<UserInfoResp> login(@RequestBody @Validated LoginReq req){
        ResponseVO<UserInfoResp> result = imUserService.loginIn(req);
        return result;
    }


    @ApiOperation("获取验证码")
    @PostMapping("/getReceiptCode")
    public ResponseVO<Boolean> getReceiptCode(@RequestBody @Validated GetReceiptCodeReq req){
        ResponseVO<Boolean> result = imUserService.getReceiptCode(req);
        System.out.println("ok啦");
        return result;
    }


    @ApiOperation("用户注册")
    @PostMapping("/signUp")
    public ResponseVO<Boolean> signUp(@RequestBody @Validated SignUpReq req){
        ResponseVO<Boolean> result = imUserService.signUp(req);
        return result;
    }
    @ApiOperation("更改密码")
    @PostMapping("/reSetPassWord")
    public ResponseVO<Boolean> reSetPassWord(@RequestBody @Validated ReSetPassWordReq req){
        ResponseVO<Boolean> result = imUserService.reSetPassWord(req);
        return result;
    }


}
