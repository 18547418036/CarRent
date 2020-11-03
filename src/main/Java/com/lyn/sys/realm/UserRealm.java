package com.lyn.sys.realm;

import com.lyn.sys.constast.SysConstast;
import com.lyn.sys.domain.Role;
import com.lyn.sys.domain.User;
import com.lyn.sys.service.UserService;
import com.lyn.sys.utils.ActiveUser;
import com.lyn.sys.utils.DataGridView;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author iamhere
 * @description shiro
 *      AuthorizingRealm：既可以认证也可以授权
 *      AuthenticationRealm:负责认证
 *
 * @date 2020/8/21
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
        // return super.getName();
    }

    /** @date 2020/8/22
     * @description 认证 该方法返回空null则表示用户不存在
     *       一般的登陆，是将用户名和密码一起发送到数据库去查询
     *       Shiro安全认证：先将用户名发送到数据库进行查询，查询出对象后再来做密码匹配
     * @param   authenticationToken
     * @return org.apache.shiro.authc.AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取身份（当前指用户名）
        String username = authenticationToken.getPrincipal().toString();
        // 根据用户名查询用户
        User user = this.userService.queryUserByUsername(username);
        if (user != null){
            // 这里设置了一个自定义的activeUser，作用是接收查询到的用户的信息（如果只做登陆验证的话，可以不需要这个类，直接用User接收即可，ActiveUser是为了之后做权限管理）
            ActiveUser activeUser = new ActiveUser(user);
            // 创建盐值（盐值一般是存在于数据库中的）
            ByteSource credentialSalt = ByteSource.Util.bytes(activeUser.getUser().getAddress());
            /**
             * 参数说明
             * @Param principal 身份(这里可以传任意对象，当前我们的目的是将数据库查到的对象返回，以便于之后我们将所需要的内容放入Session中)
             * @Param credentials 凭证(从数据库中查到散列之后的密码）
             * @Param credentialSalt 盐值
             * @Param realmName 当前类名
             * // 三个参数：SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser,activeUser.getUser().getPwd(),this.getName());
             * // 四个参数：SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser,activeUser.getUser().getPwd(),credentialSalt,this.getName());
             */
            // 验证
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser,activeUser.getUser().getPwd(),credentialSalt,this.getName());
            // 返回验证结果
            return info;
        }else {
            // 用户不存在  Shiro 会抛出 UnknowAccountException
            return null;
        }
    }



    /** @date 2020/9/12
     * @description 授权
     * @param   principalCollection
     * @return org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        /*// 获取登陆的用户信息
        // Object primaryPrincipal = principalCollection.getPrimaryPrincipal();
        // 返回的类型取决于你认证时的传的是什么
        ActiveUser activeUser = (ActiveUser) principalCollection.getPrimaryPrincipal();

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();

        // 添加角色*/


        return null;
    }



}
