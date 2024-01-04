package cn.com.wanshi.chat.group.service.impl;

import cn.com.wanshi.chat.common.constants.General;
import cn.com.wanshi.chat.common.enums.GroupJoinTypeEnum;
import cn.com.wanshi.chat.common.enums.GroupRoleEnum;
import cn.com.wanshi.chat.common.enums.GroupStatusEnum;
import cn.com.wanshi.chat.common.utils.BeanCopyUtils;
import cn.com.wanshi.chat.common.utils.ImageUtil;
import cn.com.wanshi.chat.common.utils.MinioUtil;
import cn.com.wanshi.chat.common.utils.SerialNoUtil;
import cn.com.wanshi.chat.group.entity.ImGroup;
import cn.com.wanshi.chat.group.entity.ImGroupMember;
import cn.com.wanshi.chat.group.mapper.ImGroupMapper;
import cn.com.wanshi.chat.group.mapper.ImGroupMemberMapper;
import cn.com.wanshi.chat.group.model.req.GroupMemberAddReq;
import cn.com.wanshi.chat.group.model.req.GroupMemberListReq;
import cn.com.wanshi.chat.group.model.req.GroupMemberRemoveReq;
import cn.com.wanshi.chat.group.model.resp.GroupInitResp;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
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



    @Value("${minio.endpoint}")
    private String minioHost;


    @Autowired
    private ImGroupMapper imGroupMapper;

    @Value("${minio.bucket-name}")
    private String bucketName;


    @Autowired
    MinioUtil minioUtil;



    @Override
    public List<ImGroupMember> getImgroupMembersByGroupId(String groupId) {
        List<ImGroupMember> imGroupMembers = General.GROUP_TO_MEMBERS_MAP.get(groupId);
        if(CollectionUtil.isNotEmpty(imGroupMembers)){
            return imGroupMembers;
        }

        LambdaQueryWrapper<ImGroupMember> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ImGroupMember::getGroupId, groupId);
        lqw.eq(ImGroupMember::getLeaveFlag, YesNoEnum.NO.value);
        lqw.orderByAsc(ImGroupMember::getCreateTime);
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
    public ResponseVO<GroupMemberRemoveResp> groupMemberRemove(GroupMemberRemoveReq req) throws Exception {
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
        GroupInitResp groupInitResp = this.updateGroupNameAndPhoto(req.getGroupId());
        GroupMemberRemoveResp result = BeanCopyUtils.copy(groupInitResp, GroupMemberRemoveResp.class);
        return ResponseVO.successResponse(result);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseVO<GroupMemberAddResp> groupMemberAdd(GroupMemberAddReq req) throws Exception {
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
            GroupInitResp groupInitResp = this.updateGroupNameAndPhoto(req.getGroupId());
            GroupMemberRemoveResp result = BeanCopyUtils.copy(groupInitResp, GroupMemberRemoveResp.class);
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
        GroupInitResp groupInitResp = this.updateGroupNameAndPhoto(req.getGroupId());
        GroupMemberRemoveResp result = BeanCopyUtils.copy(groupInitResp, GroupMemberRemoveResp.class);
        return ResponseVO.successResponse(groupInitResp);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public GroupInitResp updateGroupNameAndPhoto(String groupId) throws Exception {
        LambdaQueryWrapper<ImGroup> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ImGroup::getGroupId, groupId);
        lqw.eq(ImGroup::getStatus, GroupStatusEnum.NORMAL.getValue());

        ImGroup imGroup = imGroupMapper.selectOne(lqw);

        List<ImGroupMember> groupMembers = this.getImgroupMembersByGroupId(groupId);

        if(imGroup.getCustomNameFlag().equals(YesNoEnum.NO.value)){
            imGroup.setGroupName("群聊("+ groupMembers.size() +")");
        }

        List<String> imageList = groupMembers.stream().limit(9).map(m -> {
            return minioHost +"/" + m.getPhoto();
        }).collect(Collectors.toList());
        InputStream combinationOfheadInputStream = ImageUtil.getCombinationOfheadInputStream(imageList);

        String[] s = imGroup.getPhoto().split("_");
        int picIndex = 0;
        minioUtil.removeObject(bucketName, imGroup.getPhoto());
        if(s.length > 1){
            String[] split = s[1].split("\\.");
            picIndex = Integer.parseInt(split[0]);
            picIndex = picIndex + 1;
        }
        String photoUrl = imGroup.getGroupId() + "_" + picIndex;

        minioUtil.putObject(bucketName, photoUrl + ".jpg", combinationOfheadInputStream, combinationOfheadInputStream.available(), "image/jpeg");
        photoUrl = bucketName + "/"+ photoUrl + ".jpg";
        imGroup.setPhoto(photoUrl);

        imGroupMapper.updateById(imGroup);
        return GroupInitResp.builder()
                .groupId(imGroup.getGroupId())
                .groupName(imGroup.getGroupName())
                .photo(imGroup.getPhoto())
                .build();
    }


}
