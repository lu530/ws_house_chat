package cn.com.wanshi.chat.friendship.service.impl;

import cn.com.wanshi.chat.common.enums.FriendApplyApproveStatusEnum;
import cn.com.wanshi.chat.common.enums.FriendshipBlackEnum;
import cn.com.wanshi.chat.common.enums.FriendshipStatusEnum;
import cn.com.wanshi.chat.friendship.entity.ImFriendship;
import cn.com.wanshi.chat.friendship.entity.ImFriendshipRequest;
import cn.com.wanshi.chat.friendship.mapper.ImFriendshipMapper;
import cn.com.wanshi.chat.friendship.mapper.ImFriendshipRequestMapper;
import cn.com.wanshi.chat.friendship.model.req.FriendAgreeRequestReq;
import cn.com.wanshi.chat.friendship.model.req.FriendsReq;
import cn.com.wanshi.chat.friendship.model.resp.FriendAgreeRequestResp;
import cn.com.wanshi.chat.friendship.model.resp.FriendResp;
import cn.com.wanshi.chat.friendship.service.IImFriendshipService;
import cn.com.wanshi.chat.user.entity.ImUserData;
import cn.com.wanshi.chat.user.service.IImUserDataService;
import cn.com.wanshi.common.ResponseVO;
import cn.com.wanshi.common.enums.YesNoEnum;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * TOKEN 服务实现类
 * </p>
 *
 * @author zzc
 * @since 2023-07-29
 */
@Service
public class ImFriendshipServiceImpl extends ServiceImpl<ImFriendshipMapper, ImFriendship> implements IImFriendshipService {



    @Autowired
    ImFriendshipRequestMapper imFriendshipRequestMapper;

    @Autowired
    private IImUserDataService imUserService;
    /**
     * 同意好友添加
     * @param req
     * @return
     */
    @Override
    @Transactional
    public ResponseVO<FriendAgreeRequestResp> friendAgreeApply(FriendAgreeRequestReq req) {
        //接受方好友关系保存
        LambdaQueryWrapper<ImFriendship> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ImFriendship::getFromId, req.getUserId());
        lqw.eq(ImFriendship::getToId, req.getToId());
        ImFriendship one = this.getOne(lqw);
        Assert.isTrue(one == null || !one.getStatus().equals(FriendshipStatusEnum.NORMAL.getValue()),"已经添加该好友");

        Date now = new Date();
        if(Objects.nonNull(one)){
            one.setBlack(FriendshipBlackEnum.NORMAL.getValue());
            one.setStatus(FriendshipStatusEnum.NORMAL.getValue());
            one.setAddSource(req.getAddSource());
            one.setForbidFromSocialCircle(req.getForbidFromSocialCircle());
            one.setForbidToSocialCircle(req.getForbidToSocialCircle());
            one.setFriendNickName(req.getFriendNickName());
            one.setUpdateTime(now);
            this.updateById(one);
        }
        ImFriendship imFriendship = new ImFriendship();
        BeanUtils.copyProperties(req, imFriendship);
        imFriendship.setFromId(req.getUserId());
        imFriendship.setToId(req.getToId());
        imFriendship.setBlack(FriendshipBlackEnum.NORMAL.getValue());
        imFriendship.setStatus(FriendshipStatusEnum.NORMAL.getValue());
        imFriendship.setUpdateTime(now);
        imFriendship.setCreateTime(now);
        this.save(imFriendship);
        //请求方好友关系保存
        ImFriendshipRequest imFriendshipRequest = imFriendshipRequestMapper.selectById(req.getFriendshipRequestId());

        LambdaQueryWrapper<ImFriendship> applyLqw = new LambdaQueryWrapper<>();
        applyLqw.eq(ImFriendship::getFromId, imFriendshipRequest.getFromId());
        applyLqw.eq(ImFriendship::getToId, imFriendshipRequest.getToId());
        ImFriendship applyOne = this.getOne(applyLqw);

        if(Objects.nonNull(one)){
            applyOne.setBlack(FriendshipBlackEnum.NORMAL.getValue());
            applyOne.setStatus(FriendshipStatusEnum.NORMAL.getValue());
            applyOne.setAddSource(imFriendshipRequest.getAddSource());
            applyOne.setForbidFromSocialCircle(imFriendshipRequest.getForbidFromSocialCircle());
            applyOne.setForbidToSocialCircle(imFriendshipRequest.getForbidToSocialCircle());
            applyOne.setFriendNickName(imFriendshipRequest.getFriendNickName());
            applyOne.setUpdateTime(now);
            this.updateById(one);
        }
        ImFriendship applyImFriendship = new ImFriendship();
        BeanUtils.copyProperties(imFriendshipRequest, applyImFriendship);
        applyImFriendship.setBlack(FriendshipBlackEnum.NORMAL.getValue());
        applyImFriendship.setStatus(FriendshipStatusEnum.NORMAL.getValue());
        applyImFriendship.setUpdateTime(now);
        applyImFriendship.setCreateTime(now);
        this.save(applyImFriendship);

        imFriendshipRequest.setApproveStatus(FriendApplyApproveStatusEnum.AGREE.getValue());
        imFriendshipRequestMapper.updateById(imFriendshipRequest);
        return ResponseVO.successResponse(FriendAgreeRequestResp.builder().status(YesNoEnum.YES.value).message("保存成功").build());
    }

    @Override
    public ResponseVO<List<FriendResp>> friends(FriendsReq req) {

        //接受方好友关系保存
        LambdaQueryWrapper<ImFriendship> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ImFriendship::getFromId, req.getUserId());
        lqw.eq(ImFriendship::getBlack, FriendshipBlackEnum.NORMAL.getValue());
        lqw.eq(ImFriendship::getStatus, FriendshipStatusEnum.NORMAL.getValue());

        List<ImFriendship> list = this.list(lqw);

        List<String> friendUserIds = list.stream().map(ImFriendship::getToId).collect(Collectors.toList());

        /**
         * 查询好友用户信息
         */
        List<ImUserData> usersByUserIds = imUserService.getUsersByUserIds(new ArrayList<>(friendUserIds));
        Map<String, ImUserData> userDateGroupByUserId = usersByUserIds.stream().collect(Collectors.toMap(ImUserData::getUserId, Function.identity(),
                (existing, replacement) -> existing));

        List<FriendResp> collect = list.stream().map(item -> {
            FriendResp friendResp = new FriendResp();
            BeanUtils.copyProperties(item, friendResp);
            ImUserData imUserData = userDateGroupByUserId.get(item.getToId());
            BeanUtils.copyProperties(imUserData, friendResp);
            return friendResp;
        }).collect(Collectors.toList());
        return ResponseVO.successResponse(collect);
    }


}
