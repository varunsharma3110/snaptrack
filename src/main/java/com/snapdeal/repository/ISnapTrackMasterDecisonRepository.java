package com.snapdeal.repository;

import com.snapdeal.entity.SnapTrackMaster;
import com.snapdeal.entity.SnaptrackMasterDecision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface ISnapTrackMasterDecisonRepository extends JpaRepository<SnaptrackMasterDecision,Integer> {


     @Query(" Select smd from SnaptrackMasterDecision smd where smd.orderId =?1")
     List<SnaptrackMasterDecision> findDecisonTreeByOrderId(String orderId);


}
