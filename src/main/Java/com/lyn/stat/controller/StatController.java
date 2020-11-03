package com.lyn.stat.controller;

import com.lyn.stat.domain.BaseEntity;
import com.lyn.stat.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author iamhere
 * @description 统计分析
 * @date 2020/8/18
 */
@RequestMapping("stat")
@Controller // 该类一般响应的都是json格式的数据
public class StatController {

    @Autowired
    private StatService statService;


    // 这里也可以使用路由的方式

    /**跳转到客户地区统计页面*/
    @RequestMapping("toCustomerAreaStat")
    public String toCustomerAreaStat(){
        System.out.println("请求已到达");
        return "stat/customerAreaStat";
    }


    /**加载客户地区数据*/
    @RequestMapping("loadCustomerAreaStatJosn")
    @ResponseBody
    public List<BaseEntity> loadCustomerAreaStatJosn(){
        return this.statService.loadCustomerAreaStatList();
    }


    /**跳转到年度业务员统计页面*/
    @RequestMapping("toOpernameYearGradeStat")
    public String toOpernameYearGradeStat(){
        System.out.println("已到达业务员统计页面");
        return "stat/opernameYearGradeStat";
    }

    /**加载业务员年度统计数据*/
    @RequestMapping("loadOpernameYearGradeStatJson")
    @ResponseBody
    public Map<String,Object> loadOpernameYearGradeStatJson(String year){
        System.out.println("进行业务统计查询");
        List<BaseEntity> entities = this.statService.loadOpernameYearGradeStatList(year);
        Map<String,Object> map = new HashMap<String, Object>();
        List<String> names = new ArrayList<String>();
        List<Double> values = new ArrayList<Double>();
        for (BaseEntity baseEntity : entities){
            names.add(baseEntity.getName());
            values.add(baseEntity.getValue());
        }
        map.put("name",names);
        map.put("value",values);
        return map;
    }

    /**跳转到公司年度统计页面*/
    @RequestMapping("toCompanyYearGradeStat")
    public String toCompanyYearGradeStat(){
        return "stat/companyYearGradeStat";
    }

    /**加载公司年度统计数据*/
    @RequestMapping("loadCompanyYearGradeStatJson")
    @ResponseBody
    public List<Double> loadCompanyYearGradeStatJson(String year){
        List<Double> entities = this.statService.loadCompanyYearGradeStatList(year);
        // 不遍历的结果是折线图不显示连线
        for (int i = 0; i < entities.size(); i++) {
            if(null==entities.get(i)) {
                entities.set(i, 0.0);
            }
        }
        return entities;
    }

}
