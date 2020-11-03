package com.lyn.bus.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyn.bus.domain.Car;
import com.lyn.bus.domain.Rent;
import com.lyn.bus.mapper.CarMapper;
import com.lyn.bus.mapper.RentMapper;
import com.lyn.bus.service.RentService;
import com.lyn.bus.vo.RentVo;
import com.lyn.sys.constast.SysConstast;
import com.lyn.sys.utils.DataGridView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentServiceImpl implements RentService {
	
	@Autowired
	private RentMapper rentMapper;
	
	@Autowired
	private CarMapper carMapper;

	@Override
	public void addRent(RentVo rentVo) {
		this.rentMapper.insertSelective(rentVo);
		//更改车辆的出租状态
		Car car=new Car();
		car.setCarnumber(rentVo.getCarnumber());
		car.setIsrenting(SysConstast.RENT_CAR_TRUE);
		carMapper.updateByPrimaryKeySelective(car);
	}

	@Override
	public DataGridView queryAllRent(RentVo rentVo) {
		Page<Object> page= PageHelper.startPage(rentVo.getPage(), rentVo.getLimit());
		List<Rent> data = this.rentMapper.queryAllRent(rentVo);
		return new DataGridView(page.getTotal(), data);
	}

	@Override
	public void updateRent(RentVo rentVo) {
		this.rentMapper.updateByPrimaryKeySelective(rentVo);
	}

	@Override
	public void deleteRent(String rentid) {
		//更改汽车的状态
		Rent rent=this.rentMapper.selectByPrimaryKey(rentid);
		Car car=new Car();
		car.setCarnumber(rent.getCarnumber());
		car.setIsrenting(SysConstast.RENT_CAR_FALSE);
		carMapper.updateByPrimaryKeySelective(car);
		//删除出租单
		this.rentMapper.deleteByPrimaryKey(rentid);
	}

	@Override
	public Rent queryRentByRentId(String rentid) {
		return this.rentMapper.selectByPrimaryKey(rentid);
	}

}
