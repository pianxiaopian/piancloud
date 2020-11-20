package com.piancloud.oss.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 响应前端类
 * @author: chendong
 * @create: 2020/11/20 15:39
 */
@ApiModel(value = "响应前端UserInfoResp类", description="查询用户信息返回类")
@Data
public class UserInfoRespDTO {

    @ApiModelProperty(value = "uid")
    private String uid;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 用户真实姓名
     */
    @ApiModelProperty(value = "用户真实姓名")
    private String name;

    /**
     * 用户身份证号
     */
    @ApiModelProperty(value = "用户身份证号")
    private String idCardNum;

}
