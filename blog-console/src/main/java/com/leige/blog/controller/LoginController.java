package com.leige.blog.controller;


import com.leige.blog.common.base.controller.BaseController;
import com.leige.blog.common.captcha.MyblogCaptcha;
import com.leige.blog.common.enums.ResultEnum;
import com.leige.blog.common.utils.IpHelper;
import com.leige.blog.common.utils.result.Result;
import com.leige.blog.common.utils.result.ResultUtil;
import com.leige.blog.handler.exception.GlobalException;
import com.leige.blog.service.AuthService;
import com.leige.blog.service.RSAService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private MyblogCaptcha myblogCaptcha;
    @Autowired
    private AuthService authService;
    @Autowired
    private RSAService rsaService;

    @Value("${jwt.header}")
    private String tokenHeader;
    /**
      * @Title:
      * @Description: 用户登录
      * @param
      * @return
      * @author 张亚磊
      * @date 2018年01月31日 14:38:17
      */
    @RequestMapping(value = { "/in" }, method = { RequestMethod.POST }, produces="application/json;charset=UTF-8")
    @ResponseBody
    public Result login(String captcha, String userName, String enPassword, @RequestParam(value = "rememberMe", defaultValue = "0") Integer rememberMe, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        if(StringUtils.isBlank(captcha)){
            throw new GlobalException(ResultEnum.CAPTCHA_NOT_BLANK);
        }
        if(StringUtils.isBlank(userName)){
            throw new GlobalException(ResultEnum.USERNAME_NOT_BLANK);
        }
        if(StringUtils.isBlank(enPassword)){
            throw new GlobalException(ResultEnum.PASSWORD_NOT_BLANK);
        }
       if(!myblogCaptcha.validate(request,captcha)) {
           throw new GlobalException(ResultEnum.CAPTCHA_ERROR);
       }
        String password = rsaService.decryptParameter(enPassword, request);
        String ip= IpHelper.getClientIpAddress(request);
        rsaService.removePrivateKey(request);
        try {
            authService.login(request,userName,password);
            return ResultUtil.success(ResultEnum.LOGIN_IN_SUCCESS,null);
        }catch (UsernameNotFoundException e) {
            throw new GlobalException(ResultEnum.USERNAME_NOT_BLANK);
        }catch (BadCredentialsException e) {
            throw new GlobalException(ResultEnum.PASSWORD_ERROR);
        } catch (Throwable e) {
            throw new GlobalException(ResultEnum.ERROR_500);
        }
    }

    /**
      * @Title:
      * @Description: 用户退出
      * @param
      * @return
      * @author 张亚磊
      * @date 2018年03月14日 15:04:26
      */
    @PostMapping("/out")
    @ResponseBody
    public Result logout() {
        return ResultUtil.success(ResultEnum.LOGIN_OUT_SUCCESS,null);
    }
}
