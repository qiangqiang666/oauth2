package com.traffic.server.unionPay.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: server
 * @description
 * @author: belive
 * @create: 2020-12-17 03:10
 **/
@Data
@ApiModel("商品信息")
public class GoodsParam {

    @ApiModelProperty(value = "商品ID")
    private String goodsId;
     @ApiModelProperty("商品名称")
    private String goodsName;
     @ApiModelProperty("商品数量")
    private Integer quantity;
     @ApiModelProperty("商品单价 分")
    private Long price;
     @ApiModelProperty("商品说明")
    private String body;
     @ApiModelProperty("商品分类")
    private String goodsCategory;
     @ApiModelProperty("商品单位")
    private String unit;
     @ApiModelProperty("商品折扣")
    private String discount;

}
