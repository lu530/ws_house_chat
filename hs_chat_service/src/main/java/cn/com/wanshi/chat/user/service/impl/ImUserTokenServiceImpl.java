package cn.com.wanshi.chat.user.service.impl;

import cn.com.wanshi.chat.common.utils.MD5Implementor;
import cn.com.wanshi.chat.common.utils.RedisUtil;
import cn.com.wanshi.chat.common.utils.SerialNoUtil;
import cn.com.wanshi.chat.user.entity.ImUserToken;
import cn.com.wanshi.chat.user.mapper.ImUserTokenMapper;
import cn.com.wanshi.chat.user.model.resp.UserInfoResp;
import cn.com.wanshi.chat.user.service.IImUserTokenService;
import cn.com.wanshi.common.enums.YesNoEnum;
import cn.com.wanshi.common.redis.RedisRepository;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

import static cn.com.wanshi.common.enums.RedisKeyConstants.USER_TOKEN;


/**
 * <p>
 * TOKEN 服务实现类
 * </p>
 *
 * @author zzc
 * @since 2023-07-19
 */
@Service
public class ImUserTokenServiceImpl extends ServiceImpl<ImUserTokenMapper, ImUserToken> implements IImUserTokenService {


    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String createToken(String userId, String clientType, String cuid) {
        String createTokenRedisKey = USER_TOKEN.getKey() + userId ;
        // 读token缓存
        String userToken = (String) redisUtil.get(createTokenRedisKey);
        // 记录最新登录时间
        if(!StringUtils.isEmpty(userToken)){
            //判断是否存在MallUserToken缓存
            String obj = (String) redisUtil.get(userToken);
            if(obj != null){
                return userToken;
            }
        }
        // 创建token;
        String token = userId  + System.currentTimeMillis();
        //生产token
        token = MD5Implementor.MD5Encode(token);
        // 缓存token,缓存15天
        redisUtil.set(createTokenRedisKey,token,USER_TOKEN.getTimeout());
        ImUserToken pojo = new ImUserToken();
        pojo.setTockenId(SerialNoUtil.getUNID());
        pojo.setToken(token);
        pojo.setUserId(userId);
        pojo.setClientType(clientType);
        pojo.setStatus(YesNoEnum.YES.value);
        pojo.setCreateTime(new Date());
        pojo.setCuid(cuid);

        // 缓存登录MallUserToken,缓存15天
        redisUtil.set(token,com.alibaba.fastjson.JSONObject.toJSONString(pojo),USER_TOKEN.getTimeout());
        this.save(pojo);
        return token;
    }

    @Override
    public ImUserToken checkToken(String token) {
        //判断是否存在MallUserToken缓存
        String obj = (String) redisUtil.get(token);
        if(obj != null){
            ImUserToken imUserToken = JSONObject.parseObject(obj, ImUserToken.class);
            return imUserToken;
        }
        return null;
    }

}
