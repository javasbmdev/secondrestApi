package com.miniproject.dto;

import lombok.Data;

@Data
public class SearchResponseDto {
	private int planId;
	private String fullName;
	private String emailAddress;
	private String mobileNo;
	private String gender;
	private String adharNumber;

}
