package com.backend;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class RequestUtils {

    public Response sendGetWithPathParams(String url, Map<String, ?> headers, Map<String, ?> params){
        return RestAssured.given().headers(headers).log().all().pathParams(params).get(url);
    }

    public Response sendGet(String url, Map<String, ?> headers){
        return RestAssured.given().headers(headers).log().all().get(url);
    }
}
