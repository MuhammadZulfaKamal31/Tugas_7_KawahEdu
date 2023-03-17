package org.acme.Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.Service.ExcelExportService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Path("/export-excel")
public class ExportExcelController {

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response exportExcel() {
        try {
            ExcelExportService excelExportService = new ExcelExportService();
            XSSFWorkbook workbook = excelExportService.generateExcelWorkbook();

            FileOutputStream outputStream = new FileOutputStream("MyFirstExcel.xlsx");
            workbook.write(outputStream);
            outputStream.close();

            Response.ResponseBuilder response = Response.ok((Object) new FileInputStream("MyFirstExcel.xlsx"));
            response.header("Content-Disposition", "attachment; filename=MyFirstExcel.xlsx");

            return response.build();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
