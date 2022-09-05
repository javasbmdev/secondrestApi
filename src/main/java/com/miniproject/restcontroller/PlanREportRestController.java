package com.miniproject.restcontroller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.dto.SearchRequestDto;
import com.miniproject.dto.SearchResponseDto;
import com.miniproject.service.PlansReportService;
@RestController
public class PlanREportRestController {
	@Autowired
	private PlansReportService service;
	
	@GetMapping("/plan-names")
	public ResponseEntity<List<String>> getAllPlanName(){
		return new ResponseEntity<>(service.getAllUniquePlanName(),HttpStatus.OK);
	}
	@GetMapping("/plan-statuses")
	public ResponseEntity<List< String>> getAllPlanStatuses(){
		return new ResponseEntity< >(service.getAllUniquePlanStatus(),HttpStatus.OK);
	}
	@PostMapping("/search")
	public ResponseEntity<List<SearchResponseDto>> search(@RequestBody SearchRequestDto requestDto){
		List<SearchResponseDto> response = service.getAllPlanReport(requestDto);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@GetMapping("/excel")
	public void getExcelReport(HttpServletResponse response) throws Exception {
		response.setContentType("application/actet-stream");
		String headerKey = "Content-Disposition";
		String value = "attachment;filename=PlanReport.xls";
		response.setHeader(headerKey, value);
		service.generatePlanReportAsXls(response);
	}
	@GetMapping("/pdf")
	public void  getPdfReport(HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String value = "attachment;filename=PlanReport.pdf";
		response.setHeader(headerKey, value);
		service.generatePlanReportAsPdf(response);
	}
	
}
