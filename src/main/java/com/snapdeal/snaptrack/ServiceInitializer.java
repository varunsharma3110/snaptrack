package com.snapdeal.snaptrack;

import com.snapdeal.entity.SnapTrackMaster;
import com.snapdeal.repository.ISnapTrackRepository;
import com.snapdeal.web.services.ProbabilityCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

public class ServiceInitializer {

    @Autowired
    ISnapTrackRepository snapTrackRepository;

    @Autowired
    ProbabilityCalculatorService probabilityCalculatorService;


    @PostConstruct
    private void init(){

        List<SnapTrackMaster> unprocessedSnapTrackMaster=snapTrackRepository.findAllUnprocessedYellowZoneOrderIds();

        probabilityCalculatorService.feedProbabilityPercentageforYellowZoneTickets(unprocessedSnapTrackMaster);

    }
}
