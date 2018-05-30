package com.leige.blog.controller;

import com.leige.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张亚磊
 * @Description:
 * @date 2018/5/25  10:03
 */
@RestController
@RequestMapping(value="/api")
public class ApiController {
    
    @Autowired
    private SysUserService  sysUserService;


}
