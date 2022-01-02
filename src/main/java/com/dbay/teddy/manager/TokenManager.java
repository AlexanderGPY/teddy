package com.dbay.teddy.manager;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author AlexanderGuo
 */


@Service
public class TokenManager {

    public static String getToken(){
        //TODO  get token from db
        return "it is just a token";
    }

    public static void saveToken(String token){
        //TODO token save to db

    }

    public static String createToken(){
        //TODO create token
        return "it is just a token";
    }

}
