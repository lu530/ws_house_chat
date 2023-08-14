package cn.com.wanshi.chat.user.controller;


import cn.com.wanshi.chat.user.service.ImCommonService;
import cn.com.wanshi.common.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhongzhicheng
 * @data 2023/7/5
 * @time 12:31
 */
@Api(tags = "commonController")
@RestController
@RequestMapping("/im/common")
public class ImCommonController {

    @Autowired
    ImCommonService imCommonService;


    @ApiOperation(value = "上传文件", httpMethod = "POST")
    @PostMapping("/minio/upload")
    public ResponseVO<String> upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        return imCommonService.upload(multipartFile);
    }


    @ApiOperation("根据文件名返回临时查看路径")
    @GetMapping("/minio/{fileName}")
    public ResponseVO<String> fileName(@PathVariable String fileName) throws Exception {
        return imCommonService.fileName(fileName);
    }


    @GetMapping("/download")
    @ApiOperation(value = "下载文件")
    public void download(HttpServletResponse response,  @RequestParam("filePath") String filePath) throws Exception {
         imCommonService.download(response, filePath);
    }


    @GetMapping("/view")
    @ApiOperation(value = "查看文件")
    public ResponseVO view(HttpServletResponse response, @RequestParam("filePath") String filePath) throws Exception {
        return imCommonService.view(response, filePath);
    }



}
