package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /*
    根据父节点的id查询子节点
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategorysByPid(@RequestParam(value = "pid",defaultValue = "0")Long pid){
            if (pid == null || pid < 0){
                //return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();//参数不合法
                //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//参数不合法
                return ResponseEntity.badRequest().build();//参数不合法
            }
            List<Category> categories = this.categoryService.queryCategoriesByPid(pid);
            if (CollectionUtils.isEmpty(categories)){
                //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();//响应的参数为空，返回404
                return ResponseEntity.notFound().build();//响应的参数为空，返回404
            }
            return ResponseEntity.ok(categories);//响应200查询成功
    }


    /*
    商品分类名称查询
     */
    @GetMapping("names")
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){

        List<String> names = this.categoryService.queryNamesByIds(ids);
        if (CollectionUtils.isEmpty(names)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }
}
