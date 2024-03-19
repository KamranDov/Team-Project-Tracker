package com.crocusoft.teamprojecttracker.util;

import com.crocusoft.teamprojecttracker.model.DailyReport;
import com.crocusoft.teamprojecttracker.repository.DailyReportRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExcelExport {
    private final DailyReportRepository dailyReportRepository;

    public void dailyReportDataToExcel(HttpServletResponse response) throws IOException {
        List<DailyReport> dailyReports = dailyReportRepository.findAll();
        try (HSSFWorkbook hssfWorkbook = new HSSFWorkbook()) {
            HSSFSheet hssfSheet = hssfWorkbook.createSheet("Daily Report");
            HSSFRow hssfRow = hssfSheet.createRow(0);
            hssfRow.createCell(0).setCellValue("Daily Report ID");
            hssfRow.createCell(1).setCellValue("Employee ID");
            hssfRow.createCell(2).setCellValue("Employee Name");
            hssfRow.createCell(3).setCellValue("Employee Surname");
            hssfRow.createCell(4).setCellValue("Employee Email");
            hssfRow.createCell(5).setCellValue("Daily Report Description");
            hssfRow.createCell(6).setCellValue("Daily Report Created At");
            hssfRow.createCell(7).setCellValue("Project Name");

            int dataRowIndex = 1;
            for (DailyReport dailyReport : dailyReports) {
                if (!dailyReports.isEmpty()) {
                    HSSFRow dataRow = hssfSheet.createRow(dataRowIndex);
                    dataRow.createCell(0).setCellValue(dailyReport.getId());
                    dataRow.createCell(1).setCellValue(dailyReport.getEmployee().getId());
                    dataRow.createCell(2).setCellValue(dailyReport.getEmployee().getName());
                    dataRow.createCell(3).setCellValue(dailyReport.getEmployee().getSurname());
                    dataRow.createCell(4).setCellValue(dailyReport.getEmployee().getEmail());
                    dataRow.createCell(5).setCellValue(dailyReport.getDescription());
                    dataRow.createCell(6).setCellValue(dailyReport.getCreatedAt().toString());
                    dataRow.createCell(7).setCellValue(dailyReport.getProject().getName());

                    dataRowIndex++;
                }
            }
            ServletOutputStream outputStream = response.getOutputStream();
            hssfWorkbook.write(outputStream);
        }
    }
}
