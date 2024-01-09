package cn.com.wanshi.chat.group.controller;


import cn.com.wanshi.chat.common.annotation.ValidateToken;
import cn.com.wanshi.chat.group.model.req.*;
import cn.com.wanshi.chat.group.model.resp.*;
import cn.com.wanshi.chat.group.service.IImGroupMemberService;
import cn.com.wanshi.chat.group.service.IImGroupService;
import cn.com.wanshi.common.ResponseVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 群消息表 前端控制器
 * </p>
 *
 * @author zzc
 * @since 2023-12-15
 */
@RestController
@ApiOperation("群管理")
@RequestMapping("/v1/im/group")
public class ImGroupController {

    @Autowired
    IImGroupService iImGroupService;
    @Autowired
    IImGroupMemberService iImGroupMemberService;




    @ApiOperation("新建群")
    @PostMapping("/group/init")
    @ValidateToken
    public ResponseVO<GroupInitResp> groupInit(@RequestBody @Validated GroupInitReq req) throws Exception {
        ResponseVO<GroupInitResp> result = iImGroupService.groupInit(req);
        return result;
    }

    @ApiOperation("群信息")
    @PostMapping("/info")
    @ValidateToken
    public ResponseVO<GroupInfoResp> groupInfo(@RequestBody @Validated GroupInfoReq req) throws Exception {
        ResponseVO<GroupInfoResp> result = iImGroupService.groupInfo(req);
        return result;
    }


    @ApiOperation("群名称修改")
    @PostMapping("/groupName/modify")
    @ValidateToken
    public ResponseVO<Boolean> groupNameModify(@RequestBody @Validated GroupNameModifyReq req) throws Exception {
        ResponseVO<Boolean> result = iImGroupService.groupNameModify(req);
        return result;
    }


    @ApiOperation("群成员列表")
    @PostMapping("/member/list")
    @ValidateToken
    public ResponseVO<List<GroupMemberResp>> groupMemberList(@RequestBody @Validated GroupMemberListReq req) {
        ResponseVO<List<GroupMemberResp>> result = iImGroupMemberService.groupMemberList(req);
        return result;
    }

    @ApiOperation("群成员删除")
    @PostMapping("/member/remove")
    @ValidateToken
    public ResponseVO<GroupMemberRemoveResp> groupMemberRemove(@RequestBody @Validated GroupMemberRemoveReq req) throws Exception {
        ResponseVO<GroupMemberRemoveResp> result = iImGroupMemberService.groupMemberRemove(req);
        return result;
    }

    @ApiOperation("群成员删除")
    @PostMapping("/member/add")
    @ValidateToken
    public ResponseVO<GroupMemberAddResp> groupMemberAdd(@RequestBody @Validated GroupMemberAddReq req) throws Exception {
        ResponseVO<GroupMemberAddResp> result = iImGroupMemberService.groupMemberAdd(req);
        return result;
    }










}
