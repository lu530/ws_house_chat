package cn.com.wanshi.chat.group.service;

import cn.com.wanshi.chat.group.entity.ImGroupMember;
import cn.com.wanshi.chat.group.model.req.GroupMemberAddReq;
import cn.com.wanshi.chat.group.model.req.GroupMemberListReq;
import cn.com.wanshi.chat.group.model.req.GroupMemberRemoveReq;
import cn.com.wanshi.chat.group.model.resp.GroupMemberAddResp;
import cn.com.wanshi.chat.group.model.resp.GroupMemberRemoveResp;
import cn.com.wanshi.chat.group.model.resp.GroupMemberResp;
import cn.com.wanshi.common.ResponseVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 群成员关联表 服务类
 * </p>
 *
 * @author zzc
 * @since 2023-12-15
 */
public interface IImGroupMemberService extends IService<ImGroupMember> {


    List<ImGroupMember> getImgroupMembersByGroupId(String groupId);


    ResponseVO<List<GroupMemberResp>> groupMemberList(GroupMemberListReq req);

    ResponseVO<GroupMemberRemoveResp> groupMemberRemove(GroupMemberRemoveReq req);

    ResponseVO<GroupMemberAddResp> groupMemberAdd(GroupMemberAddReq req);
}
