package com.dbay.teddy.utils;

import java.util.Properties;

/**
 * @author AlexanderGuo
 */
public class TeddyConf {
    private Properties properties;

    private static final TeddyConf CONF = new TeddyConf();

    private TeddyConf() {}

    public static TeddyConf getInstance() {
        return TeddyConf.CONF;
    }

    public static void setProperties(Properties properties){
        if(TeddyConf.getInstance().properties != null){
            return ;
        }
        TeddyConf.getInstance().properties = properties;
    }

    public static String get(String key){
        return TeddyConf.getInstance().properties.getProperty(key);
    }

    public static String get(String key, String defaultV){
        String v = TeddyConf.getInstance().properties.getProperty(key);
        return v == null? defaultV : v;
    }
}
