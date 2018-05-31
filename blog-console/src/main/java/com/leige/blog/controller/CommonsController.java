package com.leige.blog.controller;

import com.baidu.ueditor.ActionEnter;
import com.leige.blog.common.base.controller.BaseController;
import com.leige.blog.common.captcha.MyblogCaptcha;
import com.leige.blog.common.utils.result.Tree;
import com.leige.blog.service.RSAService;
import com.leige.blog.service.SysResourceService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用的控制器
 * @author L.cm
 *
 */
@Controller
@RequestMapping("common")
public class CommonsController extends BaseController {
    @Autowired
    private MyblogCaptcha myblogCaptcha;
    @Autowired
    private RSAService rsaService;
    @Autowired
    private SysResourceService sysPermissionService;
    
    /**
     * 图形验证码
     */
    @GetMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        myblogCaptcha.generate(request, response);
    }
    /**
     * 获取公钥
     */
    @GetMapping("getkey")
    public @ResponseBody
    Map<String, String> publicKey(HttpServletRequest request) {
        RSAPublicKey publicKey = rsaService.generateKey(request);
        Map<String, String> data = new HashMap<String, String>();
        data.put("modulus", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        data.put("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
        return data;
    }



    @RequestMapping(value = { "getMenu" }, method = { RequestMethod.POST}, produces="application/json;charset=UTF-8")
    @ResponseBody
    //@RequiresAuthentication
    public List<Tree> getMenu(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<Tree> trees= sysPermissionService.selectTree(this.getUserId());
        return trees;
    }


    @RequestMapping(value = { "allTree" }, method = { RequestMethod.POST }, produces="application/json;charset=UTF-8")
    @ResponseBody
    public List<Tree> getAllTree(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<Tree> trees= sysPermissionService.selectAllMenu();
        return trees;
    }


    @RequestMapping("icons")
    public String  index() {
        return "common/icons";
    }

    @RequestMapping("ueditor")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
