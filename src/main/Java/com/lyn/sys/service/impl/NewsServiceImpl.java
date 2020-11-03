package com.lyn.sys.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyn.sys.domain.News;
import com.lyn.sys.mapper.NewsMapper;
import com.lyn.sys.service.NewsService;
import com.lyn.sys.utils.DataGridView;
import com.lyn.sys.vo.NewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
	
	@Autowired
	private NewsMapper newsMapper;

	@Override
	public DataGridView queryAllNews(NewsVo newsVo) {
		Page<Object> page= PageHelper.startPage(newsVo.getPage(), newsVo.getLimit());
		List<News> data = this.newsMapper.queryAllNews(newsVo);
		return new DataGridView(page.getTotal(), data);
	}

	@Override
	public void addNews(NewsVo newsVo) {
		this.newsMapper.insertSelective(newsVo);
	}

	@Override
	public void deleteNews(Integer newsid) {
		this.newsMapper.deleteByPrimaryKey(newsid);
	}

	@Override
	public void deleteBatchNews(Integer[] ids) {
		for (Integer integer : ids) {
			this.deleteNews(integer);
		}
	}

	@Override
	public void updateNews(NewsVo newsVo) {
		this.newsMapper.updateByPrimaryKeySelective(newsVo);
	}

	@Override
	public News queryNewsById(Integer id) {
		return this.newsMapper.selectByPrimaryKey(id);
	}

}
