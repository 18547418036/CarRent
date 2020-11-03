package com.lyn.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyn.sys.domain.Menu;
import com.lyn.sys.mapper.MenuMapper;
import com.lyn.sys.service.MenuService;
import com.lyn.sys.utils.DataGridView;
import com.lyn.sys.vo.MenuVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MenuServiceImpl implements MenuService {

	private Logger logger = Logger.getLogger(MenuServiceImpl.class);
	
	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private JedisPool jedisPool;

	/**
	 * 查询所有菜单【适用于管理员权限】
	 */
	@Override
	public List<Menu> queryAllMenuForList(MenuVo menuVo) {
		return menuMapper.queryAllMenu(menuVo);
	}

	/**
	 * 根据用户id查询菜单【适用于普通用户权限】
	 */
	@Override
	public List<Menu> queryMenuByUserIdForList(MenuVo menuVo, Integer userId) {
		return menuMapper.queryMenuByUid(menuVo.getAvailable(),userId);
	}

	/**
	 * 查询所有菜单【适用于渲染表格】
	 * keys *
	 * lrange ... 0 10
	 */
	@Override
	public DataGridView queryAllMenu(MenuVo menuVo) {
		// 从redis缓存中获取基础数据
		Jedis jedis = jedisPool.getResource();
		// 获取所有满足条件的key
		Set<String> keys = jedis.keys("menuData:*"); // 命令用于查找所有符合给定模式 pattern 的 key 。。
		logger.info("检查满足条件的key："+"keys:"+keys+"keys.size():"+keys.size());
		Page<Object> page = null;
		List<Menu> data = new ArrayList<>();
		if (keys == null || keys.size() == 0){  // keys:[]   keys.size():0
			logger.info("Redis缓存中没有数据");
			// 表示redis缓存中没有数据，去Mysql数据库中去查询
			page = PageHelper.startPage(menuVo.getPage(), menuVo.getLimit());
			data = this.menuMapper.queryAllMenu(menuVo);
			// 将查询到的数据保存到缓存中

			//将Menu转换为json格式
			for(Menu menu : data){
				logger.info("进入for循环");
				// Redis Hset 命令用于为哈希表中的字段赋值 。
				// 如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。
				// 如果字段已经存在于哈希表中，旧值将被覆盖。
				jedis.hset("menuData:"+menu.getId(),"id",menu.getId()+"");
				logger.info("id存入成功");
				jedis.hset("menuData:"+menu.getId(),"pid",menu.getPid()+"");
				logger.info("pid存入成功");
				jedis.hset("menuData:"+menu.getId(),"title",menu.getTitle()+"");
				logger.info("title存入成功");
				jedis.hset("menuData:"+menu.getId(),"href",menu.getHref()+"");
				logger.info("href存入成功");
				jedis.hset("menuData:"+menu.getId(),"spread",menu.getSpread()+"");
				logger.info("spread存入成功");
				jedis.hset("menuData:"+menu.getId(),"target",menu.getTarget()+"");
				logger.info("target存入成功");
				jedis.hset("menuData:"+menu.getId(),"icon",menu.getIcon()+"");
				logger.info("icon存入成功");
				jedis.hset("menuData:"+menu.getId(),"available",menu.getAvailable()+"");
				logger.info("available存入成功");
			}
			logger.info("菜单基础数据：从数据库中查询....");
		}else {
			// 从redis缓存获取数据
			for (String key : keys){
				Menu menu = new Menu();
				menu.setId(Integer.parseInt(jedis.hget(key,"id")));
				menu.setPid(Integer.parseInt(jedis.hget(key,"pid")));
				menu.setTitle((jedis.hget(key,"title")));
				menu.setHref((jedis.hget(key,"href")));
				menu.setSpread(Integer.parseInt(jedis.hget(key,"spread")));
				menu.setTarget((jedis.hget(key,"target")));
				menu.setIcon((jedis.hget(key,"icon")));
				menu.setAvailable(Integer.parseInt(jedis.hget(key,"available")));
				data.add(menu);
			}
			for (Menu m : data){
				System.out.println(m);
			}
			logger.info("菜单基础数据：从Redis缓存中获取....");
		}
		return new DataGridView(page.getTotal(), data);
	}

	@Override
	public void addMenu(MenuVo menuVo) {
		this.menuMapper.insertSelective(menuVo);
	}

	@Override
	public void updateMenu(MenuVo menuVo) {
		this.menuMapper.updateByPrimaryKeySelective(menuVo);
	}

	@Override
	public Integer queryMenuByPid(Integer pid) {
		return this.menuMapper.queryMenuByPid(pid);
	}

	@Override
	public void deleteMenu(MenuVo menuVo) {
		
		//删除菜单表的数据
		this.menuMapper.deleteByPrimaryKey(menuVo.getId());
		
		//根据菜单id删除sys_role_menu里面的数据
		this.menuMapper.deleteRoleMenuByMid(menuVo.getId());
	}

}
