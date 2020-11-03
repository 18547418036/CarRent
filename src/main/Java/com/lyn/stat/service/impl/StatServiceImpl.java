package com.lyn.stat.service.impl;

import com.lyn.stat.domain.BaseEntity;
import com.lyn.stat.mapper.StatMapper;
import com.lyn.stat.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author iamhere
 * @description
 * @date 2020/8/18
 */
@Service
public class StatServiceImpl implements StatService {

    @Autowired
    private StatMapper statMapper;

    @Override
    public List<BaseEntity> loadCustomerAreaStatList() {
        return statMapper.queryCustomerStat();
    }

    @Override
    public List<BaseEntity> loadOpernameYearGradeStatList(String year) {
        return this.statMapper.queryOpernameYearGradeStat(year);
    }

    @Override
    public List<Double> loadCompanyYearGradeStatList(String year) {
        return this.statMapper.queryCompanyYearGradeStat(year);
    }
}
