package com.lyn.sys.utils;

import com.lyn.sys.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author iamhere
 * @description shiro 登陆认证
 * @date 2020/8/21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser {
    private User user;
}
