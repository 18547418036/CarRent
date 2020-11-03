package com.lyn.sys.menu;

import com.lyn.sys.constast.SysConstast;
import com.lyn.sys.domain.Menu;
import com.lyn.sys.domain.News;
import com.lyn.sys.mapper.MenuMapper;
import com.lyn.sys.vo.MenuVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

/**
 * @author iamhere
 * @description 测试
 * ctrl alt v
 * @date 2020/9/9
 */

public class MenuTest {

    @Autowired
    private MenuVo menuVo;

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void test01(){

        List<Menu> list = menuMapper.queryAllMenu(menuVo);

        Iterator<Menu> iter = list.iterator();
        while (iter.hasNext()) {
            Menu menu = (Menu) iter.next();
            System.out.println(menu.getId()+"  "+menu.getTitle()+"  ");
        }
    }
}
