package com.snapdeal.web.services;

import com.snapdeal.entity.SnaptrackMasterDecision;
import com.snapdeal.enums.RTOType;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class DecisionTreeService {

    public String createDecisionJson(RTOType type, SnaptrackMasterDecision decision) {
        JSONObject object = new JSONObject();
        switch (type) {
            case CR:
                object.put("name", "OTPAvailable");
                object.put("parent", "null");
                if (decision.getOtp_validated()) {
                    JSONObject child = new JSONObject();
                    child.put("name", "OTP/Yes");
                    child.put("parent", "OTPAvailable");
                    object.put("children", child);
                }
                else {
                    JSONObject child = new JSONObject();
                    child.put("name", "OTP/No");
                    object.put("parent", "OTPAvailable");
                    object.put("children", child);
                    if (decision.getLoc_validated()) {
                        child = new JSONObject();
                        child.put("name", "getLocation/Yes");
                        child.put("parent", "OTPAvailable");
                        object.getJSONObject("children").put("children", child);
                    }
                    else {
                        child = new JSONObject();
                        child.put("name", "getLocation/No");
                        child.put("parent", "OTPAvailable");
                        object.getJSONObject("children").put("children", child);
                        if (decision.getCall_validated()) {
                            child = new JSONObject();
                            child.put("name", "callStatus/Yes");
                            child.put("parent", "getLocation/No");
                            object.getJSONObject("children").getJSONObject("children").put("name", "Yellow zone");
                        }
                        else {
                            child = new JSONObject();
                            child.put("name", "callStatus/No");
                            child.put("parent", "getLocation/No");
                            object.getJSONObject("children").getJSONObject("children").put("name", "Fake");
                        }
                    }
                }
                break;
            case CNA:
                object.put("name", "getLocation/Yes");
                object.put("parent", "null");

        }
        return object.toString();
    }
}
