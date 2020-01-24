package com.snapdeal.util;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.jayway.restassured.RestAssured.given;

public class HTTPUtility {

    public String getRequest(String url) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();

            if (responseCode != 200)
                return null;
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String postRequest(String path, String body) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBody(body);
        builder.setContentType("application/json; charset=UTF-8");
        RequestSpecification requestSpec = builder.build();
        Response response;
        boolean flag = true;
        int retry = 0;
        while (flag && retry < 10) {
            try {
                response = given().spec(requestSpec).when().post(path);
                flag = false;
                return response.asString();
            } catch (Exception e) {
                retry++;
            }
        }
        return null;
    }
}