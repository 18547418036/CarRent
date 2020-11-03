package com.lyn.sys.controller;


import com.lyn.sys.service.LogInfoService;
import com.lyn.sys.utils.DataGridView;
import com.lyn.sys.utils.ResultObj;
import com.lyn.sys.vo.LogInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志管理控制器
 * 
 * @author LJH
 *
 */
@RestController
@RequestMapping("logInfo")
public class LogInfoController {

	@Autowired
	private LogInfoService logInfoService;

	/**
	 * 加载日志列表返回DataGridView
	 */
	@RequestMapping("loadAllLogInfo")
	public DataGridView loadAllLogInfo(LogInfoVo logInfoVo) {
		return this.logInfoService.queryAllLogInfo(logInfoVo);
	}

	/**
	 * 删除日志
	 */
	@RequestMapping("deleteLogInfo")
	public ResultObj deleteLogInfo(LogInfoVo logInfoVo) {
		try {
			this.logInfoService.deleteLogInfo(logInfoVo.getId());
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}

	/**
	 * 批量删除日志
	 */
	@RequestMapping("deleteBatchLogInfo")
	public ResultObj deleteBatchLogInfo(LogInfoVo logInfoVo) {
		try {
			this.logInfoService.deleteBatchLogInfo(logInfoVo.getIds());
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}

}
