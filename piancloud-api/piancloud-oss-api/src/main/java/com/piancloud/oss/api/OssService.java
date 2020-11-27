package com.piancloud.oss.api;

import com.piancloud.common.core.result.Result;
import com.piancloud.oss.entity.dto.UserInfoRespDTO;
import com.piancloud.oss.entity.vo.UserInfoVO;
import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 单点登录接口服务
 * @author: chendong
 * @create: 2020/11/20 15:02
 */
@Api(value = "单点登录服务接口")
public interface OssService {


    /**
     * 查询全部用户信息
     * @return
     */
    @GetMapping("/selectDemo")
    Result<List<UserInfoRespDTO>> selectDemo();

    /**
     * 保存用户信息
     */
    @PostMapping("/save")
    Result save(@RequestBody @Valid UserInfoVO userInfoVO) throws Exception;

    /**
     * 搜索用户信息
     */
    @GetMapping("/search")
    Result<List<UserInfoRespDTO>> searchDemo(@RequestParam(required = false) String searchKey);
}
