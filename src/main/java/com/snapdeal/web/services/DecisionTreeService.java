package com.snapdeal.web.services;

import com.snapdeal.entity.SnaptrackMasterDecision;
import com.snapdeal.enums.RTOType;
import org.json.JSONArray;
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
                    child.put("name", "Yes->Not Fake");
                    child.put("parent", "OTPAvailable");
                    JSONArray array = new JSONArray();
                    array.put(child);
                    object.put("children", array);
                }
                else {
                    JSONObject child = new JSONObject();
                    child.put("name", "No->getLocation");
                    child.put("parent", "OTPAvailable");
                    JSONArray array = new JSONArray();
                    array.put(child);
                    object.put("children", array);
                    if (decision.getLoc_validated()) {
                        child = new JSONObject();
                        child.put("name", "Yes->Not Fake");
                        child.put("parent", "No->getLocation");
                        array = new JSONArray();
                        array.put(child);
                        object.getJSONArray("children").getJSONObject(0).put("children", array);
                    }
                    else {
                        child = new JSONObject();
                        child.put("name", "No->callStatus");
                        child.put("parent", "No->getLocation");
                        array = new JSONArray();
                        array.put(child);
                        object.getJSONArray("children").getJSONObject(0).put("children", array);
                        if (decision.getCall_validated()) {
                            child = new JSONObject();
                            child.put("name", "Yes->Recommended for QC");
                            child.put("parent", "No->callStatus");
                            object.getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(0).put("children", child);
                        }
                        else {
                            child = new JSONObject();
                            child.put("name", "No->Fake");
                            child.put("parent", "No->callStatus");
                            object.getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(0).put("children", child);
                        }
                    }
                }
                break;
            case CNA:
                object.put("name", "getLocation");
                object.put("parent", "null");
                if (decision.getLoc_validated()) {
                    JSONObject child = new JSONObject();
                    child.put("name", "Location/Yes");
                    child.put("parent", "getLocation");
                    object.put("children", child);

                    JSONObject leaf = new JSONObject();
                    leaf.put("name", "Not Fake, Valid RTO");
                    child.put("children", leaf);
                }
                else {
                    JSONObject child = new JSONObject();
                    child.put("name", "Location/No");
                    child.put("parent", "getLocation");
                    object.put("children", child);

                    JSONObject child1 = new JSONObject();
                    child1.put("name", "getCallDetails");
                    child1.put("parent", "Location/No");

                    if(!decision.getCall_validated()) {
                        JSONObject child2 = new JSONObject();
                        child2.put("name", "Call/No");
                        child2.put("parent", "getCallDetails");

                        JSONObject leaf = new JSONObject();
                        leaf.put("name", "Fake RTO");
                        leaf.put("parent", "Call/No");
                        child2.put("children", leaf);
                        child1.put("children", child2);
                        child.put("children", child1);
                    }
                    else {
                        JSONObject child2 = new JSONObject();
                        child2.put("name", "Call/Yes");
                        child2.put("parent", "getCallDetails");

                        JSONObject leaf = new JSONObject();
                        leaf.put("name", "Yellow Zone");
                        leaf.put("parent", "Call/Yes");
                        child2.put("children", leaf);
                        child1.put("children", child2);
                        child.put("children", child1);
                    }
                }
                break;
        }
        return object.toString();
    }

    public static void main(String[] args) {
        DecisionTreeService service = new DecisionTreeService();
        SnaptrackMasterDecision decision = new SnaptrackMasterDecision();
        decision.setOtp_validated(false);
        decision.setLoc_validated(false);
        decision.setCall_validated(true);
        decision.setRtoReason("CR");
        String json = service.createDecisionJson(RTOType.valueOf(decision.getRtoReason()), decision);
        System.out.println(json);
    }
}
