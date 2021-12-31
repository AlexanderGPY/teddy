package com.dbay.teddy.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @description:
 * @author:AlexanderGuo
 * @date:2021/12/31 14:55:20
 **/

@Component
public class WebHookSender {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CloseableHttpClient httpclient = HttpClients.createDefault();
    public WebHookSender(){}
    
    public void wxRobotSend(String webHook, String subject, String text) {
        try {
            
            // create message
            JSONObject outer = new JSONObject();
            JSONObject inner = new JSONObject();
            String content = subject + '\n' + text;
            inner.put("content", content);
            outer.put("msgtype", "text");
            outer.put("text", inner);
            String[] webHookArr = webHook.split(";");
            
            
            for (String url : webHookArr) {
                // create httpClient
                HttpPost httpPost = new HttpPost(url);
                httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
                httpPost.setEntity(new StringEntity(outer.toJSONString(),"utf-8"));
                CloseableHttpResponse resp = null;
                try {
                    resp = httpclient.execute(httpPost);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(resp!=null){
                        try {
                            resp.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                logger.info("webhook success：-->"+url+"["+subject+"]"+text);
            }

        }catch(Exception e){
            logger.error("webhook error，"+e.getMessage());
        }
    }
}
