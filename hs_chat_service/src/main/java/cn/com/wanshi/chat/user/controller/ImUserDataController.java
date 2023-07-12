package cn.com.wanshi.chat.user.controller;


import cn.com.wanshi.chat.user.model.req.UserInfoReq;
import cn.com.wanshi.chat.user.model.resp.UserInfoResp;
import cn.com.wanshi.common.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.com.wanshi.chat.user.service.ImUserService;
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
    private ImUserService imUserService;

    @ApiOperation("获取用户信息接口")
    @PostMapping("/getUserInfo")
    public ResponseVO<UserInfoResp> getUserInfo(@RequestBody UserInfoReq req){
        ResponseVO<UserInfoResp> result = imUserService.getUserInfo(req);
        return result;
    }


}
