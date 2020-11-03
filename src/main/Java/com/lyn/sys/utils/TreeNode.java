package com.lyn.sys.utils;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @date 2020/9/9
 * @description  具有层级关系的菜单类
 * 		加载左边导航菜单时，Menu类无法满足需求（缺少层级关系），于是需要创建TreeNode类构成层级关系。
 * 		在navs.json中，并没有id和pid，但是它自身已经是标准的json数据格式，拥有了层级关系。
 * 		而我们从数据表中查到的Menu是简单json，并没有层级关系，所以在	TreeNode 中就需要id和pid来构成层级关系。
 *
 * 		TreeNode ：该类的作用就是构造如navs.json中json数据的格式，将返回的List<Menu>普通json格式转换成我们需要的标准json格式
 * @param   null
 * @return 
 */
public class TreeNode {

	private Integer id;
	@JsonProperty("parentId")
	private Integer pid;

	private String title;
	private String icon;
	private String href;
	private Boolean spread;
	private String target;

	private List<TreeNode> children = new ArrayList<>();

	// 复选树的必要属性
	private String checkArr="0"; // 选中就是1

	/**
	 * 首页左边导航树的构造器
	 * 
	 * @param id
	 * @param pid
	 * @param title
	 * @param icon
	 * @param href
	 * @param spread
	 * @param target
	 */
	public TreeNode(Integer id, Integer pid, String title, String icon, String href, Boolean spread, String target) {
		super();
		this.id = id;
		this.pid = pid;
		this.title = title;
		this.icon = icon;
		this.href = href;
		this.spread = spread;
		this.target = target;
	}

	/**
	 * dtree的复选树使用
	 * 
	 * @param id
	 * @param pid
	 * @param title
	 * @param spread
	 * @param checkArr
	 */
	public TreeNode(Integer id, Integer pid, String title, Boolean spread, String checkArr) {
		super();
		this.id = id;
		this.pid = pid;
		this.title = title;
		this.spread = spread;
		this.checkArr = checkArr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Boolean getSpread() {
		return spread;
	}

	public void setSpread(Boolean spread) {
		this.spread = spread;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getCheckArr() {
		return checkArr;
	}

	public void setCheckArr(String checkArr) {
		this.checkArr = checkArr;
	}
}
