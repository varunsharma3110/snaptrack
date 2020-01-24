package com.snapdeal.repository;


import com.snapdeal.entity.SnapTrackMaster;
import com.snapdeal.entity.SnaptrackMasterDecision;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ISnapTrackRepository extends JpaRepository<SnapTrackMaster,Integer> {

    @Query(" Select sm from SnapTrackMaster sm where sm.dtReason =?1")
    List<SnapTrackMaster> findAllByDecison(String decision);

    @Query(" Select sm from SnapTrackMaster sm where sm.orderId =?1")
    List<SnapTrackMaster> findOneByOrderId(String orderId);


}
