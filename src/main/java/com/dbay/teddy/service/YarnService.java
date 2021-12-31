package com.dbay.teddy.service;

import com.alibaba.fastjson.JSON;
import com.dbay.teddy.utils.TeddyConf;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author AlexanderGuo
 */
@Service
public class YarnService {

    private final CloseableHttpClient httpclient = HttpClients.createDefault();

    /**
     * https://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/ResourceManagerRest.html#Cluster_Application_State_API
     * @return
     */
    public String state(String appId){
        for(String host : TeddyConf.get("yarn.cluster").split(",")){
            HttpGet httpGet = new HttpGet("http://"+host+"/ws/v1/cluster/apps/"+ appId + "/state");
            httpGet.setHeader("Content-Type", "application/json");
            CloseableHttpResponse resp = null;
            try {
                resp = httpclient.execute(httpGet);
                if (resp.getStatusLine().getStatusCode() == 200) {
                    String content = IOUtils.toString(resp.getEntity().getContent());
                    return JSON.parseObject(content).getString("state");
                }
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
        }
        return "NONE";
    }
}
