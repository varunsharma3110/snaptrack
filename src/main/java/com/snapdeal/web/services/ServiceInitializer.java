package com.snapdeal.web.services;

import com.snapdeal.entity.SnapTrackMaster;
import com.snapdeal.enums.Decision;
import com.snapdeal.repository.ISnapTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ServiceInitializer {

    @Autowired
    ISnapTrackRepository snapTrackRepository;

    @Autowired
    ProbabilityCalculatorService probabilityCalculatorService;

    @Autowired
    AddressService addressService;

    @Autowired
    DecisionTreeService decisionTreeService;

    @PostConstruct
    private void init(){

        List<SnapTrackMaster> unprocessedYellowZoneSnapTrackMaster=snapTrackRepository.findAllUnprocessedYellowZoneOrderIds(false, Decision.YELLOW_ZONE.name());

        probabilityCalculatorService.feedProbabilityPercentageforYellowZoneTickets(unprocessedYellowZoneSnapTrackMaster);

        List<SnapTrackMaster> unprocessedSnapTrackMaster=snapTrackRepository.findAllUnprocessedOrders(false);

        addressService.feedDistanceBetweenCustomerAndCourierLocation(unprocessedSnapTrackMaster);
        decisionTreeService.updateMasterDecisionTable();
    }
}
