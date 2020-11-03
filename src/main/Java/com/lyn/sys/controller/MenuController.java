package com.lyn.sys.controller;


import com.lyn.sys.constast.SysConstast;
import com.lyn.sys.domain.Menu;
import com.lyn.sys.domain.User;
import com.lyn.sys.service.MenuService;
import com.lyn.sys.utils.*;
import com.lyn.sys.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 菜单管理控制器
 * 
 * @author LJH
 *
 */
@RestController
@RequestMapping("menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	/**
	 * 加载菜单管理左边的菜单树JSON格式的数据以符合前端js的需要
	 */
	@RequestMapping("loadIndexLeftMenuJson")
	public List<TreeNode> loadIndexLeftMenuJson(MenuVo menuVo){
		// 得到当前登陆的用户对象
		User user=(User) WebUtils.getHttpSession().getAttribute("user");
		// 接收数据表查询返回的菜单数据
		List<Menu> list = null;
		// 设置只查询可用的菜单
		menuVo.setAvailable(SysConstast.AVAILABLE_TRUE);
		// 判断当前用户类型（1：super、2：common）【根据用户类型获取相应的菜单】
		if(user.getType()==SysConstast.USER_TYPE_SUPER) {
			list=this.menuService.queryAllMenuForList(menuVo);
		}else {
			list=this.menuService.queryMenuByUserIdForList(menuVo, user.getUserid());
		}
		// 创建节点树
		List<TreeNode> nodes= new ArrayList<>();
		// 把list里面的数据放到nodes
		for (Menu menu : list) {
			Integer id = menu.getId();
			Integer pid = menu.getPid();
			String title = menu.getTitle();
			String icon = menu.getIcon();
			String href = menu.getHref();
			Boolean spread = menu.getSpread()==SysConstast.SPREAD_TRUE?true:false;
			String target = menu.getTarget();
			nodes.add(new TreeNode(id, pid, title, icon, href, spread, target));
		}
		// 返回数据格式给前台
		return TreeNodeBuilder.builder(nodes, 1);
	}
	
	
	/**
	 * 加载菜单管理页面左侧的菜单树
	 */
	@RequestMapping("loadMenuManagerLeftTreeJson")
	public DataGridView loadMenuManagerLeftTreeJson(MenuVo menuVo){
		menuVo.setAvailable(SysConstast.AVAILABLE_TRUE);//只查询可用的
		List<Menu> list=this.menuService.queryAllMenuForList(menuVo);
		List<TreeNode> nodes= new ArrayList<>();
		// 把list里面的数据放到nodes
		for (Menu menu : list) {
			Integer id=menu.getId();
			Integer pid=menu.getPid();
			String title=menu.getTitle();
			String icon=menu.getIcon();
			String href=menu.getHref();
			Boolean spread=menu.getSpread()==SysConstast.SPREAD_TRUE?true:false;
			String target=menu.getTarget();
			nodes.add(new TreeNode(id, pid, title, icon, href, spread, target));
		}
		return new DataGridView(nodes);
	}
	
	/**
	 * 加载菜单管理页面右侧的菜单列表返回DataGridView
	 */
	@RequestMapping("loadAllMenu")
	public DataGridView loadAllMenu(MenuVo menuVo) {
		return this.menuService.queryAllMenu(menuVo);
	}
	/**
	 * 添加菜单
	 */
	@RequestMapping("addMenu")
	public ResultObj addMenu(MenuVo menuVo) {
		try {
			this.menuService.addMenu(menuVo);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	/**
	 * 修改菜单
	 */
	@RequestMapping("updateMenu")
	public ResultObj updateMenu(MenuVo menuVo) {
		try {
			this.menuService.updateMenu(menuVo);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	/**
	 * 根据id判断当前菜单有没有子节点
	 * 有返回code>=0
	 * 没有 返回code<0
	 */
	@RequestMapping("checkMenuHasChildren")
	public ResultObj checkMenuHasChildren(MenuVo menuVo){
		//根据pid查询菜单数量
		Integer count=this.menuService.queryMenuByPid(menuVo.getId());
		if(count>0) {
			return ResultObj.STATUS_TRUE;
		}else {
			return ResultObj.STATUS_FALSE;
		}
	}
	
	/**
	 * 删除菜单
	 */
	@RequestMapping("deleteMenu")
	public ResultObj deleteMenu(MenuVo menuVo) {
		try {
			this.menuService.deleteMenu(menuVo);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
	
}
