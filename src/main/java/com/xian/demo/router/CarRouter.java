package com.xian.demo.router;

import com.xian.demo.entity.Result;
import com.xian.demo.entity.Page;
import com.xian.demo.entity.User;
import com.xian.demo.service.CarService;
import com.xian.demo.util.Common;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("car/")
public class CarRouter {

    @Autowired
    private CarService carService;

    @RequestMapping(value = "addItemToCar", method = RequestMethod.POST)
    public Result addItemToCar(@Param("pid") Integer pid,
                               HttpServletRequest httpServletRequest){

        User user = (User) httpServletRequest.getAttribute(Common.getReqUserKey());
        System.out.println(user.toString());
        System.out.println(pid);
        Integer tempFlag = carService.addItemToCar(user.getId(), pid);
        if(tempFlag == 1){
            return Result.ok("success",null);
        } else {
            return Result.errorMsg("添加失败");
        }
    }

    @RequestMapping(value = "getCarList", method = RequestMethod.POST)
    public Result getCarList(HttpServletRequest httpServletRequest,
                             @RequestParam(value = "pageShowNumber", defaultValue = "10") Integer pageShowNumber,
                             @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage){
        pageShowNumber = Common.checkParam(pageShowNumber, 10);
        currentPage = Common.checkParam(currentPage, 1);

        User user = (User) httpServletRequest.getAttribute(Common.getReqUserKey());
        Page page = carService.getCarList(user.getId(), pageShowNumber, currentPage);

        if (null == page){
            return Result.ok("服务器异常");
        }else{
            return Result.ok(page);
        }
    }

    @RequestMapping(value = "removeItem", method = RequestMethod.POST)
    public Result removeItem(HttpServletRequest httpServletRequest, @Param("pid") Integer pid){

        User user = (User) httpServletRequest.getAttribute(Common.getReqUserKey());
        Integer tempFlag = carService.removeItem(user.getId(), pid);
        if(tempFlag == 1 ){
            return Result.ok("删除成功");
        }else{
            return Result.errorMsg("删除失败");
        }
    }
}
