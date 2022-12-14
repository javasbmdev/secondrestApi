package com.miniproject.service;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.miniproject.dto.SearchRequestDto;
import com.miniproject.dto.SearchResponseDto;
import com.miniproject.entities.PlansReport;
import com.miniproject.repository.PlanReportRepository;

@Service
public class PlanReportServiceImpl implements PlansReportService {
	@Autowired
	private PlanReportRepository planReportRepository;

	@Override
	public List<String> getAllUniquePlanName() {
		return planReportRepository.findPlanNames();
	}

	@Override
	public List<String> getAllUniquePlanStatus() {
		return planReportRepository.findStatuses();
	}

	@Override
	public List<SearchResponseDto> getAllPlanReport(SearchRequestDto searchRequestDto) {
		List<SearchResponseDto> responseDtos = new ArrayList<>();
		PlansReport queryBuilder = new PlansReport();

		// BeanUtils.copyProperties(searchRequestDto, queryBuilder);

		String planName = searchRequestDto.getPlanName();

		if (planName != null && !planName.equals("")) {
			queryBuilder.setPlanName(planName);
		}
		String planStatus = searchRequestDto.getPlanStatus();
		if (planStatus != null && !planStatus.equals("")) {
			queryBuilder.setPlanStatus(planStatus);
		}
		LocalDate startDate = searchRequestDto.getStartDate();
		if (startDate != null) {
			queryBuilder.setStartDate(searchRequestDto.getStartDate());
		}
		LocalDate endDate = searchRequestDto.getEndDate();
		if (endDate != null) {
			queryBuilder.setEndDate(searchRequestDto.getEndDate());
		}

		Example<PlansReport> example = Example.of(queryBuilder);
		
		List<PlansReport> entities = planReportRepository.findAll(example);

		entities.forEach(planEntity -> {
			SearchResponseDto responseDto = new SearchResponseDto();
			BeanUtils.copyProperties(planEntity, responseDto);
			responseDtos.add(responseDto);
		});

		return responseDtos;
	}

	@Override
	public void generatePlanReportAsPdf(HttpServletResponse response) throws Exception {
		Document document = new Document(PageSize.A4);
		PdfWriter pdfWriter = PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(16);
		font.setColor(Color.CYAN);

		Paragraph p = new Paragraph("AllPlanReport", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);

		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 3.0f, 1.5f, 3.0f });
		table.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.WHITE);
		cell.setPadding(5);

		font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.BLUE);

		cell.setPhrase(new Phrase("Serial No", font));

		table.addCell(cell);

		cell.setPhrase(new Phrase("Full Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Email Address", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Mobile NO", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Gender", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Adhar Number", font));
		table.addCell(cell);

		List<PlansReport> entities = planReportRepository.findAll();
		int i = 0;
		for (PlansReport entity : entities) {
			table.addCell((entity.getPlanId().toString()));
			table.addCell(entity.getFullName());
			table.addCell(entity.getEmailAddress());
			table.addCell(entity.getMobileNo());
			table.addCell(entity.getGender());
			table.addCell(entity.getAdharNumber());
		}
		document.add(table);
		document.close();
	}

	@Override
	public void generatePlanReportAsXls(HttpServletResponse httpServletResponse) throws Exception {

		List<PlansReport> planReports = null;
		Workbook workbook = null;
		Sheet sheet = null;
		Row dataRow = null;
		Row headerRow = null;
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet("PlanReports");
		headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("SNo");
		headerRow.createCell(1).setCellValue("Full Name");
		headerRow.createCell(2).setCellValue("Email Address");
		headerRow.createCell(3).setCellValue("Mobile Number");
		headerRow.createCell(4).setCellValue("Gender");
		headerRow.createCell(5).setCellValue("Adhar Number");

		/*
		 * planReportRepository.findAll().forEach(entity -> { });
		 */
		planReports = planReportRepository.findAll();
		int i = 1;
		for (PlansReport entity : planReports) {
			dataRow = sheet.createRow(i);
			dataRow.createCell(0).setCellValue(entity.getPlanId());// here intsead of using loop seting manually
			dataRow.createCell(1).setCellValue(entity.getFullName());
			dataRow.createCell(2).setCellValue(entity.getEmailAddress());
			dataRow.createCell(3).setCellValue(entity.getMobileNo());
			dataRow.createCell(4).setCellValue(entity.getGender());
			dataRow.createCell(5).setCellValue(entity.getAdharNumber());
			i++;
		}
		ServletOutputStream outputStream = httpServletResponse.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

}
