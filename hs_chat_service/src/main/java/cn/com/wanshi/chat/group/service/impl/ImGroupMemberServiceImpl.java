package cn.com.wanshi.chat.group.service.impl;

import cn.com.wanshi.chat.common.constants.General;
import cn.com.wanshi.chat.friendship.entity.ImFriendshipRequest;
import cn.com.wanshi.chat.group.entity.ImGroupMember;
import cn.com.wanshi.chat.group.mapper.ImGroupMemberMapper;
import cn.com.wanshi.chat.group.service.IImGroupMemberService;
import cn.com.wanshi.common.enums.YesNoEnum;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
