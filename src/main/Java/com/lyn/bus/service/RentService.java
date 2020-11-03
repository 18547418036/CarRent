package com.lyn.bus.service;


import com.lyn.bus.domain.Rent;
import com.lyn.bus.vo.RentVo;
import com.lyn.sys.utils.DataGridView;

public interface RentService {

	/**
	 * 保存出租单信息
	 * @param rentVo
	 */
	void addRent(RentVo rentVo);

	/**
	 * 查询
	 * @param rentVo
	 */
	DataGridView queryAllRent(RentVo rentVo);

	/**
	 * 修改出租单
	 * @param rentVo
	 */
	void updateRent(RentVo rentVo);

	/**
	 * 删除出租单
	 * @param rentid
	 */
	void deleteRent(String rentid);

	/**
	 *  根据出租单号查询出租单信息
	 * @param rentid
	 * @return
	 */
	Rent queryRentByRentId(String rentid);

}
