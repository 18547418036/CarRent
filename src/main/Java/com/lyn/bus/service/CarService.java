package com.lyn.bus.service;


import com.lyn.bus.domain.Car;
import com.lyn.bus.vo.CarVo;
import com.lyn.sys.utils.DataGridView;

/**
 * 车辆管理的服务接口
 * @author LJH
 *
 */
public interface CarService {
	/**
	 * 查询所有车辆
	 * @param carVo
	 * @return
	 */
	public DataGridView queryAllCar(CarVo carVo);
	/**
	 * 添加车辆
	 * @param carVo
	 */
	public void addCar(CarVo carVo);
	/**
	 * 修改车辆
	 * @param carVo
	 */
	public void updateCar(CarVo carVo);
	/**
	 * 根据id删除车辆
	 * @param carid
	 */
	public void deleteCar(String carnumber);
	/**
	 * 批量删除车辆
	 * @param carVo
	 */
	public void deleteBatchCar(String[] carnumbers);
	
	/**
	 * 根据车牌号查询
	 * @param carnumber
	 * @return
	 */
	public Car queryCarByCarNumber(String carnumber);

}
