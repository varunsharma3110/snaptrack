package com.snapdeal.web.services;

import com.snapdeal.entity.SnapTrackMaster;
import com.snapdeal.entity.SnaptrackMasterDecision;
import com.snapdeal.enums.Decision;
import com.snapdeal.enums.RTOType;
import com.snapdeal.repository.ISnapTrackMasterDecisonRepository;
import com.snapdeal.repository.ISnapTrackRepository;
import com.snapdeal.sro.GeoAngleSRO;
import com.snapdeal.sro.GeoPointSRO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DecisionTreeService {

    @Autowired
    ISnapTrackRepository snapTrackRepository;

    @Autowired
    ISnapTrackMasterDecisonRepository masterDecisonRepository;

    @Autowired
    GeoLocationService geoLocationService;

    public String createDecisionJson(RTOType type, SnaptrackMasterDecision decision) {
        JSONObject object = new JSONObject();
        switch (type) {
            case CR:
                object.put("name", "getOTP");
                object.put("parent", "null");
                object.put("color", "blue");
                if (decision.getOtp_validated()) {
                    JSONArray arrayChild = new JSONArray();
                    JSONObject childYes = new JSONObject();
                    childYes.put("name", "OTP/Yes");
                    childYes.put("parent", "getOTP");
                    childYes.put("color", "blue");
                    arrayChild.put(childYes);
                    JSONObject childNo = new JSONObject();
                    childNo.put("name", "OTP/No");
                    childNo.put("parent", "getOTP");
//                    childNo.put("color", "black");
                    arrayChild.put(childNo);
                    object.put("children", arrayChild);

                    JSONObject leaf = new JSONObject();
                    JSONArray arrayLeaf = new JSONArray();
                    leaf.put("name", "Not Fake, Valid RTO");
                    leaf.put("parent", "OTP/Yes");
                    leaf.put("color", "green");
                    arrayLeaf.put(leaf);
                    childYes.put("children", arrayLeaf);
                    SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                    master.setDtReason(Decision.NOT_FAKE.name());
                    snapTrackRepository.save(master);
                } else {
                    JSONObject childNo = new JSONObject();
                    JSONArray arrayChild = new JSONArray();
                    childNo.put("name", "OTP/No");
                    childNo.put("parent", "getOTP");
                    childNo.put("color", "blue");
                    arrayChild.put(childNo);
                    JSONObject childYes = new JSONObject();
                    childYes.put("name", "OTP/Yes");
                    childYes.put("parent", "getOTP");
//                    childYes.put("color", "black");
                    arrayChild.put(childYes);
                    object.put("children", arrayChild);

                    JSONObject child1 = new JSONObject();
                    JSONArray arrayChild1 = new JSONArray();
                    child1.put("name", "getLocation");
                    child1.put("parent", "OTP/No");
                    child1.put("color", "blue");
                    arrayChild1.put(child1);
                    childNo.put("children", arrayChild1);

                    if (!decision.getLoc_validated()) {
                        JSONObject child2No = new JSONObject();
                        JSONArray arrayChild2 = new JSONArray();
                        child2No.put("name", "Location/No");
                        child2No.put("parent", "getLocation");
                        child2No.put("color", "blue");

                        JSONObject child2Yes = new JSONObject();
                        child2Yes.put("name", "Location/Yes");
                        child2Yes.put("parent", "getLocation");
//                        child2Yes.put("color", "black");

                        arrayChild2.put(child2No);
                        arrayChild2.put(child2Yes);
                        child1.put("children", arrayChild2);


                        JSONObject child3 = new JSONObject();
                        JSONArray arrayChild3 = new JSONArray();
                        child3.put("name", "getCallDetails");
                        child3.put("parent", "Location/No");
                        child3.put("color", "blue");
                        arrayChild3.put(child3);
                        child2No.put("children", arrayChild3);

                        if(decision.getCall_validated()) {
                            JSONObject child4No = new JSONObject();
                            JSONArray arrayChild4 = new JSONArray();
                            child4No.put("name", "Called/No");
                            child4No.put("parent", "getLocation");
//                            child4No.put("color", "black");

                            JSONObject child4Yes = new JSONObject();
                            child4Yes.put("name", "Called/Yes");
                            child4Yes.put("parent", "getLocation");
                            child4Yes.put("color", "blue");

                            arrayChild4.put(child4Yes);
                            arrayChild4.put(child4No);
                            child3.put("children", arrayChild4);

                            JSONObject leaf = new JSONObject();
                            JSONArray arrayLeaf = new JSONArray();
                            leaf.put("name", "Yellow Zone");
                            leaf.put("parent", "Called/Yes");
                            leaf.put("color", "yellow");
                            // child2.put("children", leaf);
                            arrayLeaf.put(leaf);
                            child4Yes.put("children", arrayLeaf);
                            SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                            master.setDtReason(Decision.YELLOW_ZONE.name());
                            snapTrackRepository.save(master);
                        }
                        else {
                            JSONObject child4No = new JSONObject();
                            JSONArray arrayChild4 = new JSONArray();
                            child4No.put("name", "Called/No");
                            child4No.put("parent", "getLocation");
                            child4No.put("color", "blue");

                            JSONObject child4Yes = new JSONObject();
                            child4Yes.put("name", "Called/Yes");
                            child4Yes.put("parent", "getLocation");
//                            child4Yes.put("color", "black");

                            arrayChild4.put(child4Yes);
                            arrayChild4.put(child4No);
                            child3.put("children", arrayChild4);

                            JSONObject leaf = new JSONObject();
                            JSONArray arrayLeaf = new JSONArray();
                            leaf.put("name", "Fake RTO");
                            leaf.put("parent", "Called/No");
                            leaf.put("color", "red");
                            arrayLeaf.put(leaf);
                            child4Yes.put("children", arrayLeaf);
                            SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                            master.setDtReason(Decision.FAKE.name());
                            snapTrackRepository.save(master);
                        }

//                        JSONObject leaf = new JSONObject();
//                        JSONArray arrayLeaf = new JSONArray();
//                        leaf.put("name", "Fake RTO");
//                        leaf.put("parent", "Call/No");
//                        leaf.put("color", "red");
//                        arrayLeaf.put(leaf);
//                        child2No.put("children", arrayLeaf);
//                        childNo.put("children", arrayChild1);
//                        SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
//                        master.setDtReason(Decision.FAKE.name());
//                        snapTrackRepository.save(master);
                    } else {
                        JSONObject child2Yes = new JSONObject();
                        JSONArray arrayChild2 = new JSONArray();
                        child2Yes.put("name", "Call/Yes");
                        child2Yes.put("parent", "getCallDetails");
                        child2Yes.put("color", "blue");

                        JSONObject child2No = new JSONObject();
                        child2No.put("name", "Call/No");
                        child2No.put("parent", "getCallDetails");
//                        child2No.put("color", "black");

                        JSONObject leaf = new JSONObject();
                        JSONArray arrayLeaf = new JSONArray();
                        leaf.put("name", "Yellow Zone");
                        leaf.put("parent", "Call/Yes");
                        leaf.put("color", "yellow");
                        // child2.put("children", leaf);
                        arrayLeaf.put(leaf);
                        child2Yes.put("children", arrayLeaf);
                        // child1.put("children", child2);
                        arrayChild2.put(child2Yes);
                        arrayChild2.put(child2No);
                        child1.put("children", arrayChild2);
                        // child.put("children", child1);
                        arrayChild1.put(child1);
                        childNo.put("children", arrayChild1);
                        SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                        master.setDtReason(Decision.YELLOW_ZONE.name());
                        snapTrackRepository.save(master);
                    }
                }
                break;
            case CNA:
                object.put("name", "getLocation");
                object.put("parent", "null");
                object.put("color", "blue");
                if (decision.getLoc_validated()) {
                    JSONArray arrayChild = new JSONArray();
                    JSONObject childYes = new JSONObject();
                    childYes.put("name", "Location/Yes");
                    childYes.put("parent", "getLocation");
                    childYes.put("color", "blue");
                    arrayChild.put(childYes);
                    JSONObject childNo = new JSONObject();
                    childNo.put("name", "Location/No");
                    childNo.put("parent", "getLocation");
//                    childNo.put("color", "black");
                    arrayChild.put(childNo);
                    object.put("children", arrayChild);

                    JSONObject leaf = new JSONObject();
                    JSONArray arrayLeaf = new JSONArray();
                    leaf.put("name", "Not Fake, Valid RTO");
                    leaf.put("parent", "Location/Yes");
                    leaf.put("color", "green");
                    arrayLeaf.put(leaf);
                    childYes.put("children", arrayLeaf);
                    SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                    master.setDtReason(Decision.NOT_FAKE.name());
                    snapTrackRepository.save(master);
                } else {
                    JSONObject childNo = new JSONObject();
                    JSONArray arrayChild = new JSONArray();
                    childNo.put("name", "Location/No");
                    childNo.put("parent", "getLocation");
                    childNo.put("color", "blue");
                    arrayChild.put(childNo);
                    JSONObject childYes = new JSONObject();
                    childYes.put("name", "Location/Yes");
                    childYes.put("parent", "getLocation");
//                    childYes.put("color", "black");
                    arrayChild.put(childYes);
                    object.put("children", arrayChild);

                    JSONObject child1 = new JSONObject();
                    JSONArray arrayChild1 = new JSONArray();
                    child1.put("name", "getCallDetails");
                    child1.put("parent", "Location/No");
                    child1.put("color", "blue");


                    if (!decision.getCall_validated()) {
                        JSONObject child2No = new JSONObject();
                        JSONArray arrayChild2 = new JSONArray();
                        child2No.put("name", "Call/No");
                        child2No.put("parent", "getCallDetails");
                        child2No.put("color", "blue");

                        JSONObject child2Yes = new JSONObject();
                        child2Yes.put("name", "Call/Yes");
                        child2Yes.put("parent", "getCallDetails");
//                        child2Yes.put("color", "black");

                        JSONObject leaf = new JSONObject();
                        JSONArray arrayLeaf = new JSONArray();
                        leaf.put("name", "Fake RTO");
                        leaf.put("parent", "Call/No");
                        leaf.put("color", "red");
                        // child2.put("children", leaf);
                        arrayLeaf.put(leaf);
                        child2No.put("children", arrayLeaf);
                        SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                        master.setDtReason(Decision.FAKE.name());
                        snapTrackRepository.save(master);
                        // child1.put("children", child2);
                        arrayChild2.put(child2No);
                        arrayChild2.put(child2Yes);
                        child1.put("children", arrayChild2);
                        // child.put("children", child1);
                        arrayChild1.put(child1);
                        childNo.put("children", arrayChild1);
                    } else {
                        JSONObject child2Yes = new JSONObject();
                        JSONArray arrayChild2 = new JSONArray();
                        child2Yes.put("name", "Call/Yes");
                        child2Yes.put("parent", "getCallDetails");
                        child2Yes.put("color", "blue");

                        JSONObject child2No = new JSONObject();
                        child2No.put("name", "Call/No");
                        child2No.put("parent", "getCallDetails");
//                        child2No.put("color", "black");

                        JSONObject leaf = new JSONObject();
                        JSONArray arrayLeaf = new JSONArray();
                        leaf.put("name", "Yellow Zone");
                        leaf.put("parent", "Call/Yes");
                        leaf.put("color", "yellow");
                        // child2.put("children", leaf);
                        arrayLeaf.put(leaf);
                        child2Yes.put("children", arrayLeaf);
                        SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                        master.setDtReason(Decision.YELLOW_ZONE.name());
                        snapTrackRepository.save(master);
                        // child1.put("children", child2);
                        arrayChild2.put(child2Yes);
                        arrayChild2.put(child2No);
                        child1.put("children", arrayChild2);
                        // child.put("children", child1);
                        arrayChild1.put(child1);
                        childNo.put("children", arrayChild1);
                    }
                }
                break;
            case CNR:
                object.put("name", "getLocation");
                object.put("parent", "null");
                object.put("color", "blue");
                if (decision.getLoc_validated()) {
                    JSONArray arrayChild = new JSONArray();
                    JSONObject childYes = new JSONObject();
                    childYes.put("name", "Location/Yes");
                    childYes.put("parent", "getLocation");
                    childYes.put("color", "blue");
                    JSONObject childNo = new JSONObject();
                    childNo.put("name", "Location/No");
                    childNo.put("parent", "getLocation");
//                    childNo.put("color", "black");
                    arrayChild.put(childNo);
                    arrayChild.put(childYes);
                    object.put("children", arrayChild);

                    JSONObject leaf = new JSONObject();
                    JSONArray arrayLeaf = new JSONArray();
                    leaf.put("name", "Not Fake, Valid RTO");
                    leaf.put("parent", "Location/Yes");
                    leaf.put("color", "green");
                    arrayLeaf.put(leaf);
                    childYes.put("children", arrayLeaf);
                    SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                    master.setDtReason(Decision.NOT_FAKE.name());
                    snapTrackRepository.save(master);
                } else {
                    JSONArray arrayChild = new JSONArray();
                    JSONObject childNo = new JSONObject();
                    childNo.put("name", "Location/No");
                    childNo.put("parent", "getLocation");
                    childNo.put("color", "blue");
                    JSONObject childYes = new JSONObject();
                    childYes.put("name", "Location/Yes");
                    childYes.put("parent", "getLocation");
 //                   childYes.put("color", "black");
                    arrayChild.put(childYes);
                    arrayChild.put(childNo);
                    object.put("children", arrayChild);

                    JSONObject leaf = new JSONObject();
                    JSONArray arrayLeaf = new JSONArray();
                    leaf.put("name", "Fake RTO");
                    leaf.put("parent", "Location/No");
                    leaf.put("color", "red");
                    arrayLeaf.put(leaf);
                    childNo.put("children", arrayLeaf);
                    SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                    master.setDtReason(Decision.FAKE.name());
                    snapTrackRepository.save(master);
                }
                break;
            case CD:
                object.put("name", "getCallDetails");
                object.put("parent", "null");
                object.put("color", "blue");
                if (decision.getCall_validated()) {
                    JSONArray arrayChild = new JSONArray();
                    JSONObject childYes = new JSONObject();
                    childYes.put("name", "Called/Yes");
                    childYes.put("parent", "getCallDetails");
                    childYes.put("color", "blue");
                    arrayChild.put(childYes);
                    JSONObject childNo = new JSONObject();
                    childNo.put("name", "Called/No");
                    childNo.put("parent", "getCallDetails");
//                    childNo.put("color", "black");
                    arrayChild.put(childNo);
                    object.put("children", arrayChild);

                    JSONObject leaf = new JSONObject();
                    JSONArray arrayLeaf = new JSONArray();
                    leaf.put("name", "Yellow Zone");
                    leaf.put("parent", "Called/Yes");
                    leaf.put("color", "yellow");
                    arrayLeaf.put(leaf);
                    childYes.put("children", arrayLeaf);
                    SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                    master.setDtReason(Decision.YELLOW_ZONE.name());
                    snapTrackRepository.save(master);
                } else {
                    JSONArray arrayChild = new JSONArray();
                    JSONObject childNo = new JSONObject();
                    childNo.put("name", "Called/No");
                    childNo.put("parent", "getCallDetails");
                    childNo.put("color", "blue");
                    arrayChild.put(childNo);
                    JSONObject childYes = new JSONObject();
                    childYes.put("name", "Called/Yes");
                    childYes.put("parent", "getCallDetails");
//                    childYes.put("color", "black");
                    arrayChild.put(childYes);
                    object.put("children", arrayChild);

                    JSONObject leaf = new JSONObject();
                    JSONArray arrayLeaf = new JSONArray();
                    leaf.put("name", "Fake RTO");
                    leaf.put("parent", "Called/No");
                    leaf.put("color", "red");
                    arrayLeaf.put(leaf);
                    childNo.put("children", arrayLeaf);
                    SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                    master.setDtReason(Decision.FAKE.name());
                    snapTrackRepository.save(master);
                }
                break;
            case AI:
                object.put("name", "getLocation");
                object.put("parent", "null");
                object.put("color", "blue");
                if (decision.getLoc_validated()) {
                    JSONArray arrayChild = new JSONArray();
                    JSONObject childYes = new JSONObject();
                    childYes.put("name", "Location/Yes");
                    childYes.put("parent", "getLocation");
                    childYes.put("color", "blue");
                    arrayChild.put(childYes);
                    JSONObject childNo = new JSONObject();
                    childNo.put("name", "Location/No");
                    childNo.put("parent", "getLocation");
//                    childNo.put("color", "black");
                    arrayChild.put(childNo);
                    object.put("children", arrayChild);

                    JSONObject leaf = new JSONObject();
                    JSONArray arrayLeaf = new JSONArray();
                    leaf.put("name", "Not Fake, Valid RTO");
                    leaf.put("parent", "Location/Yes");
                    leaf.put("color", "green");
                    arrayLeaf.put(leaf);
                    childYes.put("children", arrayLeaf);
                    SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                    master.setDtReason(Decision.NOT_FAKE.name());
                    snapTrackRepository.save(master);
                } else {
                    JSONObject childNo = new JSONObject();
                    JSONArray arrayChild = new JSONArray();
                    childNo.put("name", "Location/No");
                    childNo.put("parent", "getLocation");
                    childNo.put("color", "blue");
                    arrayChild.put(childNo);
                    JSONObject childYes = new JSONObject();
                    childYes.put("name", "Location/Yes");
                    childYes.put("parent", "getLocation");
//                    childYes.put("color", "black");
                    arrayChild.put(childYes);
                    object.put("children", arrayChild);

                    JSONObject child1 = new JSONObject();
                    JSONArray arrayChild1 = new JSONArray();
                    child1.put("name", "getCallDetails");
                    child1.put("parent", "Location/No");
                    child1.put("color", "blue");

                    if (!decision.getCall_validated()) {
                        JSONObject child2No = new JSONObject();
                        JSONArray arrayChild2 = new JSONArray();
                        child2No.put("name", "Call/No");
                        child2No.put("parent", "getCallDetails");
                        child2No.put("color", "blue");

                        JSONObject child2Yes = new JSONObject();
                        child2Yes.put("name", "Call/Yes");
                        child2Yes.put("parent", "getCallDetails");
//                        child2Yes.put("color", "black");

                        JSONObject leaf = new JSONObject();
                        JSONArray arrayLeaf = new JSONArray();
                        leaf.put("name", "Fake RTO");
                        leaf.put("parent", "Call/No");
                        leaf.put("color", "red");
                        // child2.put("children", leaf);
                        arrayLeaf.put(leaf);
                        child2No.put("children", arrayLeaf);
                        SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                        master.setDtReason(Decision.FAKE.name());
                        snapTrackRepository.save(master);
                        // child1.put("children", child2);
                        arrayChild2.put(child2No);
                        arrayChild2.put(child2Yes);
                        child1.put("children", arrayChild2);
                        // child.put("children", child1);
                        arrayChild1.put(child1);
                        childNo.put("children", arrayChild1);
                    } else {
                        JSONObject child2Yes = new JSONObject();
                        JSONArray arrayChild2 = new JSONArray();
                        child2Yes.put("name", "Call/Yes");
                        child2Yes.put("parent", "getCallDetails");
                        child2Yes.put("color", "blue");

                        JSONObject child2No = new JSONObject();
                        child2No.put("name", "Call/No");
                        child2No.put("parent", "getCallDetails");
//                        child2No.put("color", "black");

                        JSONObject leaf = new JSONObject();
                        JSONArray arrayLeaf = new JSONArray();
                        leaf.put("name", "Yellow Zone");
                        leaf.put("parent", "Call/Yes");
                        leaf.put("color", "yellow");
                        // child2.put("children", leaf);
                        arrayLeaf.put(leaf);
                        child2Yes.put("children", arrayLeaf);
                        SnapTrackMaster master = snapTrackRepository.findOneByOrderId(decision.getOrderId()).get(0);
                        master.setDtReason(Decision.YELLOW_ZONE.name());
                        snapTrackRepository.save(master);
                        // child1.put("children", child2);
                        arrayChild2.put(child2Yes);
                        arrayChild2.put(child2No);
                        child1.put("children", arrayChild2);
                        // child.put("children", child1);
                        arrayChild1.put(child1);
                        childNo.put("children", arrayChild1);
                    }

                }
        }
        return object.toString();
    }

    public void updateMasterDecisionTable() {
        List<SnapTrackMaster> masterList = snapTrackRepository.findAll();
        for (SnapTrackMaster master : masterList) {
            List<SnaptrackMasterDecision> decisionList = masterDecisonRepository.findDecisonTreeByOrderId(master.getOrderId());
            SnaptrackMasterDecision decision;
            if (decisionList != null && decisionList.size() > 0)
                decision = decisionList.get(0);
            else {
                decision = new SnaptrackMasterDecision();
                decision.setOrderId(master.getOrderId());
                decision.setCreated(new Date());
            }
            if (master.getOtp() != null)
                decision.setOtp_validated(true);
            else
                decision.setOtp_validated(false);
            if (master.getDistance() < 5)
                decision.setLoc_validated(true);
            else
                decision.setLoc_validated(false);

            String duration = master.getCallDuration();
            if (duration != null) {
                int min = Integer.parseInt(duration.split(":")[0]);
                int sec = Integer.parseInt(duration.split(":")[1]);
                int time = min * 60 + sec;
                if (master.getCallStatus().equals("Connected") && time > 10)
                    decision.setCall_validated(true);
                else
                    decision.setCall_validated(false);
            }
            else
                decision.setCall_validated(false);
            decision.setRtoReason(master.getRtoReason());

            masterDecisonRepository.saveAndFlush(decision);
        }
    }
}