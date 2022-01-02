package com.dbay.teddy.controller;


import com.dbay.teddy.entity.Job;
import com.dbay.teddy.service.LoginService;
import com.dbay.teddy.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author AlexanderGuo
 */
@RestController
@RequestMapping("teddy")
public class LoginController {

    @Autowired
    LoginService loginService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String TOKEN_KEY = "token";

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Response login(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        logger.info("收到登录请求：" + req.getParameter("userName"));
        System.out.println("qqqqqqqqqqqqqqqqqqqqq");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String token = "";
        if (loginService.checkPassword(userName, password)) {
            token = loginService.createToken();
            Cookie tokenCookie = new Cookie(TOKEN_KEY, URLEncoder.encode(token, "utf-8"));
            tokenCookie.setPath("/");
            tokenCookie.setMaxAge(50);
            //set cookie
            resp.addCookie(tokenCookie);

            logger.info("登录成功：" + userName);
            return Response.SUCCESS(token);
        }
        logger.error("登录失败：" + userName);
        return Response.ERROR("401");
    }

    @RequestMapping(value = "checkToken", method = RequestMethod.POST)
    public Response checkToken(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        logger.info("收到登录请求：检查token");
        System.out.println("xxxxxxxxxxxxxxxxxxx");
        String token = "";
//        Cookie[] cookies = req.getCookies();
//        if (cookies != null && cookies.length > 0) {
//            for (Cookie cookie : cookies) {
//                System.out.println(cookie.getName());
//                //取出token
//                if (TOKEN_KEY.equals(cookie.getName())) {
//                    token = URLDecoder.decode(cookie.getValue(), "utf-8");
//                }
//            }
//        }

        //通过header拿到cookie
        token = loginService.getCookieValue(req, "token",true);

        //检查token
        if (loginService.checkToken(token)) {
            logger.info("登录成功：" + token);
            return Response.SUCCESS("success");
        }

        logger.error("登录失败：" + token);
        return Response.ERROR("401");
    }
}
