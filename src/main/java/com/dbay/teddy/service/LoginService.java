package com.dbay.teddy.service;

import com.dbay.teddy.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * @author AlexanderGuo
 */


@Service
public class LoginService {
    @Autowired
    private TokenManager tokenManager;
    private static final String USERNAME = "welove";
    private static final String PASSWORD = "welove520";
    private static final String TOKEN = "it is just a token";


    public boolean checkPassword(String userName, String password) {

        return USERNAME.equals(userName) && PASSWORD.equals(password);
    }

    public boolean checkToken(String token) {
        return TOKEN.equals(token);
    }

    public String createToken() {
        return TokenManager.createToken();
    }

    public String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        String cookie = request.getHeader("cookie");
        if (cookieName == null) {
            return null;
        }
        if (cookie == null) {
            return null;
        }
        String retValue = null;
        String[] cookKeys = cookie.split(";");

        try {
            for (String cookKey : cookKeys) {
                String[] rawCookieNameAndValuePair = cookKey.split("=");
                if (rawCookieNameAndValuePair[0].trim().equals(cookieName)) {
                    if (isDecoder) {
                        retValue = URLDecoder.decode(rawCookieNameAndValuePair[1].trim(), "UTF-8");
                    } else {
                        retValue = rawCookieNameAndValuePair[1].trim();
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }


}
