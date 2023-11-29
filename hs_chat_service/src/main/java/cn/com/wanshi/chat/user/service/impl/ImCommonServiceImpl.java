package cn.com.wanshi.chat.user.service.impl;

import cn.com.wanshi.chat.common.utils.MinioUtil;
import cn.com.wanshi.chat.user.service.ImCommonService;
import cn.com.wanshi.common.ResponseVO;
import io.minio.ObjectWriteResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zzc
 * @since 2023-07-19
 */
@Service
public class ImCommonServiceImpl implements ImCommonService {

    @Value("${minio.bucket-name}")
    private String bucketName;


    @Autowired
    MinioUtil minioUtil;



    public ResponseVO<String> upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        ObjectWriteResponse objectWriteResponse = minioUtil.uploadFile("ws-house-chat-2023", multipartFile.getOriginalFilename(), multipartFile);
        return ResponseVO.successResponse(bucketName + "/"+ multipartFile.getOriginalFilename());
    }


    public ResponseVO<String> fileName(@PathVariable String fileName) throws Exception {
        String objectURL = minioUtil.getObjectURL(bucketName, fileName);
        return ResponseVO.successResponse(objectURL);
    }


    public void download(HttpServletResponse response, @RequestParam("filePath") String filePath) throws Exception {
        if(!StringUtils.isEmpty(filePath)){
            minioUtil.downloadFile(bucketName, filePath, response);
        }
    }


    public ResponseVO view(HttpServletResponse response, @RequestParam("filePath") String filePath) throws Exception {
        if(!StringUtils.isEmpty(filePath)){
            InputStream inputStream = minioUtil.getObject(bucketName, filePath);
            if (inputStream != null) {
                //   response.setContentType("application/x-png;charset=utf-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + filePath);
                response.setCharacterEncoding("UTF-8");
                byte[] buffer = new byte[1024];
                BufferedInputStream bis = null;
                try {
                    bis = new BufferedInputStream(inputStream);
                    ServletOutputStream outputStream = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        outputStream.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
        return new ResponseVO(500, "下载失败");
    }



}
