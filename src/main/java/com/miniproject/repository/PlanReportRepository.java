package com.miniproject.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject.entities.PlansReport;


public interface PlanReportRepository extends JpaRepository<PlansReport, Integer>{
	@Query("select DISTINCT(planName) from PlansReport")
	List<String> findPlanNames( );
	@Query("select distinct(planStatus) from PlansReport")
	List<String> findStatuses();
}
