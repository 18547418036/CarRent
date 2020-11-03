package com.lyn.sys.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import com.lyn.sys.constast.SysConstast;
import com.lyn.sys.domain.User;
import com.lyn.sys.service.LogInfoService;
import com.lyn.sys.service.UserService;
import com.lyn.sys.utils.ActiveUser;
import com.lyn.sys.utils.WebUtils;
import com.lyn.sys.vo.LogInfoVo;
import com.lyn.sys.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * 用户登陆控制器
 * @author iamhere
 *
 */
@Controller
@RequestMapping("login")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private LogInfoService logInfoService;
	
	
	/**
	 * 跳转到登陆页面
	 */
	@RequestMapping("toLogin")
	public String toLogin() {
		return "system/main/login";
	}


	/**
	 * 登陆方法(Shiro安全认证）
	 */
	@RequestMapping("login")
	public String login(UserVo userVo,Model model) {
		// 先判断验证码
		String code = WebUtils.getHttpSession().getAttribute("code").toString();
		if (userVo.getCode().equals(code)){
			// 获取主体
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(userVo.getLoginname(),userVo.getPwd());
			try{
				subject.login(token);
				// 获取身份
				ActiveUser activerUser = (ActiveUser)subject.getPrincipal();
				System.out.println();
				// 放到session
				WebUtils.getHttpSession().setAttribute("user", activerUser.getUser());
				// 记录登陆日志 向sys_login_log里面插入数据
				LogInfoVo logInfoVo=new LogInfoVo();
				logInfoVo.setLogintime(new Date());
				logInfoVo.setLoginname(activerUser.getUser().getLoginname());
				logInfoVo.setLoginip(WebUtils.getHttpServletRequest().getRemoteAddr());
				logInfoService.addLogInfo(logInfoVo);
				return "system/main/index";
			}catch (AuthenticationException e){
				model.addAttribute("error", SysConstast.USER_LOGIN_ERROR_MSG);
				return "system/main/login";
			}
		}else {
			model.addAttribute("error", SysConstast.USER_LOGIN_CODE_ERROR_MSG);
			return "system/main/login";
		}
	}

	
//	/**
//	 * 登陆方法(一般方法）
//	 */
//	@RequestMapping("login")
//	public String login(UserVo userVo, Model model) {
//		// 先判断验证码
//		String code = WebUtils.getHttpSession().getAttribute("code").toString();
//		if (userVo.getCode().equals(code)){
//			User user=this.userService.login(userVo);
//			if(null!=user) {
//				// 放到session
//				WebUtils.getHttpSession().setAttribute("user", user);
//				// 记录登陆日志 向sys_login_log里面插入数据
//				LogInfoVo logInfoVo=new LogInfoVo();
//				logInfoVo.setLogintime(new Date());
//				logInfoVo.setLoginname(user.getRealname()+"-"+user.getLoginname());
//				logInfoVo.setLoginip(WebUtils.getHttpServletRequest().getRemoteAddr());
//
//				logInfoService.addLogInfo(logInfoVo);
//				return "system/main/index";
//			}else {
//				model.addAttribute("error", SysConstast.USER_LOGIN_ERROR_MSG);
//				return "system/main/login";
//			}
//		}else {
//			model.addAttribute("error", SysConstast.USER_LOGIN_CODE_ERROR_MSG);
//			return "system/main/login";
//		}
//	}

	/**
	 * 得到登陆验证码
	 * 需要引入 hutool maven依赖
	 */
	@RequestMapping("getCode")
	public void getCode(HttpServletResponse response, HttpSession session) throws IOException {
		// 定义图形验证码的长和宽
		LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36, 4, 5);
		session.setAttribute("code",lineCaptcha.getCode());
		// 拿到输出流
		ServletOutputStream outputStream = response.getOutputStream();
		// 输出图像
		ImageIO.write(lineCaptcha.getImage(),"JPEG",outputStream);
	}
	
}
