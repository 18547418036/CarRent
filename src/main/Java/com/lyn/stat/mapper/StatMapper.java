package com.lyn.stat.mapper;

import com.lyn.stat.domain.BaseEntity;

import java.util.List;

public interface StatMapper {

    /*查询客户地区的数据*/
    List<BaseEntity> queryCustomerStat();

    /*查询业务员年度数据*/
    List<BaseEntity> queryOpernameYearGradeStat(String year);

    /*查询公司年度数据*/
    List<Double> queryCompanyYearGradeStat(String year);
}
