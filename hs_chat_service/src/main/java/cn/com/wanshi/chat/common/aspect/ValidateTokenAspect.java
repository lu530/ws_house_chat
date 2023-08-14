package cn.com.wanshi.chat.common.aspect;


import cn.com.wanshi.chat.common.annotation.ValidateToken;
import cn.com.wanshi.chat.common.model.BaseReq;
import cn.com.wanshi.chat.user.entity.ImUserData;
import cn.com.wanshi.chat.user.entity.ImUserToken;
import cn.com.wanshi.chat.user.service.IImUserDataService;
import cn.com.wanshi.chat.user.service.IImUserTokenService;
import cn.com.wanshi.common.ResponseVO;
import cn.com.wanshi.common.enums.ApiCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author zhongzhicheng
 * @description CheckTokenAspect
 * @date 2023/07/31
 */
@Aspect
@Slf4j
@Component
public class ValidateTokenAspect {

    @Autowired
    private IImUserDataService iImUserDataService;

    @Autowired
    private IImUserTokenService iImUserTokenService;


    @Around("@annotation(cn.com.wanshi.chat.common.annotation.ValidateToken)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ValidateToken validateToken = method.getAnnotation(ValidateToken.class);
        Object[] args = joinPoint.getArgs();

        ImUserData userInfoDto = null;
        boolean hasObjExtendBaseReq = false;
        for (Object arg : args) {
            if (!(arg instanceof BaseReq)) {
                continue;
            }
            hasObjExtendBaseReq = true;
            BaseReq baseReq = (BaseReq) arg;
            String token = baseReq.getToken();

            //checkToken=false不强制校验，token为空允许跳过
            if (StringUtils.isBlank(token) && !validateToken.checkToken()) {
                return joinPoint.proceed(args);
            }else{
                ImUserToken imUserToken = iImUserTokenService.checkToken(token);
                if (Objects.isNull(imUserToken)) {
                    log.error("req error1:{},{},{},{}", token, request.getLocalAddr(), request.getLocalPort(), request.getRequestURI());
                    return ResponseVO.errorResponse(ApiCode.TOKEN_INVALID.getValue(), ApiCode.TOKEN_INVALID.getMessage());
                }
                baseReq.setUserId(imUserToken.getUserId());
            }

            if (validateToken.needUserInfo() && StringUtils.isNotEmpty(baseReq.getUserId())) {
                 userInfoDto = iImUserDataService.getUserInfoByUserId(baseReq.getUserId());
                baseReq.setImUserData(userInfoDto);
            }
            break;
        }
        if (!hasObjExtendBaseReq) {
            throw new IllegalStateException("未找到继承" + BaseReq.class.getName() + "的对象!");
        }
        return joinPoint.proceed(args);
    }



}
