package cn.com.wanshi.chat.group.service.impl;

import cn.com.wanshi.chat.common.constants.General;
import cn.com.wanshi.chat.common.enums.GroupJoinTypeEnum;
import cn.com.wanshi.chat.common.enums.GroupRoleEnum;
import cn.com.wanshi.chat.common.utils.BeanCopyUtils;
import cn.com.wanshi.chat.common.utils.SerialNoUtil;
import cn.com.wanshi.chat.friendship.entity.ImFriendshipRequest;
import cn.com.wanshi.chat.group.entity.ImGroupMember;
import cn.com.wanshi.chat.group.mapper.ImGroupMemberMapper;
import cn.com.wanshi.chat.group.model.req.GroupMemberAddReq;
import cn.com.wanshi.chat.group.model.req.GroupMemberListReq;
import cn.com.wanshi.chat.group.model.req.GroupMemberRemoveReq;
import cn.com.wanshi.chat.group.model.resp.GroupMemberAddResp;
import cn.com.wanshi.chat.group.model.resp.GroupMemberRemoveResp;
import cn.com.wanshi.chat.group.model.resp.GroupMemberResp;
import cn.com.wanshi.chat.group.service.IImGroupMemberService;
import cn.com.wanshi.chat.user.entity.ImUserData;
import cn.com.wanshi.chat.user.service.IImUserDataService;
import cn.com.wanshi.common.ResponseVO;
import cn.com.wanshi.common.enums.YesNoEnum;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 群成员关联表 服务实现类
 * </p>
 *
 * @author zzc
 * @since 2023-12-15
 */
@Service
public class ImGroupMemberServiceImpl extends ServiceImpl<ImGroupMemberMapper, ImGroupMember> implements IImGroupMemberService {

    @Autowired
    private IImUserDataService imUserService;


    @Override
    public List<ImGroupMember> getImgroupMembersByGroupId(String groupId) {
        List<ImGroupMember> imGroupMembers = General.GROUP_TO_MEMBERS_MAP.get(groupId);
        if(CollectionUtil.isNotEmpty(imGroupMembers)){
            return imGroupMembers;
        }

        LambdaQueryWrapper<ImGroupMember> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ImGroupMember::getGroupId, groupId);
        lqw.eq(ImGroupMember::getLeaveFlag, YesNoEnum.NO.value);
        List<ImGroupMember> list = this.list(lqw);
        General.GROUP_TO_MEMBERS_MAP.put(groupId, list);
        return list;
    }

    @Override
    public ResponseVO<List<GroupMemberResp>> groupMemberList(GroupMemberListReq req) {
        List<ImGroupMember> imgroupMembersByGroupId = this.getImgroupMembersByGroupId(req.getGroupId());
        List<GroupMemberResp> collect = imgroupMembersByGroupId.stream().map(item -> {
            GroupMemberResp groupMemberResp = new GroupMemberResp();
            BeanUtils.copyProperties(item, groupMemberResp);
            return groupMemberResp;
        }).collect(Collectors.toList());
        return ResponseVO.successResponse(collect);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseVO<GroupMemberRemoveResp> groupMemberRemove(GroupMemberRemoveReq req) {
        Date now = new Date();
        LambdaQueryWrapper<ImGroupMember> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ImGroupMember::getGroupId, req.getGroupId());
        lqw.in(ImGroupMember::getMemberId, req.getGroupMembers());
        lqw.eq(ImGroupMember::getLeaveFlag, YesNoEnum.NO.value);
        ImGroupMember imGroupMember = new ImGroupMember();
        imGroupMember.setLeaveFlag(YesNoEnum.YES.value);
        imGroupMember.setLeaveTime(now);
        boolean update = this.update(imGroupMember, lqw);
        General.GROUP_TO_MEMBERS_MAP.remove(req.getGroupId());
        return ResponseVO.successResponse();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseVO<GroupMemberAddResp> groupMemberAdd(GroupMemberAddReq req) {
        Date now = new Date();
        LambdaQueryWrapper<ImGroupMember> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ImGroupMember::getGroupId, req.getGroupId());
        lqw.in(ImGroupMember::getMemberId, req.getGroupMembers());
        List<ImGroupMember> list = this.list(lqw);

        if(CollectionUtil.isNotEmpty(list)){
            ImGroupMember imGroupMember = new ImGroupMember();
            imGroupMember.setLeaveFlag(YesNoEnum.NO.value);
            imGroupMember.setLeaveTime(null);
            boolean update = this.update(imGroupMember, lqw);
        }

        Set<String> hadInGroupMembers = list.stream().map(ImGroupMember::getMemberId).collect(Collectors.toSet());

        List<String> needAddMemberIds = req.getGroupMembers().stream().filter(f -> !hadInGroupMembers.contains(f)).collect(Collectors.toList());

        if(CollectionUtil.isEmpty(needAddMemberIds)){
            General.GROUP_TO_MEMBERS_MAP.remove(req.getGroupId());
            return ResponseVO.successResponse();
        }
        List<ImUserData> usersByUserIds = imUserService.getUsersByUserIds(needAddMemberIds);

        Map<String, ImUserData> userMap = usersByUserIds.stream().collect(Collectors.toMap(ImUserData::getUserId, Function.identity(),
                (existing, replacement) -> existing));

        List<ImGroupMember> addList = req.getGroupMembers().stream().map(item -> {
            ImUserData imUserData = userMap.get(item);
            ImGroupMember groupMember = new ImGroupMember();
            groupMember.setGroupId(req.getGroupId());
            groupMember.setGroupMemberId("GM" + SerialNoUtil.getUNID());
            groupMember.setPhoto(imUserData.getPhoto());
            groupMember.setNickName(imUserData.getNickName());
            groupMember.setMemberId(item);
            groupMember.setJoinTime(now);
            groupMember.setJoinType(GroupJoinTypeEnum.FRIEND_RECOMMEND.getValue());
            groupMember.setRole(GroupRoleEnum.NORMAL.getValue());
            groupMember.setCreateTime(now);
            return groupMember;
        }).collect(Collectors.toList());
        this.saveBatch(addList);

        //删除缓存
        General.GROUP_TO_MEMBERS_MAP.remove(req.getGroupId());
        return ResponseVO.successResponse();
    }
}
