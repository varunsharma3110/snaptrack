package com.snapdeal.repository;

import com.snapdeal.entity.SnapTrackMaster;
import com.snapdeal.entity.SnaptrackMasterDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISnapTrackMasterDecisonRepository extends JpaRepository<SnaptrackMasterDecision,Integer> {

     @Query(" Select * from snaptrack_master_decison where order_id =?")
     List<SnaptrackMasterDecision> findDecisonTreeByOrderId(String orderId);


}
