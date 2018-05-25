package com.leige.blog.controller;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * error控制器，覆盖默认springboot的error控制器
 */
@Controller
public class MyblogErrorController extends BasicErrorController {
    public MyblogErrorController(){
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    private static final String PATH = "/error";

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = new HashMap();
        HttpStatus status = getStatus(request);
        body.put("state",false);
        if(404==status.value()){
            body.put("code",status.value());
            body.put("msg",status.getReasonPhrase());
        }else{
            Integer code=(Integer)request.getAttribute("code");
            String msg=(String)request.getAttribute("msg");
            body.put("code",code);
            body.put("msg",msg);
        }
        return new ResponseEntity<Map<String, Object>>(body, status);
    }

    @RequestMapping(
            produces = {"text/html"}
    )
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = this.getStatus(request);
        ModelAndView result=new ModelAndView("common/error");
        if(404==status.value()){
            result.addObject("code",status.value());
            result.addObject("msg",status.getReasonPhrase());
        }else {
            Integer code=(Integer)request.getAttribute("code");
            String msg=(String)request.getAttribute("msg");
            result.addObject("code",code);
            result.addObject("msg",msg);
        }
        result.addObject("state",false);
        return result;
    }
    @Override
    public String getErrorPath() {
        return PATH;
    }
}
