package org.acme.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.acme.Util.DatabaseUtil;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExportService {
    public XSSFWorkbook generateExcelWorkbook() throws SQLException {
        Connection connection = DatabaseUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.kebun");
        ResultSet resultSet = preparedStatement.executeQuery();

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("kebun");

        // Membuat style untuk header tabel
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setBorderTop(BorderStyle.MEDIUM);
        headerStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerStyle.setBorderRight(BorderStyle.MEDIUM);

        // Membuat style untuk sel tabel
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        int rownum = 0;
        Row headerRow = sheet.createRow(rownum++);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nama Barang");
        headerRow.createCell(2).setCellValue("Total");
        headerRow.createCell(3).setCellValue("CreateAt");
        headerRow.createCell(4).setCellValue("UpdateAt");

        // Menerapkan style pada sel header tabel
        for (Cell cell : headerRow) {
            cell.setCellStyle(headerStyle);
        }

        // Atur format tanggal pada sel Excel
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));

        while (resultSet.next()) {
            Row row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(resultSet.getString("id"));
            row.createCell(1).setCellValue(resultSet.getString("namaBarang"));
            row.createCell(2).setCellValue(resultSet.getInt("total"));

            // Konversi nilai seri tanggal ke LocalDate
            LocalDate createAt = resultSet.getDate("createAt").toLocalDate();
            LocalDate updateAt = resultSet.getDate("updateAt").toLocalDate();

            // Tulis tanggal ke dalam sel Excel dengan format tanggal
            Cell createAtCell = row.createCell(3);
            createAtCell.setCellValue(createAt);
            createAtCell.setCellStyle(dateCellStyle);

            Cell updateAtCell = row.createCell(4);
            updateAtCell.setCellValue(updateAt);
            updateAtCell.setCellStyle(dateCellStyle);
        }

        return workbook;
    }
}
