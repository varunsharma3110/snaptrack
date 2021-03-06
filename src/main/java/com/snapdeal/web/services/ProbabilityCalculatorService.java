package com.snapdeal.web.services;

import com.snapdeal.entity.SnapTrackMaster;
import com.snapdeal.repository.ISnapTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ProbabilityCalculatorService {

    @Autowired
    private ISnapTrackRepository snapTrackRepository;


    public int getProbabilityPercentage(SnapTrackMaster snapTrackMaster) {

        int percentage = 0;

        int callDurationInSeconds = Integer.parseInt(snapTrackMaster.getCallDuration().split(":")[1]);

        if (callDurationInSeconds > 20) {
            percentage += 10;
        }

        if (snapTrackMaster.getCustomerRtoScore() <= 0.5) {
            percentage += 50;
        }

        if (snapTrackMaster.getCustomerCancellationTickets() < 10) {
            percentage += 15;
        }

        return percentage;


    }

    public void feedProbabilityPercentageforYellowZoneTickets(List<SnapTrackMaster> snapTrackMasterList) {
        for (SnapTrackMaster snapTrackMaster : snapTrackMasterList) {
            snapTrackMaster.setProbabilityRecommendation(getProbabilityPercentage(snapTrackMaster));
            snapTrackRepository.save(snapTrackMaster);
        }


    }


}
