package com.backend;

import config.ConfigFactory;

import java.util.HashMap;
import java.util.Map;


public class Utils {

    String base_url;

    public Map<String, ?> getRequestHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");
        return headers;
    }

    public Map<String, ?> getPathParam(int id){
        Map<String, String> headers = new HashMap<>();
        headers.put("id", String.valueOf(id));
        return headers;
    }

    public String getApiPath(String path){
        base_url = ConfigFactory.getConfig().URL();
        return base_url + path;
    }


}
