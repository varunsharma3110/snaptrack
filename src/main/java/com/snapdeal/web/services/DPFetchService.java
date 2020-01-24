package com.snapdeal.web.services;

import com.snapdeal.util.HTTPUtility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class DPFetchService {

    HTTPUtility httpUtility;

    @PostConstruct
    public void init() {
        httpUtility = new HTTPUtility();
//        getDataFromDP();
    }

    public void getDataFromDP() {
        String listURL = "http://10.65.31.51/scheduled/38589/1/?user.name=client&op=LISTSTATUS";
        JSONObject dpResponse = new JSONObject(httpUtility.getRequest(listURL));
        List<String> partFiles = new ArrayList<>();
        JSONArray files = dpResponse.getJSONObject("FileStatuses").getJSONArray("FileStatus");
        for (int i = 1; i < files.length(); ++i) {
            JSONObject obj = files.getJSONObject(i);
            partFiles.add(obj.getString("pathSuffix"));
        }
        for (String partFile : partFiles) {
            String fileURL = "http://10.65.31.51/scheduled/38589/1/" + partFile + "?user.name=client&op=OPEN";
            String data = httpUtility.getRequest(fileURL);
            System.out.println(data);
        }
    }
}
