package cn.com.wanshi.chat.group.service.impl;

import cn.com.wanshi.chat.common.constants.CommonConstants;
import cn.com.wanshi.chat.common.enums.GroupApplyJoinTypeEnum;
import cn.com.wanshi.chat.common.enums.GroupJoinTypeEnum;
import cn.com.wanshi.chat.common.enums.GroupRoleEnum;
import cn.com.wanshi.chat.common.enums.GroupTypeEnum;
import cn.com.wanshi.chat.common.utils.ImageUtil;
import cn.com.wanshi.chat.common.utils.MinioUtil;
import cn.com.wanshi.chat.common.utils.SerialNoUtil;
import cn.com.wanshi.chat.group.entity.ImGroup;
import cn.com.wanshi.chat.group.entity.ImGroupMember;
import cn.com.wanshi.chat.group.mapper.ImGroupMapper;
import cn.com.wanshi.chat.group.model.req.GroupInitReq;
import cn.com.wanshi.chat.group.model.resp.GroupInitResp;
import cn.com.wanshi.chat.group.service.IImGroupService;
import cn.com.wanshi.chat.user.entity.ImUserData;
import cn.com.wanshi.chat.user.service.IImUserDataService;
import cn.com.wanshi.common.ResponseVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
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
        imGroup.setGroupName("群聊(" +req.getGroupMembers().size() +")");
        imGroup.setGroupId("G"+ SerialNoUtil.getUNID());
        imGroup.setOwnerId(req.getUserId());
        imGroup.setGroupType(GroupTypeEnum.PRIVATE.getValue());
        imGroup.setApplyJoinType(GroupApplyJoinTypeEnum.FREE.getValue());
        imGroup.setMaxMemberCount(CommonConstants.GROUP_MAX_MEMBERCOUNT);
        imGroup.setCreateTime(now);

        List<ImUserData> usersByUserIds = imUserService.getUsersByUserIds(req.getGroupMembers());
        List<String> imageList = usersByUserIds.stream().map(m -> {
            return minioHost +"/" + m.getPhoto();
        }).collect(Collectors.toList());
        InputStream combinationOfheadInputStream = ImageUtil.getCombinationOfheadInputStream(imageList);

        minioUtil.putObject(bucketName, imGroup.getGroupId() + ".jpg", combinationOfheadInputStream, combinationOfheadInputStream.available(), "image/jpeg");

        String photoUrl = bucketName + "/"+ imGroup.getGroupId() + ".jpg";
        imGroup.setPhoto(photoUrl);

        List<ImGroupMember> groupMembers = req.getGroupMembers().stream().map(item -> {
            ImGroupMember groupMember = new ImGroupMember();
            groupMember.setGroupId(imGroup.getGroupId());
            groupMember.setGroupMemberId("GM" + SerialNoUtil.getUNID());
            groupMember.setMemberId(item);
            groupMember.setJoinTime(now);
            groupMember.setJoinType(GroupJoinTypeEnum.FRIEND_RECOMMEND.getValue());
            groupMember.setRole(GroupRoleEnum.NORMAL.getValue());
            groupMember.setCreateTime(now);

            return groupMember;
        }).collect(Collectors.toList());

        ImGroupMember groupMember = new ImGroupMember();
        groupMember.setGroupId(imGroup.getGroupId());
        groupMember.setGroupMemberId("GM"+ SerialNoUtil.getUNID());
        groupMember.setMemberId(req.getUserId());
        groupMember.setJoinTime(now);
        groupMember.setJoinType(GroupJoinTypeEnum.CREATOR.getValue());
        groupMember.setRole(GroupRoleEnum.GROUP_LEADER.getValue());
        groupMember.setCreateTime(now);
        groupMembers.add(groupMember);
        this.save(imGroup);
        imGroupMemberServiceImpl.saveBatch(groupMembers);
        return ResponseVO.successResponse(GroupInitResp.builder()
                .groupName(imGroup.getGroupName())
                .photo(photoUrl)
                .build());
    }
}
