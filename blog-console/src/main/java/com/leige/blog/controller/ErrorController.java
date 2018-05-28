package com.leige.blog.controller;


import com.leige.blog.common.enums.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping(value = "/404")
    public String error404(Model model) {
        /*List<Navbar> navbars=navbarService.selectAll();
        model.addAttribute("navbars",navbars);*/
        model.addAttribute("code", ResultEnum.ERROR_404_HTML.getCode());
        model.addAttribute("msg", ResultEnum.ERROR_404_HTML.getMsg());
        return "common/error";
    }

    @GetMapping(value = "/500")
    public String error500(Model model) {
       /* List<Navbar> navbars=navbarService.selectAll();
        model.addAttribute("navbars",navbars);*/
        model.addAttribute("code", ResultEnum.ERROR_500.getCode());
        model.addAttribute("msg", ResultEnum.ERROR_500.getMsg());
        return "common/error";
    }
}
