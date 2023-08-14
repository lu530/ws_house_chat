package cn.com.wanshi.chat.user.service;


import cn.com.wanshi.common.ResponseVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author zzc
 * @since 2023-07-19
 */
public interface ImCommonService {

    ResponseVO<String> upload(@RequestParam("file") MultipartFile multipartFile) throws Exception;

    ResponseVO<String> fileName(@PathVariable String fileName) throws Exception;


    void download(HttpServletResponse response, @RequestParam("filePath") String filePath) throws Exception;


    ResponseVO view(HttpServletResponse response, @RequestParam("filePath") String filePath) throws Exception;


}
