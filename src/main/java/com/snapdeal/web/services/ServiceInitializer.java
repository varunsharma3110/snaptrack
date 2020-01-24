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


    @PostConstruct
    private void init(){

        List<SnapTrackMaster> unprocessedSnapTrackMaster=snapTrackRepository.findAllUnprocessedYellowZoneOrderIds(false, Decision.YELLOW_ZONE.name());

        probabilityCalculatorService.feedProbabilityPercentageforYellowZoneTickets(unprocessedSnapTrackMaster);

    }
}
