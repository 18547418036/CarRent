package com.lyn.bus.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyn.bus.domain.Car;
import com.lyn.bus.domain.Check;
import com.lyn.bus.domain.Customer;
import com.lyn.bus.domain.Rent;
import com.lyn.bus.mapper.CarMapper;
import com.lyn.bus.mapper.CheckMapper;
import com.lyn.bus.mapper.CustomerMapper;
import com.lyn.bus.mapper.RentMapper;
import com.lyn.bus.service.CheckService;
import com.lyn.bus.vo.CheckVo;
import com.lyn.sys.constast.SysConstast;
import com.lyn.sys.domain.User;
import com.lyn.sys.utils.DataGridView;
import com.lyn.sys.utils.RandomUtils;
import com.lyn.sys.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckServiceImpl implements CheckService {

	@Autowired
	private CheckMapper checkMapper;
	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private RentMapper rentMapper;
	@Autowired
	private CarMapper carMapper;
	@Override
	public Map<String, Object> initCheckFormData(String rentid) {
		
		//查询出租单
		Rent rent=this.rentMapper.selectByPrimaryKey(rentid);
		//查询客户
		Customer customer=this.customerMapper.selectByPrimaryKey(rent.getIdentity());
		//查询车辆
		Car car=this.carMapper.selectByPrimaryKey(rent.getCarnumber());
		//组装Check
		Check check=new Check();
		check.setCheckid(RandomUtils.createRandomStringUseTime(SysConstast.CAR_ORDER_JC));
		check.setRentid(rentid);
		check.setCheckdate(new Date());
		User user=(User) WebUtils.getHttpSession().getAttribute("user");
		check.setOpername(user.getRealname());
		
		Map<String, Object> map=new HashMap<String, Object>();
		
		map.put("rent", rent);
		map.put("customer", customer);
		map.put("car", car);
		map.put("check", check);
		
		return map;
	}
	@Override
	public void addCheck(CheckVo checkVo) {
		this.checkMapper.insertSelective(checkVo);
		//更改出租单的状态
		Rent rent=this.rentMapper.selectByPrimaryKey(checkVo.getRentid());
		rent.setRentflag(SysConstast.RENT_BACK_TRUE);
		this.rentMapper.updateByPrimaryKeySelective(rent);
		//更改汽车的状态
		Car car=new Car();
		car.setCarnumber(rent.getCarnumber());
		car.setIsrenting(SysConstast.RENT_CAR_FALSE);
		this.carMapper.updateByPrimaryKeySelective(car);
	}
	@Override
	public DataGridView queryAllCheck(CheckVo checkVo) {
		Page<Object> page= PageHelper.startPage(checkVo.getPage(), checkVo.getLimit());
		List<Check> data = this.checkMapper.queryAllCheck(checkVo);
		return new DataGridView(page.getTotal(), data);
	}
	@Override
	public void updateCheck(CheckVo checkVo) {
		this.checkMapper.updateByPrimaryKeySelective(checkVo);
	}

}
