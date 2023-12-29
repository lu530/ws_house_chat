package cn.com.wanshi.chat.group.service.impl;

import cn.com.wanshi.chat.common.constants.CommonConstants;
import cn.com.wanshi.chat.common.enums.*;
import cn.com.wanshi.chat.common.utils.ImageUtil;
import cn.com.wanshi.chat.common.utils.MinioUtil;
import cn.com.wanshi.chat.common.utils.SerialNoUtil;
import cn.com.wanshi.chat.group.entity.ImGroup;
import cn.com.wanshi.chat.group.entity.ImGroupMember;
import cn.com.wanshi.chat.group.mapper.ImGroupMapper;
import cn.com.wanshi.chat.group.model.req.GroupInitReq;
import cn.com.wanshi.chat.group.model.resp.GroupInitResp;
import cn.com.wanshi.chat.group.service.IImGroupService;
import cn.com.wanshi.chat.message.entity.ImMessageData;
import cn.com.wanshi.chat.message.service.IImMessageDataService;
import cn.com.wanshi.chat.user.entity.ImUserData;
import cn.com.wanshi.chat.user.service.IImUserDataService;
import cn.com.wanshi.common.ResponseVO;
import cn.com.wanshi.common.enums.YesNoEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 群消息表 服务实现类
 * </p>
 *
 * @author zzc
 * @since 2023-12-15
 */
@Service
public class ImGroupServiceImpl extends ServiceImpl<ImGroupMapper, ImGroup> implements IImGroupService {

    @Autowired
    private IImUserDataService imUserService;


    @Autowired
    private ImGroupMemberServiceImpl imGroupMemberServiceImpl;

    @Autowired
    private IImMessageDataService imMessageDataService;



    @Value("${minio.endpoint}")
    private String minioHost;


    @Value("${minio.bucket-name}")
    private String bucketName;


    @Autowired
    MinioUtil minioUtil;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<GroupInitResp> groupInit(GroupInitReq req) throws Exception {
        Date now = new Date();
        ImGroup imGroup = new ImGroup();
        imGroup.setGroupName("群聊(" + (req.getGroupMembers().size() + 1) +")");
        imGroup.setGroupId("G"+ SerialNoUtil.getUNID());
        imGroup.setOwnerId(req.getUserId());
        imGroup.setGroupType(GroupTypeEnum.PRIVATE.getValue());
        imGroup.setApplyJoinType(GroupApplyJoinTypeEnum.FREE.getValue());
        imGroup.setMaxMemberCount(CommonConstants.GROUP_MAX_MEMBERCOUNT);
        imGroup.setCreateTime(now);
        List<String> userIds = new ArrayList<>();
        userIds.addAll(req.getGroupMembers());
        userIds.add(req.getUserId());
        List<ImUserData> usersByUserIds = imUserService.getUsersByUserIds(userIds);
        List<String> imageList = usersByUserIds.stream().map(m -> {
            return minioHost +"/" + m.getPhoto();
        }).collect(Collectors.toList());
        InputStream combinationOfheadInputStream = ImageUtil.getCombinationOfheadInputStream(imageList);

        Map<String, ImUserData> userMap = usersByUserIds.stream().collect(Collectors.toMap(ImUserData::getUserId, Function.identity(),
                (existing, replacement) -> existing));

        minioUtil.putObject(bucketName, imGroup.getGroupId() + ".jpg", combinationOfheadInputStream, combinationOfheadInputStream.available(), "image/jpeg");

        String photoUrl = bucketName + "/"+ imGroup.getGroupId() + ".jpg";
        imGroup.setPhoto(photoUrl);

        List<ImGroupMember> groupMembers = req.getGroupMembers().stream().map(item -> {
            ImUserData imUserData = userMap.get(item);
            ImGroupMember groupMember = new ImGroupMember();
            groupMember.setGroupId(imGroup.getGroupId());
            groupMember.setGroupMemberId("GM" + SerialNoUtil.getUNID());
            groupMember.setPhoto(imUserData.getPhoto());
            groupMember.setMemberId(item);
            groupMember.setJoinTime(now);
            groupMember.setJoinType(GroupJoinTypeEnum.FRIEND_RECOMMEND.getValue());
            groupMember.setRole(GroupRoleEnum.NORMAL.getValue());
            groupMember.setCreateTime(now);

            return groupMember;
        }).collect(Collectors.toList());

        ImUserData imUserData = userMap.get(req.getUserId());
        ImGroupMember groupMember = new ImGroupMember();
        groupMember.setGroupId(imGroup.getGroupId());
        groupMember.setGroupMemberId("GM"+ SerialNoUtil.getUNID());
        groupMember.setMemberId(req.getUserId());
        groupMember.setPhoto(imUserData.getPhoto());
        groupMember.setJoinTime(now);
        groupMember.setJoinType(GroupJoinTypeEnum.CREATOR.getValue());
        groupMember.setRole(GroupRoleEnum.GROUP_LEADER.getValue());
        groupMember.setCreateTime(now);
        groupMembers.add(groupMember);
        this.save(imGroup);
        imGroupMemberServiceImpl.saveBatch(groupMembers);

        ImMessageData imMessageData = new ImMessageData();
        imMessageData.setFromType(MessageFromUserTypeEnum.GROUP_USERS.getType());
        imMessageData.setOwnerId(req.getUserId());
        imMessageData.setFromId(imGroup.getGroupId());
        imMessageData.setToType(MessageToUserTypeEnum.NORMAL_USER.getType());
        imMessageData.setToId(req.getUserId());
        imMessageData.setMessageType(MessageTypeEnum.TEXT.getType());
        imMessageData.setMeesageData("");
        imMessageData.setSendStatus(YesNoEnum.YES.value);
        imMessageData.setCreateTime(now);
        imMessageData.setMessageTime(now);
        imMessageDataService.save(imMessageData);

        return ResponseVO.successResponse(GroupInitResp.builder()
                .groupName(imGroup.getGroupName())
                .photo(photoUrl)
                .groupId(imGroup.getGroupId())
                .build());
    }
}
