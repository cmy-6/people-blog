package com.qiang.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiang.commons.PagedResult;
import com.qiang.commons.StringAndArray;
import com.qiang.mapper.ArticleMapper;
import com.qiang.pojo.VO.BlogMessageVO;
import com.qiang.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.service.impl
 * @Description: 首页操作业务逻辑层
 * @Date: 2019/7/8 0008 15:47
 **/
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult findAllBlog(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<BlogMessageVO> blog = articleMapper.findBlog();
        for (BlogMessageVO b:
             blog) {
            b.setTagValue(StringAndArray.stringToArray(b.getLabelValues()));
            b.setArticleUrl("/article/" + b.getId());
        }
        PageInfo<BlogMessageVO> pageList = new PageInfo<>(blog);
        PagedResult grid = new PagedResult();
        grid.setPage(page);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        grid.setRows(blog);
        return grid;
    }
}