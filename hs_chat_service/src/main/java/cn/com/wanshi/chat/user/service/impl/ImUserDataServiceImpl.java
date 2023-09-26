package cn.com.wanshi.chat.user.service.impl;

import cn.com.wanshi.chat.common.utils.MD5Implementor;
import cn.com.wanshi.chat.common.utils.SerialNoUtil;
import cn.com.wanshi.chat.common.utils.VerifyCodeUtil;
import cn.com.wanshi.chat.user.model.req.FindUserInfoReq;
import cn.com.wanshi.chat.user.entity.ImUserData;
import cn.com.wanshi.chat.user.mapper.ImUserDataMapper;
import cn.com.wanshi.chat.user.model.req.*;
import cn.com.wanshi.chat.user.model.resp.FindUserInfoResp;
import cn.com.wanshi.chat.user.model.resp.UserInfoResp;
import cn.com.wanshi.chat.user.service.IImUserDataService;
import cn.com.wanshi.chat.user.service.IImUserTokenService;
import cn.com.wanshi.common.ResponseVO;
import cn.com.wanshi.common.enums.DelFlagEnum;
import cn.com.wanshi.common.enums.ReceiptCodeTypeEnum;
import cn.com.wanshi.common.enums.RedisKeyConstants;
import cn.com.wanshi.common.enums.YesNoEnum;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static cn.com.wanshi.common.enums.ApiCode.USER_NO_FIND;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zzc
 * @since 2023-07-19
 */
@Service
public class ImUserDataServiceImpl extends ServiceImpl<ImUserDataMapper, ImUserData> implements IImUserDataService {


    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ImUserDataMapper imuserDataMapper;



    @Autowired
    private IImUserTokenService iImUserTokenService;

    @Autowired
    private VerifyCodeUtil verifyCodeUtil;


    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public ResponseVO<UserInfoResp> getUserInfo(UserInfoReq req) {
        LambdaQueryWrapper<ImUserData> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ImUserData::getUserId, req.getUserId());
        lqw.eq(ImUserData::getDelFlag, DelFlagEnum.NORMAL.getCode());
        List<ImUserData> imUserDataEntities = imuserDataMapper.selectList(lqw);
        if(CollectionUtil.isNotEmpty(imUserDataEntities)){
            ImUserData imUserDataEntity = imUserDataEntities.get(0);
            UserInfoResp userInfoResp = new UserInfoResp();
            BeanUtil.copyProperties(imUserDataEntity, userInfoResp);
            return ResponseVO.successResponse(userInfoResp);
        }
        return ResponseVO.errorResponse();
    }

    @Override
    public ResponseVO<UserInfoResp> loginIn(LoginReq req) {
        LambdaQueryWrapper<ImUserData> queryWrapper = new LambdaQueryWrapper();
        if(Validator.isEmail(req.getAccount())){
            queryWrapper.eq(ImUserData::getEmail, req.getAccount());
        }else{
            queryWrapper.eq(ImUserData::getAccount, req.getAccount());
        }
        queryWrapper.eq(ImUserData::getDelFlag, DelFlagEnum.NORMAL.getCode());
        ImUserData one = this.getOne(queryWrapper);
        Assert.isTrue(one != null,"该用户账号未注册");

        String md5Encode = MD5Implementor.MD5Encode(req.getPassword());
        Assert.isTrue(md5Encode.equals(one.getPassword()),"密码错误");
        String token = iImUserTokenService.createToken(one.getUserId(), req.getClientType(), req.getCuid());
        UserInfoResp userInfoResp = new UserInfoResp();
        BeanUtil.copyProperties(one, userInfoResp);
        userInfoResp.setPassword("");
        userInfoResp.setToken(token);
        return ResponseVO.successResponse(userInfoResp);
    }

    @Override
    public ResponseVO<Boolean> getReceiptCode(GetReceiptCodeReq req) {
        // 定义Redis的key
        String key = RedisKeyConstants.USER_RECEIPT.getKey() + req.getEmail();

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String verifyCode = valueOperations.get(key);
        if(StringUtils.isEmpty(verifyCode)){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(req.getEmail());
            ReceiptCodeTypeEnum enumByReceiptCodeType = ReceiptCodeTypeEnum.getEnumByReceiptCodeType(req.getReceiptCodeType());
            message.setSubject(enumByReceiptCodeType.getReceiptCodeTypeName());
             verifyCode = verifyCodeUtil.generateVerifyCode();
            message.setText(MessageFormat.format(enumByReceiptCodeType.getMessage(), req.getEmail(), verifyCode));
            message.setFrom(from);
            mailSender.send(message);
            valueOperations.set(key, verifyCode, RedisKeyConstants.USER_RECEIPT.getTimeout(), RedisKeyConstants.USER_RECEIPT.getUnit());
        }
        return ResponseVO.successResponse(true);
    }

    @Override
    public ResponseVO<Boolean> signUp(SignUpReq req) {
        LambdaQueryWrapper<ImUserData> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ImUserData::getAccount, req.getAccount());
        queryWrapper.eq(ImUserData::getDelFlag, DelFlagEnum.NORMAL.getCode());
        ImUserData one = this.getOne(queryWrapper);
        Assert.isTrue(one == null,"该用户账号已经注册");

        LambdaQueryWrapper<ImUserData> emailQueryWrapper = new LambdaQueryWrapper();
        emailQueryWrapper.eq(ImUserData::getEmail, req.getEmail());
        emailQueryWrapper.eq(ImUserData::getDelFlag, DelFlagEnum.NORMAL.getCode());
        ImUserData emailOne = this.getOne(emailQueryWrapper);
        Assert.isTrue(emailOne == null,"该邮箱已经注册");


        Integer checkVerifyCodeResult = verifyCodeUtil.checkVerifyCode(req.getEmail(), req.getReceiptCode());
        Assert.isTrue(checkVerifyCodeResult.equals(YesNoEnum.YES.value), "验证码校验不通过");
        ImUserData imUserDate = new ImUserData();
        BeanUtils.copyProperties(req, imUserDate);
        String md5Encode = MD5Implementor.MD5Encode(req.getPassword());
        imUserDate.setPassword(md5Encode);
        imUserDate.setUserId("U"+ SerialNoUtil.getUNID());
        Date now = new Date();
        imUserDate.setCreateTime(now);
        imUserDate.setUpdateTime(now);
        this.save(imUserDate);
        return ResponseVO.successResponse(true);
    }

    @Override
    @Transactional
    public ResponseVO<Boolean> reSetPassWord(ReSetPassWordReq req) {
        LambdaQueryWrapper<ImUserData> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ImUserData::getEmail, req.getEmail());
        queryWrapper.eq(ImUserData::getDelFlag, DelFlagEnum.NORMAL.getCode());
        ImUserData one = this.getOne(queryWrapper);
        Assert.isTrue(one != null,"该邮箱还没注册用户，请先注册");

        Integer checkVerifyCodeResult = verifyCodeUtil.checkVerifyCode(req.getEmail(), req.getReceiptCode());
        Assert.isTrue(checkVerifyCodeResult.equals(YesNoEnum.YES.value), "验证码校验不通过");
        String md5Encode = MD5Implementor.MD5Encode(req.getPassword());
        if(md5Encode.equals(one.getPassword())){
            return ResponseVO.successResponse(true);
        }
        one.setPassword(md5Encode);
        this.update(one, queryWrapper);
        return ResponseVO.successResponse(true);
    }

    @Override
    public ImUserData getUserInfoByUserId(String userId) {
        LambdaQueryWrapper<ImUserData> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ImUserData::getUserId, userId);
        queryWrapper.eq(ImUserData::getDelFlag, DelFlagEnum.NORMAL.getCode());
        ImUserData one = this.getOne(queryWrapper);
        return one;
    }

    @Override
    public ResponseVO<FindUserInfoResp> findUserInfo(FindUserInfoReq req) {
        LambdaQueryWrapper<ImUserData> queryWrapper = new LambdaQueryWrapper();
        if(Validator.isEmail(req.getAccountWords())){
            queryWrapper.eq(ImUserData::getEmail, req.getAccountWords());
        }else{
            queryWrapper.eq(ImUserData::getAccount, req.getAccountWords());
        }
        queryWrapper.eq(ImUserData::getDelFlag, DelFlagEnum.NORMAL.getCode());
        ImUserData one = this.getOne(queryWrapper);
        if(Objects.isNull(one)){
            return ResponseVO.errorResponse(USER_NO_FIND.getValue(), USER_NO_FIND.getMessage());
        }

        FindUserInfoResp resp = new FindUserInfoResp();
        BeanUtils.copyProperties(one, resp);
        return ResponseVO.successResponse(resp);
    }

    @Override
    public List<ImUserData> getUsersByUserIds(List<String> userIds) {
        LambdaQueryWrapper<ImUserData> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(ImUserData::getUserId, userIds);
        queryWrapper.eq(ImUserData::getDelFlag, DelFlagEnum.NORMAL.getCode());
        return this.list(queryWrapper);
    }


}
