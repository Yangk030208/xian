package com.xian.demo.router;

import com.xian.demo.entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "page/")
public class Page {
    @RequestMapping(value = "getMeanuList",method = RequestMethod.GET)
    public Result getMeanuList(){
        return Result.ok();
    }
}