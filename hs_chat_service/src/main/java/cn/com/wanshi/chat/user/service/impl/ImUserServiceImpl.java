package cn.com.wanshi.chat.user.service.impl;

import cn.com.wanshi.chat.user.dao.ImUserDataEntity;
import cn.com.wanshi.chat.user.dao.mapper.ImUserDataMapper;
import cn.com.wanshi.chat.user.model.req.UserInfoReq;
import cn.com.wanshi.chat.user.model.resp.UserInfoResp;
import cn.com.wanshi.chat.user.service.ImUserService;
import cn.com.wanshi.common.ResponseVO;
import cn.com.wanshi.common.enums.DelFlagEnum;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author li
 * @data 2023/4/9
 * @time 11:22
 */
@Service
public class ImUserServiceImpl implements ImUserService {

    @Autowired
    private ImUserDataMapper imuserDataMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public ResponseVO<UserInfoResp> getUserInfo(UserInfoReq req) {
        LambdaQueryWrapper<ImUserDataEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ImUserDataEntity::getUserId, req.getUserId());
        lqw.eq(ImUserDataEntity::getDelFlag, DelFlagEnum.NORMAL.getCode());
        List<ImUserDataEntity> imUserDataEntities = imuserDataMapper.selectList(lqw);
        if(CollectionUtil.isNotEmpty(imUserDataEntities)){
            ImUserDataEntity imUserDataEntity = imUserDataEntities.get(0);
            UserInfoResp userInfoResp = new UserInfoResp();
            BeanUtil.copyProperties(imUserDataEntity, userInfoResp);
            return ResponseVO.successResponse(userInfoResp);
        }
        return ResponseVO.errorResponse();
    }
}
