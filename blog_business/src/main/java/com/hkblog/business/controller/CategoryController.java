package com.hkblog.business.controller;

import com.hkblog.business.service.impl.CategoryServiceImpl;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.domain.entity.Category;
import com.hkblog.domain.vo.CategoryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : CategoryController
 * @date : 2021/11/26 0:05
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private IdWorker idWorker ;

    /**
     * 保存 Category 分类信息
     *      注意开启事务
     *
     */
    @Transactional
    @PostMapping()
    public ResponseResult save(@RequestBody Category category){

        // 设置分类 id
        category.setId(idWorker.nextId()+"");
        category.setCreateTime(new Date());
        category.setUpdateTime(category.getCreateTime());
        categoryService.save(category);

        return ResponseResult.SUCCESS().setData(category);
    }


    /**
     * 通过 Id 查询文章分类信息
     *
     *
     *
     */
    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable(name = "id")String id){

        Category byId = categoryService.getById(id);
        return new ResponseResult(ResultCode.SUCCESS,byId);
    }


    /**
     * 通过 Id 删除分类信息
     *      注意事务：，逻辑删除
     */
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategoryById(@PathVariable(name = "id")String id){

        boolean res = categoryService.removeById(id);

        return new ResponseResult(ResultCode.SUCCESS,res);
    }


    /**
     *  更新或保存文章分类信息
     *
     *
     *
     */
    @PutMapping()
    public ResponseResult updateCategory(@RequestBody Category category){

        category.setUpdateTime(new Date());
        boolean res = categoryService.saveOrUpdate(category);
        return new ResponseResult(ResultCode.SUCCESS, res);
    }


    /**
     * @methodName : 获取全部分类信息, 前台业务展示
     * @author : HK意境
     * @date : 2021/11/28 20:40
     * @description :
     * @Todo : 需要显示返回Vo 对象
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @GetMapping("/all/vo")
    public ResponseResult getAllCategoryVo(){

        List<CategoryVo> categoryVos = categoryService.findAllCategoryVo();

        return new ResponseResult(ResultCode.SUCCESS ,categoryVos);
    }






    /**
     * @methodName : 查询所有分类信息，后台管理系统
     * @author : HK意境
     * @date : 2021/11/28 20:43
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
    @GetMapping("/all")
    public ResponseResult findAll(){

        List<Category> categories = categoryService.list();
        return new ResponseResult(ResultCode.SUCCESS ,categories);
    }



    /**
     * @methodName : 查询文章分类详细信息
     * @author : HK意境
     * @date : 2021/11/29 16:54
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
    @GetMapping("/all/detail")
    public ResponseResult getAllCategoryDetail(){

        List<CategoryVo> categoryVos = categoryService.findAllCategoryVo();

        return new ResponseResult(ResultCode.SUCCESS ,categoryVos);
    }



    /**
     * @methodName : 通过分类id, 查询详细信息
     * @author : HK意境
     * @date : 2021/11/29 20:07
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
    @GetMapping("/detail/{id}")
    public ResponseResult getCategoryDetailById(@PathVariable(name = "id") String categoryId){


        CategoryVo categoryVo = new CategoryVo(categoryService.getById(categoryId));

        return new ResponseResult(ResultCode.SUCCESS, categoryVo);
    }


}
