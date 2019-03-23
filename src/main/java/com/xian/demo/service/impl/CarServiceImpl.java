package com.xian.demo.service.impl;

import com.xian.demo.dao.CarMapper;
import com.xian.demo.dao.ProductMapper;
import com.xian.demo.entity.Car;
import com.xian.demo.entity.CarDetial;
import com.xian.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarMapper carMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Integer addItemToCar(@Valid Car car) {
        // 在插入之前应该有两个步骤
        // 1. pid 是否有效
        // 2. 商品是否已经在购物车
        Integer isExist = carMapper.checkPidIsExist(car.getUid(), car.getPid());
        if(isExist>=1){ // 已经存在于购物车的情况
            System.out.println("已经在购物车的情况");
            return 0;
        }
        Integer validatedPid = productMapper.validatedPid(car.getPid());
        if(validatedPid != 1){ // 商品表中不存在的情况
            System.out.println("不存在");
            return 0;
        }
        return carMapper.addItemToCar(car.getUid(), car.getPid());
    }

    @Override
    public List<CarDetial> getCarList(Integer uid) {

        return carMapper.getCarList(uid);
    }

    @Override
    public Integer removeItem(@NotNull(message = "用户ID不能为空") Integer uid,
                              @NotNull(message = "商品ID不能为空") Integer pid) {
        return carMapper.removeItem(uid, pid);
    }
}