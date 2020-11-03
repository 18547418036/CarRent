package com.lyn.sys.mapper;


import com.lyn.sys.domain.User;
import com.lyn.sys.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * 登陆(一般方法） 在当前项目中，并没有严格按照VO来写，参数user中不止有name和pwd，还有其他属性，不过其他属性都是null值
     */
    User login(User user);

    /**
     * 登陆 （Shiro安全认证）根据用户名查询用户
     * @param username
     */
    User queryUserByUsername(@Param("loginname") String username);
    /**
     * 查询用户
     */
    List<User> queryAllUser(User user);
    /**
     * 保存用户和角色的关系
     * @param userid
     * @param rid
     */
	void insertUserRole(@Param("uid") Integer userid, @Param("rid") Integer rid) ;


}