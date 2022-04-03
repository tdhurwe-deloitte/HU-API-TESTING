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
        String path = "src/test/resources/user data.xlsx";
        FileInputStream file = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(SheetNum);
        return sheet;
    }

    public static List GetCreateUserData(int SheetNum, int rowNum) throws Exception{
        XSSFRow row;
        XSSFCell cell;
        XSSFSheet sheet = ExcelSheetLoader(SheetNum);
        List<String> list = new ArrayList<>();
        row = sheet.getRow(rowNum);
//        System.out.println(row.getLastCellNum());
            for (int j = 0; j < row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                if (j == 3) {
                    list.add(Integer.toString((int) cell.getNumericCellValue()));
//                    System.out.println((int) cell.getNumericCellValue());
                } else {
//                    System.out.println(cell.getStringCellValue());
                    list.add(cell.getStringCellValue());
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

    public static void TokenWriter(String tokenData) throws Exception{
        String path = "src/test/resources/user data.xlsx";
        FileInputStream fileInputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = null;
        XSSFCell cell = null;
        row = sheet.getRow(1);
        cell = row.createCell(4);
        cell.setCellValue(tokenData);
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        workbook.write(fileOutputStream);
        fileOutputStream.flush();
    }
    public static void IDWriter(String ID) throws Exception{
        String path = "src/test/resources/user data.xlsx";
        FileInputStream fileInputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = null;
        XSSFCell cell = null;
        row = sheet.getRow(1);
        cell = row.createCell(5);
        cell.setCellValue(ID);
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        workbook.write(fileOutputStream);
        fileOutputStream.flush();
    }

//public static void main(String[] args) throws Exception{
////    GetCreateUserData(0);
////    TokenWriter();
//}

}
