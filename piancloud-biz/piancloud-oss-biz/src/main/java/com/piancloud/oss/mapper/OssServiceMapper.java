package com.piancloud.oss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.piancloud.oss.entity.Do.UserInfoDO;
import com.piancloud.oss.entity.dto.UserInfoRespDTO;
import java.util.List;
import org.apache.ibatis.annotations.Select;

/**
 * @description: 单点登录mapper层
 * @author: chendong
 * @create: 2020/11/20 15:48
 */
public interface OssServiceMapper extends BaseMapper<UserInfoDO> {

    @Select("select u.uid,u.user_name,u.name,u.id_card_num from user_info u where u.state = '0'")
    List<UserInfoRespDTO> selectUserInfo();
}
