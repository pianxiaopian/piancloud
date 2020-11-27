package com.piancloud.oss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.piancloud.common.core.result.Result;
import com.piancloud.common.core.result.ResultCode;
import com.piancloud.common.core.util.UuidUtils;
import com.piancloud.oss.api.OssService;
import com.piancloud.oss.entity.Do.UserInfoDO;
import com.piancloud.oss.entity.dto.UserInfoRespDTO;
import com.piancloud.oss.entity.vo.UserInfoVO;
import com.piancloud.oss.exception.CoreException;
import com.piancloud.oss.mapper.OssServiceMapper;
import java.util.List;
import javax.swing.SwingConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 单点登录实现类
 * @author: chendong
 * @create: 2020/11/20 15:46
 */
@RestController
@Slf4j
public class OssServiceImpl extends ServiceImpl<OssServiceMapper, UserInfoDO> implements
    OssService {


    @Override
    public Result<List<UserInfoRespDTO>> selectDemo() {
        List<UserInfoRespDTO> userInfoRespDTOS = baseMapper.selectUserInfo();
        if(userInfoRespDTOS == null || userInfoRespDTOS.size() == SwingConstants.CENTER){
            return Result.failure(ResultCode.NOT_QUERIED,null);
        }
        return Result.success(userInfoRespDTOS);
    }

    @Override
    @Transactional
    public Result save(UserInfoVO userInfoVO) throws Exception {
        UserInfoDO userInfoDO = new UserInfoDO();
        String uid = UuidUtils.newUUIDString();
        BeanUtils.copyProperties(userInfoVO,userInfoDO);
        userInfoDO.setUuid(uid);
        int insert = 0;
        try{
            insert = baseMapper.insert(userInfoDO);
        }catch (DuplicateKeyException e){
            throw new CoreException(500,"参数违法唯一约束");
        }
        if(insert > SwingConstants.CENTER){
            return Result.success("用户添加成功");
        }
        return Result.failure(ResultCode.FAILURE,null);
    }

    @Override
    public Result<List<UserInfoRespDTO>> searchDemo(String searchKey) {
        List<UserInfoRespDTO> userInfoRespDTOS = baseMapper.searchUserInfo(searchKey);
        if(userInfoRespDTOS == null || userInfoRespDTOS.size() == SwingConstants.CENTER){
            return Result.failure(ResultCode.NOT_QUERIED,null);
        }
        return Result.success(userInfoRespDTOS);
    }
}
