package cn.com.wanshi.chat.friendship.service.impl;

import cn.com.wanshi.chat.common.annotation.ValidateToken;
import cn.com.wanshi.chat.common.enums.FriendApplyApproveStatusEnum;
import cn.com.wanshi.chat.common.enums.RequsetFromEnum;
import cn.com.wanshi.chat.common.utils.SerialNoUtil;
import cn.com.wanshi.chat.friendship.entity.ImFriendshipRequest;
import cn.com.wanshi.chat.friendship.mapper.ImFriendshipRequestMapper;
import cn.com.wanshi.chat.friendship.model.req.FriendRequestCountReq;
import cn.com.wanshi.chat.friendship.model.req.FriendRequestListReq;
import cn.com.wanshi.chat.friendship.model.req.FriendRequestReq;
import cn.com.wanshi.chat.friendship.model.resp.FriendRequestCountResp;
import cn.com.wanshi.chat.friendship.model.resp.FriendRequestHistoryListResp;
import cn.com.wanshi.chat.friendship.model.resp.FriendRequestListResp;
import cn.com.wanshi.chat.friendship.model.resp.FriendRequestResp;
import cn.com.wanshi.chat.friendship.service.IImFriendshipRequestService;
import cn.com.wanshi.chat.user.entity.ImUserData;
import cn.com.wanshi.chat.user.service.IImUserDataService;
import cn.com.wanshi.common.ResponseVO;
import cn.com.wanshi.common.enums.YesNoEnum;
import cn.com.wanshi.common.utils.DateUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 好友申请信息表 服务实现类
 * </p>
 *
 * @author zzc
 * @since 2023-08-07
 */
@Service
public class ImFriendshipRequestServiceImpl extends ServiceImpl<ImFriendshipRequestMapper, ImFriendshipRequest> implements IImFriendshipRequestService {


    @Autowired
    private IImUserDataService imUserService;

    /**
     * 好友申请
     * @param req
     * @return
     */
    @Override
    @ValidateToken
    public ResponseVO<FriendRequestResp> friendRequest(@RequestBody @Validated FriendRequestReq req) {
        LambdaQueryWrapper<ImFriendshipRequest> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ImFriendshipRequest::getFromId, req.getUserId());
        lqw.eq(ImFriendshipRequest::getToId, req.getToId());
        ImFriendshipRequest one = this.getOne(lqw);

        if(Objects.nonNull(one) && Objects.nonNull(one.getApproveStatus()) && one.getApproveStatus().equals(FriendApplyApproveStatusEnum.UNFRIENDED.getValue())){
            return ResponseVO.successResponse(FriendRequestResp.builder().status(FriendApplyApproveStatusEnum.UNFRIENDED.getValue()).message("已被对方拉黑！").build());
        }

        if(Objects.nonNull(one)){
            one.setReadStatus(YesNoEnum.NO.value);
            this.updateById(one);
            return ResponseVO.successResponse(FriendRequestResp.builder().status(0).message("好友申请已经发送!").build());
        }
        ImFriendshipRequest imFriendshipRequest = new ImFriendshipRequest();
        BeanUtils.copyProperties(req, imFriendshipRequest);
        imFriendshipRequest.setFromId(req.getUserId());
        imFriendshipRequest.setReadStatus(YesNoEnum.NO.value);
        imFriendshipRequest.setAppId(Long.parseLong(SerialNoUtil.getUNID()));
        this.save(imFriendshipRequest);
        return ResponseVO.successResponse(FriendRequestResp.builder().status(0).message("好友申请已经发送!").build());
    }

    /**
     * 好友申请统计
     * @param req
     * @return
     */
    @Override
    public ResponseVO<FriendRequestCountResp> friendRequestCount(FriendRequestCountReq req) {
        LambdaQueryWrapper<ImFriendshipRequest> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ImFriendshipRequest::getToId, req.getUserId());
        lqw.eq(ImFriendshipRequest::getReadStatus, req.getReadStatus());
        int count = this.count(lqw);
        return ResponseVO.successResponse(FriendRequestCountResp.builder().count(count).build());
    }

    /**
     * 新增好友
     * @param req
     * @return
     */
    @Override
    public ResponseVO<FriendRequestListResp> friendRequestList(FriendRequestListReq req) {
        LambdaQueryWrapper<ImFriendshipRequest> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ImFriendshipRequest::getToId, req.getUserId());
        lqw.eq(ImFriendshipRequest::getDelFlag, YesNoEnum.NO.value);
        Set<String> userIdList = new HashSet<>();
        List<ImFriendshipRequest> toList = this.list(lqw);
        List<FriendRequestHistoryListResp> toRespList = toList.stream().map(item -> {
            FriendRequestHistoryListResp resp = new FriendRequestHistoryListResp();
            BeanUtils.copyProperties(item, resp);
            resp.setRequsetFrom(RequsetFromEnum.FROM_OTHER.value);
            resp.setUserId(item.getFromId());
            userIdList.add(item.getFromId());
            return resp;
        }).collect(Collectors.toList());

        LambdaQueryWrapper<ImFriendshipRequest> fromLqw = new LambdaQueryWrapper<>();
        fromLqw.eq(ImFriendshipRequest::getFromId, req.getUserId());
        fromLqw.eq(ImFriendshipRequest::getDelFlag, YesNoEnum.NO.value);
        List<ImFriendshipRequest> fromList = this.list(fromLqw);
        List<FriendRequestHistoryListResp> fromRespList = fromList.stream().map(item -> {
            FriendRequestHistoryListResp resp = new FriendRequestHistoryListResp();
            BeanUtils.copyProperties(item, resp);
            resp.setRequsetFrom(RequsetFromEnum.FROM_MYSELF.value);
            resp.setUserId(item.getToId());
            userIdList.add(item.getToId());
            return resp;
        }).collect(Collectors.toList());

        toRespList.addAll(fromRespList);
        if(CollectionUtil.isEmpty(toRespList)){
            return ResponseVO.successResponse();
        }

        /**
         * 查询好友用户信息
         */
        List<ImUserData> usersByUserIds = imUserService.getUsersByUserIds(new ArrayList<>(userIdList));
        Map<String, ImUserData> userDateGroupByUserId = usersByUserIds.stream().collect(Collectors.toMap(ImUserData::getUserId, Function.identity(),
                (existing, replacement) -> existing));

        /**
         * 好友信息整合  & 进三天 分类
         */
        Date now = new Date();

        Map<Integer, List<FriendRequestHistoryListResp>> groupByInThreeDayFlay = toRespList.stream().map(item -> {
            String userId = item.getUserId();
            int i = DateUtil.getDaysBetween(now, item.getCreateTime()) <= 3 ? 1 : 0;
            item.setInThreeDayFlay(i);
            ImUserData imUserData = userDateGroupByUserId.get(userId);
            //防止覆盖了 ImFriendshipRequest 的创建时间影响了
            BeanUtils.copyProperties(imUserData, item);
            return item;
        }).collect(Collectors.groupingBy(FriendRequestHistoryListResp::getInThreeDayFlay));

        FriendRequestListResp resp = new FriendRequestListResp();
        resp.setInThreeDaysList(groupByInThreeDayFlay.get(YesNoEnum.YES.value));
        resp.setBeforeThreeDaysList(groupByInThreeDayFlay.get(YesNoEnum.NO.value));
        return ResponseVO.successResponse(resp);
    }
}
