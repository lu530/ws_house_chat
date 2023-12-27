package cn.com.wanshi.chat.group.controller;


import cn.com.wanshi.chat.common.annotation.ValidateToken;
import cn.com.wanshi.chat.group.model.req.GroupInitReq;
import cn.com.wanshi.chat.group.model.resp.GroupInitResp;
import cn.com.wanshi.chat.group.service.IImGroupService;
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
 * 群消息表 前端控制器
 * </p>
 *
 * @author zzc
 * @since 2023-12-15
 */
@RestController
@RequestMapping("/v1/im/group")
public class ImGroupController {

    @Autowired
    IImGroupService iImGroupService;


    @ApiOperation("新建群")
    @PostMapping("/group/init")
    @ValidateToken
    public ResponseVO<GroupInitResp> groupInit(@RequestBody @Validated GroupInitReq req) throws Exception {
        ResponseVO<GroupInitResp> result = iImGroupService.groupInit(req);
        return result;
    }






}
