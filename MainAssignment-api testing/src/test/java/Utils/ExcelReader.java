package Utils;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    public static XSSFSheet ExcelSheetLoader(int SheetNum) throws Exception{
        String path = "src/test/resources/main assignment data.xlsx";
        FileInputStream file = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(SheetNum);
        return sheet;
    }

    public static List GetCreateUserData(int SheetNum) throws Exception{
        XSSFRow row;
        XSSFCell cell;
        XSSFSheet sheet = ExcelSheetLoader(SheetNum);
        List<String> list = new ArrayList<>();
        for (int i = 1; i<= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(1);
//        System.out.println(row.getLastCellNum());
            for (int j = 0; j < row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                if (j == 3) {
                    list.add(Integer.toString((int) cell.getNumericCellValue()));
                    System.out.println((int) cell.getNumericCellValue());
                } else {
                    System.out.println(cell.getStringCellValue());
                    list.add(cell.getStringCellValue());
                }
            }
        }
        return list;
    }

    public static List GetCreateTaskData(int SheetNum) throws Exception{
        XSSFRow row = null;
        XSSFCell cell = null;
        XSSFSheet sheet = ExcelSheetLoader(SheetNum);
        List<String> TaskList = new ArrayList<>();
        for (int i = 1; i<= sheet.getLastRowNum(); i++){
            row = sheet.getRow(i);
            for (int j = 0; j <= row.getLastCellNum(); i++){
                cell = row.getCell(j);
                TaskList.add(cell.getStringCellValue());
            }
        }
        return TaskList;
    }

    public static void TokenWriter() throws Exception{
        String path = "src/test/resources/main assignment data.xlsx";
        File excelfile = new File(path);
        FileInputStream inputStream = new FileInputStream(path);
        FileInputStream file1 = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(file1);
        XSSFSheet sheet = ExcelSheetLoader(0);
        XSSFRow row = null;
        XSSFCell cell = null;
        row = sheet.getRow(0);
        row.createCell(4).setCellValue("token");
        cell = row.getCell(4);
        System.out.println(cell.getStringCellValue());
        row = sheet.getRow(1);
        row.createCell(4).setCellValue("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MjQ5NjRkMzE2YTc0ZTAwMTc0NWZmZGQiLCJpYXQiOjE2NDg5OTIzNjF9.Hxy6Sdlc2fMRKYiHiPZ-K2SBq6JReaJazB465h6hJjg");
        cell = row.getCell(4);
        System.out.println(cell.getStringCellValue());
        inputStream.close();
        FileOutputStream outputStream = new FileOutputStream(excelfile);
        workbook.write(outputStream);
    }

public static void main(String[] args) throws Exception{
//    GetCreateUserData(0);
    TokenWriter();
}

}
