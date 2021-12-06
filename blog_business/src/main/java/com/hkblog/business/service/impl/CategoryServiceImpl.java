package com.hkblog.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.domain.entity.Category;
import com.hkblog.business.service.CategoryService;
import com.hkblog.business.mapper.CategoryMapper;
import com.hkblog.domain.vo.CategoryVo;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private IdWorker idWorker;

    /**
     * @methodName : 根据文章id 查询全部的分类标签
     * @author : HK意境
     * @date : 2021/11/27 20:44
     * @description :
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Trace
    @Override
    public List<Category> findCategoryListByPostId(String postId) {

        List<Category> categoryList = categoryMapper.selectListByPostId(postId);

        return categoryList;
    }



    /**
     * @methodName : 查找全部分类信息 Vo 对象
     * @author : HK意境
     * @date : 2021/11/28 20:56
     * @description :
     * @Todo : 转换Vo 对象
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Trace
    @Override
    public List<CategoryVo> findAllCategoryVo() {

        List<Category> categories = categoryMapper.selectList(null);
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categories) {
            categoryVoList.add(new CategoryVo(category));
        }

        return categoryVoList;
    }


    /**
     * @methodName : 保存 category 分类信息，通过 vo 对象
     * @author : HK意境
     * @date : 2021/11/29 9:30
     * @description :
     * @Todo : vo 对象转换为 category 对象进行保存
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Trace
    @Override
    public Boolean saveCategoryVo(CategoryVo categoryVo) {

        Category category = new Category();


        return true;
    }
}




