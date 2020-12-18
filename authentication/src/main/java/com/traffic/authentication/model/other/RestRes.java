package com.traffic.authentication.model.other;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.traffic.authentication.enums.ErrorCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class RestRes<T> {
    @ApiModelProperty(value = "状态码")
    private int code;
    @ApiModelProperty(value = "描述")
    private String message;
    @ApiModelProperty(value = "数据")
    private T data;

    public void succeed() {
        code = 0;
        message = "succeed";
    }

    public static <T> RestRes<T> succeed(T body) {
        RestRes<T> restRes = new RestRes<>();
        restRes.succeed();
        restRes.setData(body);
        return restRes;
    }

    public static RestRes<PageData> succeed(IPage page) {
        RestRes<PageData> restRes = new RestRes<>();
        restRes.succeed();
        restRes.setData(new PageData(page));
        return restRes;
    }

    public static RestRes failed(int code, String message) {
        RestRes restRes = new RestRes();
        restRes.setCode(code);
        restRes.setMessage(message);
        return restRes;
    }

    public static RestRes failed(ErrorCodeEnum errorEnum) {
        RestRes restRes = new RestRes();
        restRes.setCode(errorEnum.code());
        restRes.setMessage(errorEnum.msg());
        return restRes;
    }
}
