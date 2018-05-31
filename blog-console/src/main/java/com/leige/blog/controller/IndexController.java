package com.leige.blog.controller;


import com.leige.blog.common.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController extends BaseController {



    /**
      * @Title:
      * @Description: 跳转到主页面
      * @param
      * @return
      * @author 张亚磊
      * @date 2018年01月31日 14:38:17
      */
    @GetMapping(value = {"/","/index"})
    public String  index() {
        return "index";
    }

}
