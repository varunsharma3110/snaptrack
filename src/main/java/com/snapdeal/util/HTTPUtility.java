package com.snapdeal.util;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import static com.jayway.restassured.RestAssured.given;

public class HTTPUtility {

    private static final Logger LOG = Logger.getLogger("HTTPUtility");

    public JSONObject makeGetRequest(String url) throws Exception {

        LOG.info(new Throwable().getStackTrace()[1].toString());
        LOG.info("Request URL: " + url);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        LOG.info("Response : " + response.toString());

        return new JSONObject(response.toString());
    }

    public JSONObject makePostRequest(String path, String body) throws JSONException {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBody(body);
        LOG.info(new Throwable().getStackTrace()[1].toString());
        LOG.info("Request URL: " + path);
        LOG.info("Request body : " + body);
        builder.setContentType("application/json; charset=UTF-8");
        RequestSpecification requestSpec = builder.build();
        try {
            Response response = given().authentication().preemptive().basic("", "").spec(requestSpec).when().post(path);
            LOG.info("Response : " + response.body().asString());
            return new JSONObject(response.body().asString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public JSONObject makePostRequest(String path,String auth, String body) throws JSONException {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBody(body);
        LOG.info(new Throwable().getStackTrace()[1].toString());
        LOG.info("Request URL: " + path);
        LOG.info("Request auth: "+ auth);
        LOG.info("Request body : " + body);
        builder.setContentType("application/json; charset=UTF-8");
        RequestSpecification requestSpec = builder.build();
        try {
            Response response = given().header("X-SD-AD-TOKEN",auth).spec(requestSpec).when().post(path);
            LOG.info("Response : " + response.body().asString());
            return new JSONObject(response.body().asString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public JSONObject makeGetRequest(String url,String auth)throws Exception{
        URL obj = new URL(url);
        LOG.info(new Throwable().getStackTrace()[1].toString());
        LOG.info("Request URL: " + url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("X-SD-AD-TOKEN", auth);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        LOG.info("Response : " + response.toString());
        return new JSONObject(response.toString());

    }
}
