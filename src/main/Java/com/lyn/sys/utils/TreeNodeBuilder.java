package com.lyn.sys.utils;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeBuilder {
	/**
	 * 把简单的集合转成有层级关系的集合【二级菜单】
	 *
	 * @param nodes
	 * @param topPid
	 * @return
	 */
	public static List<TreeNode> builder(List<TreeNode> nodes, Integer topPid) {

		// 存储具有层级关系的节点树       TreeNode类中已经有了id和pid，但是还没有构造层级关系【当前为二级菜单】
		List<TreeNode> treeNodes = new ArrayList<>();

		// 构造层级关系
		// 遍历nodes，如果该节点的pid == 1，表明该节点为一级菜单，将该节点放入treeNodes中
		for (TreeNode n1 : nodes) {
			// 根节点
			if (n1.getPid() == topPid) {
				treeNodes.add(n1);
			}
			// 子节点【再遍历，如果某个菜单的pid等于另一个菜单的id，那么就是父子节点】
			for (TreeNode n2 : nodes) {
				if (n2.getPid() == n1.getId()) {
					n1.getChildren().add(n2);
				}
			}
		}
		return treeNodes;
	}
}
