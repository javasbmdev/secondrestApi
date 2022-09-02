package com.miniproject.entities;

import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name = "plan_report")
public class PlansReport {
	@Id
	@GeneratedValue
	@Column(name = "plan_id")
	private Integer planId;
	@Column(name = "full_name")
	private String fullName;
	@Column(name = "mobile_no")
	private String mobileNo;
	@Column(name = "email_address")
	private String emailAddress;
	private String gender;
	@Column(name = "adhar_number")
	private String adharNumber;
	@Column(name = "plan_name")
	private String planName;
	@Column(name = "plan_status")
	private String planStatus;
	@Column(name = "start_date")
	private LocalDate startDate;
	@Column(name = "end_date")
	private LocalDate endDate;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "created_date")
	private LocalDate createdDate;
	@Column(name = "updated_date")
	private LocalDate updatedDate;

}
