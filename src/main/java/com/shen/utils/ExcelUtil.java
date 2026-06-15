package com.shen.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.shen.config.SavePathConstant;
import com.shen.entity.Airport;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName ExcelUtil.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年10月18日 21:20:00
 */
public class ExcelUtil {

    private static final String[] TITLES = { "地区", "名称", "地址", "等级", "IATA", "ICAO", "机场类型", "经度", "纬度", "海拔高度", "跑道数量" };

    public static void ExportExcel(List<Airport> list) {
        try (SXSSFWorkbook wb = new SXSSFWorkbook(100)) {
            Sheet sheet = wb.createSheet();
            Row row = sheet.createRow(0);
            // 给单元格设置样式
            CellStyle cellStyle = wb.createCellStyle();
            Font font = wb.createFont();
            // 设置字体大小
            font.setFontHeightInPoints((short) 12);
            // 设置字体加粗
            font.setBold(true);
            // 给字体设置样式
            cellStyle.setFont(font);
            // 设置单元格背景颜色
            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            // 设置单元格填充样式(使用纯色背景颜色填充)
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            for (int i = 0; i < TITLES.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(TITLES[i]);
                cell.setCellStyle(cellStyle);
                // 设置列的宽度
                sheet.setColumnWidth(i, 200 * 50);
            }
            for (int j = 0; j < list.size(); j++) {
                Row rowData = sheet.createRow(j + 1);
                Airport airport = list.get(j);
                Cell area = rowData.createCell(0);
                area.setCellValue(airport.getArea());
                Cell name = rowData.createCell(1);
                name.setCellValue(airport.getName());
                Cell address = rowData.createCell(2);
                address.setCellValue(airport.getAddress());
                Cell level = rowData.createCell(3);
                level.setCellValue(airport.getLevel());
                Cell iata = rowData.createCell(4);
                iata.setCellValue(airport.getIata());
                Cell icao = rowData.createCell(5);
                icao.setCellValue(airport.getIcao());
                Cell type = rowData.createCell(6);
                type.setCellValue(airport.getType());
                Cell longitude = rowData.createCell(7);
                longitude.setCellValue(airport.getLongitude());
                Cell latitude = rowData.createCell(8);
                latitude.setCellValue(airport.getLatitude());
                Cell altitude = rowData.createCell(9);
                altitude.setCellValue(airport.getAltitude());
                Cell runway = rowData.createCell(10);
                runway.setCellValue(airport.getRunway());
            }

            ensureOutputDirectory();
            String fileName = "/中国民用机场名单.xlsx";
            try (FileOutputStream fileOutputStream = new FileOutputStream(SavePathConstant.OUTPUT + fileName)) {
                wb.write(fileOutputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void EasyExport(List<Airport> list) throws IOException {
        ensureOutputDirectory();
        try (Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("中国机场", null, "运输机场"),
                Airport.class, list);
             FileOutputStream fos = new FileOutputStream(SavePathConstant.OUTPUT + "/中国机场.xls")) {
            workbook.write(fos);
        }
    }

    private static void ensureOutputDirectory() {
        File savefile = new File(SavePathConstant.OUTPUT);
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
    }

}
