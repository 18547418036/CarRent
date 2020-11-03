package com.lyn.sys.vo;


import com.lyn.sys.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserVo extends User {
	/*分页参数*/
	private Integer page;
	private Integer limit;
	/*验证码*/
	private String code;
	/*接收多个角色id*/
	private Integer[] ids;
}
