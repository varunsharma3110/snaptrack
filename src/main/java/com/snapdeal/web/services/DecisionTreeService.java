package com.snapdeal.web.services;

import com.snapdeal.entity.SnapTrackMaster;
import com.snapdeal.entity.SnaptrackMasterDecision;
import com.snapdeal.enums.Decision;
import com.snapdeal.enums.RTOType;
import com.snapdeal.repository.ISnapTrackRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DecisionTreeService {

    @Autowired
    ISnapTrackRepository snapTrackRepository;

    public String createDecisionJson(RTOType type, SnaptrackMasterDecision decision) {
        JSONObject object = new JSONObject();
        switch (type) {
            case CR:
                object.put("name", "OTPAvailable");
                object.put("parent", "null");
                JSONObject child = new JSONObject();
                if (decision.getOtp_validated()) {
                    child.put("name", "Yes->Not Fake");
                    child.put("parent", "OTPAvailable");
                    JSONArray array = new JSONArray();
                    array.put(child);
                    object.put("children", array);
                }
                else {
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
                            array = new JSONArray();
                            array.put(child);
                            object.getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(0).put("children", array);
                            SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                            master.setDtReason(Decision.YELLOW.name());
                            snapTrackRepository.saveAndFlush(master);
                        }
                        else {
                            child = new JSONObject();
                            child.put("name", "No->Fake");
                            child.put("parent", "No->callStatus");
                            array = new JSONArray();
                            array.put(child);
                            object.getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(0).put("children", array);
                            SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                            master.setDtReason(Decision.FAKE.name());
                            snapTrackRepository.saveAndFlush(master);
                        }
                    }
                }
                break;
            case CNA:
                object.put("name", "getLocation");
                object.put("parent", "null");
                if (decision.getLoc_validated()) {
                    JSONArray arrayChild = new JSONArray();
                    child = new JSONObject();
                    child.put("name", "Location/Yes");
                    child.put("parent", "getLocation");
                    arrayChild.put(child);
                    object.put("children", arrayChild);

                    JSONObject leaf = new JSONObject();
                    JSONArray arrayLeaf = new JSONArray();
                    leaf.put("name", "Not Fake, Valid RTO");
                    leaf.put("parent", "Location/Yes");
                    arrayLeaf.put(leaf);
                    child.put("children", arrayLeaf);
                }
                else {
                    child = new JSONObject();
                    JSONArray arrayChild = new JSONArray();
                    child.put("name", "Location/No");
                    child.put("parent", "getLocation");
                    arrayChild.put(child);
                    object.put("children", arrayChild);

                    JSONObject child1 = new JSONObject();
                    JSONArray arrayChild1 = new JSONArray();
                    child1.put("name", "getCallDetails");
                    child1.put("parent", "Location/No");

                    if(!decision.getCall_validated()) {
                        JSONObject child2 = new JSONObject();
                        JSONArray arrayChild2 = new JSONArray();
                        child2.put("name", "Call/No");
                        child2.put("parent", "getCallDetails");

                        JSONObject leaf = new JSONObject();
                        JSONArray arrayLeaf = new JSONArray();
                        leaf.put("name", "Fake RTO");
                        leaf.put("parent", "Call/No");
                        //child2.put("children", leaf);
                        arrayLeaf.put(leaf);
                        child2.put("children", arrayLeaf);
                        //child1.put("children", child2);
                        arrayChild2.put(child2);
                        child1.put("children", arrayChild2);
                        //child.put("children", child1);
                        arrayChild1.put(child1);
                        child.put("children", arrayChild1);
                    }
                    else {
                        JSONObject child2 = new JSONObject();
                        JSONArray arrayChild2 = new JSONArray();
                        child2.put("name", "Call/Yes");
                        child2.put("parent", "getCallDetails");

                        JSONObject leaf = new JSONObject();
                        JSONArray arrayLeaf = new JSONArray();
                        leaf.put("name", "Yellow Zone");
                        leaf.put("parent", "Call/Yes");
                        //child2.put("children", leaf);
                        arrayLeaf.put(leaf);
                        child2.put("children", arrayLeaf);
                        //child1.put("children", child2);
                        arrayChild2.put(child2);
                        child1.put("children", arrayChild2);
                        //child.put("children", child1);
                        arrayChild1.put(child1);
                        child.put("children", arrayChild1);
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
