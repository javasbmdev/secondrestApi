package com.miniproject.service;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.miniproject.dto.SearchRequestDto;
import com.miniproject.dto.SearchResponseDto;

public interface PlansReportService {
	public List<String> getAllUniquePlanName();
	public List<String> getAllUniquePlanStatus();
	public List<SearchResponseDto> getAllPlanReport(SearchRequestDto  searchRequestDto);
	public void generatePlanReportAsPdf(HttpServletResponse response) throws Exception;
	public void generatePlanReportAsXls(HttpServletResponse httpServletResponse)throws Exception;
}
