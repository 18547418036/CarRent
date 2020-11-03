package com.lyn.bus.controller;


import com.lyn.bus.domain.Customer;
import com.lyn.bus.service.CustomerService;
import com.lyn.bus.service.RentService;
import com.lyn.bus.vo.RentVo;
import com.lyn.sys.constast.SysConstast;
import com.lyn.sys.domain.User;
import com.lyn.sys.utils.DataGridView;
import com.lyn.sys.utils.RandomUtils;
import com.lyn.sys.utils.ResultObj;
import com.lyn.sys.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 汽车出租管理的控制器
 * @author LJH
 *
 */
@RestController
@RequestMapping("rent")
public class RentController {

	@Autowired
	private RentService rentService;
	
	@Autowired
	private CustomerService customerService;
	
	
	/**
	 * 检查身份证号是否存在
	 */
	@RequestMapping("checkCustomerExist")
	public ResultObj checkCustomerExist(RentVo rentVo) {
		Customer customer=customerService.queryCustomerByIdentity(rentVo.getIdentity());
		if(null!=customer) {
			return ResultObj.STATUS_TRUE;
		}else {
			return ResultObj.STATUS_FALSE;
		}
	}
	
	/**
	 * 初始化添加出租单的表单数据
	 */
	@RequestMapping("initRentFrom")
	public RentVo initRentFrom(RentVo rentVo) {
		//生成出租单号
		rentVo.setRentid(RandomUtils.createRandomStringUseTime(SysConstast.CAR_ORDER_CZ));
		//设置起租时间
		rentVo.setBegindate(new Date());
		
		User user=(User) WebUtils.getHttpSession().getAttribute("user");
		//设置操作员
		rentVo.setOpername(user.getRealname());
		return rentVo;
	}
	
	/**
	 * 保存出租单信息
	 */
	@RequestMapping("saveRent")
	public ResultObj saveRent(RentVo rentVo) {
		try {
			//设置创建时间
			rentVo.setCreatetime(new Date());
			//设置归还状态
			rentVo.setRentflag(SysConstast.RENT_BACK_FALSE);
			
			//保存
			this.rentService.addRent(rentVo);
			
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	
	/***************出租单管理*****************/
	
	/**
	 * 查询
	 */
	@RequestMapping("loadAllRent")
	public DataGridView loadAllRent(RentVo rentVo) {
		return this.rentService.queryAllRent(rentVo);
	}
	
	
	/**
	 * 修改出租单信息
	 */
	@RequestMapping("updateRent")
	public ResultObj updateRent(RentVo rentVo) {
		try {
			//保存
			this.rentService.updateRent(rentVo);
			
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}
	

	/**
	 * 删除租单信息
	 */
	@RequestMapping("deleteRent")
	public ResultObj deleteRent(RentVo rentVo) {
		try {
			this.rentService.deleteRent(rentVo.getRentid());
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
	
	
	
	
}
