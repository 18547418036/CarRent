package com.lyn.stat.service;

import com.lyn.stat.domain.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * @author iamhere
 * @description     统计数据服务接口
 * @date 2020/8/18
 */
public interface StatService {

    /*查询客户地区的数据*/
    List<BaseEntity> loadCustomerAreaStatList();
    /*查询业务员年度数据*/
    List<BaseEntity> loadOpernameYearGradeStatList(String year);
    /*查询公司年度数据*/
    List<Double> loadCompanyYearGradeStatList(String year);
}
