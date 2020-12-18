package com.traffic.server.controller.common;

import com.traffic.server.enums.ErrorCodeEnum;
import com.traffic.server.exception.ApiException;
import com.traffic.server.utils.OSSClientUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述:
 * 〈上传图片〉
 *
 * @author Monkey
 * @create 2020/7/6 10:48
 */
@Slf4j
@Api(value = "【图片/文件】文件/图片处理", tags = "【图片/文件】")
@RestController
@RequestMapping("/common/upload")
public class UploadingController {

    @Autowired
    private OSSClientUtil ossClientUtil;


    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "凭证",defaultValue = "Bearer ")
    })
    @ApiOperation(httpMethod = "POST", value = "[后端]上传文件/图片")
    public String uploadBannerImage(@RequestParam(value = "file") MultipartFile file) {
        String[] split = ossClientUtil.getImgUrl(ossClientUtil.uploadImg2Oss(file)).split("\\?");
        if (split.length <= 0) {
            throw new ApiException(ErrorCodeEnum.FL1.code(),"图片/文件上传失败...");
        }
        return split[0];
    }
}