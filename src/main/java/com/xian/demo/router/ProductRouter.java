package com.xian.demo.router;

import com.xian.demo.entity.Page;
import com.xian.demo.entity.Product;
import com.xian.demo.entity.Result;
import com.xian.demo.service.ProductService;
import com.xian.demo.util.Common;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "product/")
public class ProductRouter {

    @Autowired
    private ProductService productService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Cacheable(value="product_all", key = "'-'+ #p0+'-'+#p1")
    @RequestMapping(value = "findAll",method = RequestMethod.POST)
    public Result findAll(@RequestParam(value = "pageShowNumber", defaultValue = "10") Integer pageShowNumber,
                          @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage){

        pageShowNumber = Common.checkParam(pageShowNumber, 10);
        currentPage = Common.checkParam(currentPage, 1);

        Page page = productService.findAll(pageShowNumber, currentPage);
        if (null == page)  {
            return Result.errorMsg("暂无商品");
        }else{
            return Result.ok(page);
        }

    }

    @Cacheable(value="findByType", key = "'-'+ #p1+'-'+#p2")
    @RequestMapping(value = "findByType",method = RequestMethod.POST)
    public Result findProductByType(@Param(value = "type") Integer type,
                                    @RequestParam(value = "pageShowNumber", defaultValue = "10") Integer pageShowNumber,
                                    @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage){

        pageShowNumber = Common.checkParam(pageShowNumber, 10);
        currentPage = Common.checkParam(currentPage, 1);

        Page page  = productService.findProductByType(type, pageShowNumber, currentPage);
        if (null == page){ // 没有查到对应的商品
            return Result.errorMsg("没有对应的商品");
        }else{
            return Result.ok(page);
        }
    }
    //TODO 2019/4/8 10:47 AM  指定缓存的时效
    @Cacheable(value="findProductById", key = "'-'+ #p0")
    @RequestMapping(value = "findById/{id}",method = RequestMethod.GET)
    public Result findProductById(@PathVariable(value = "id") Integer id) {

        Product product = productService.findProductById(id);
        if (product == null) {
            return Result.ok("没对对应的商品ID",null);
        } else {
            return Result.ok(product);
        }
    }
}
