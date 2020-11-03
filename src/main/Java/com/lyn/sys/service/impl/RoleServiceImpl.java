package com.lyn.sys.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyn.sys.constast.SysConstast;
import com.lyn.sys.domain.Menu;
import com.lyn.sys.domain.Role;
import com.lyn.sys.mapper.MenuMapper;
import com.lyn.sys.mapper.RoleMapper;
import com.lyn.sys.service.RoleService;
import com.lyn.sys.utils.DataGridView;
import com.lyn.sys.utils.TreeNode;
import com.lyn.sys.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<Role> queryAllRoleForList(RoleVo roleVo) {
		return roleMapper.queryAllRole(roleVo);
	}

	/**
	 * 后期权限管理完成之后再来改
	 */
	@Override
	public List<Role> queryRoleByUserIdForList(RoleVo roleVo, Integer userId) {
		return roleMapper.queryAllRole(roleVo);
	}

	@Override
	public DataGridView queryAllRole(RoleVo roleVo) {
		Page<Object> page = PageHelper.startPage(roleVo.getPage(), roleVo.getLimit());
		List<Role> data = this.roleMapper.queryAllRole(roleVo);
		return new DataGridView(page.getTotal(), data);
	}

	@Override
	public void addRole(RoleVo roleVo) {
		this.roleMapper.insertSelective(roleVo);
	}

	@Override
	public void updateRole(RoleVo roleVo) {
		this.roleMapper.updateByPrimaryKeySelective(roleVo);
	}

	@Override
	public void deleteRole(Integer roleid) {
		// 删除角色表的数据
		this.roleMapper.deleteByPrimaryKey(roleid);
		// 根据角色id删除sys_role_meun里面的数据
		this.roleMapper.deleteRolMenuByRid(roleid);
		// 根据角色id删除sys_role_user里面的数据
		this.roleMapper.deleteRoleUserByRid(roleid);

	}

	@Override
	public void deleteBatchRole(Integer[] ids) {
		for (Integer rid : ids) {
			deleteRole(rid);
		}
	}

	@Override
	public DataGridView initRoleMenuTreeJson(Integer roleid) {
		// 查询所有的可用的菜单
		Menu menu = new Menu();
		menu.setAvailable(SysConstast.AVAILABLE_TRUE);
		List<Menu> allMenu = menuMapper.queryAllMenu(menu);
		// 根据角色ID查询当前角色拥有的菜单
		List<Menu> roleMenu = menuMapper.queryMenuByRoleId(SysConstast.AVAILABLE_TRUE, roleid);

		List<TreeNode> data = new ArrayList<>();
		for (Menu m1 : allMenu) {
			String checkArr = SysConstast.CODE_ZERO+"";
			for (Menu m2 : roleMenu) {
				if (m1.getId() == m2.getId()) {
					checkArr = SysConstast.CODE_ONE+"";
					break;
				}
			}
			Integer id = m1.getId();
			Integer pid = m1.getPid();
			String title = m1.getTitle();
			Boolean spread = m1.getSpread() == SysConstast.SPREAD_TRUE ? true : false;
			data.add(new TreeNode(id, pid, title, spread, checkArr));
		}
		return new DataGridView(data);
	}

	@Override
	public void saveRoleMenu(RoleVo roleVo) {
		Integer rid=roleVo.getRoleid();
		Integer [] mids=roleVo.getIds();
		//根据rid删除sys_role_menu里面所有数据
		this.roleMapper.deleteRolMenuByRid(rid);
		//保存角色和菜单的关系
		for (Integer mid : mids) {
			this.roleMapper.insertRoleMenu(rid,mid);
		}
	}

}
