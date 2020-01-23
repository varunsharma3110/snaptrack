package com.snapdeal.repository;


import com.snapdeal.entity.SnapTrackMaster;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ISnapTrackRepository extends JpaRepository<SnapTrackMaster,Integer> {


}
