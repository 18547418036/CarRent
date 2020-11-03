package com.lyn.sys.mapper;


import com.lyn.sys.domain.News;

import java.util.List;

public interface NewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);
    
    /**
     * 查询
     */
    List<News> queryAllNews(News news);
}