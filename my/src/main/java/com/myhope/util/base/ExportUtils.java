//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.myhope.util.base;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_UPLOAD = "yyyyMMddHHmmss";

    public ExportUtils() {
    }

    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if(c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception var7) {
                    var7.printStackTrace();
                    b = new byte[0];
                }

                for(int j = 0; j < b.length; ++j) {
                    int k = b[j];
                    if(k < 0) {
                        k += 256;
                    }

                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }

        return sb.toString();
    }

    public static HSSFWorkbook createHSSWorkbook() {
        return new HSSFWorkbook();
    }

    public static HSSFSheet createHSSFSheet(String title, HSSFWorkbook workbook) {
        return workbook.createSheet(title);
    }

    public static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, HSSFSheet sheet, String[] head) {
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment((short)2);
        style.setFillForegroundColor((short)9);
        style.setFillPattern((short)1);
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderTop((short)1);
        style.setBorderRight((short)1);

        for(int i = 0; i < head.length; ++i) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(head[i]);
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, 3766);
        }

        return style;
    }

    public static void writeModel(HSSFWorkbook workbook, HttpServletResponse response) throws Exception {
        ServletOutputStream out = null;

        try {
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception var6) {
            var6.printStackTrace();
            if(out != null) {
                try {
                    out.close();
                } catch (IOException var5) {
                    var5.printStackTrace();
                }
            }
        }

    }

    public static int createHSSFCell(HSSFRow row, String value, HSSFCellStyle style, int m) {
        HSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createHSSFCell(HSSFRow row, boolean value, HSSFCellStyle style, int m) {
        HSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createHSSFCell(HSSFRow row, double value, HSSFCellStyle style, int m) {
        HSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createHSSFCell(HSSFRow row, Date value, HSSFCellStyle style, int m) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        if(null == value) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(formatter.format(value));
        }

        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createHSSFCell(HSSFRow row, Calendar value, HSSFCellStyle style, int m) {
        HSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createHSSFCell(HSSFRow row, RichTextString value, HSSFCellStyle style, int m) {
        HSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createHSSFCell(HSSFRow row, Long value, HSSFCellStyle style, int m) {
        HSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        if(null == value) {
            cell.setCellValue("");
        } else {
            cell.setCellValue((double)value.longValue());
        }

        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createHSSFCell(HSSFRow row, BigDecimal value, HSSFCellStyle style, int m) {
        HSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        if(null == value) {
            cell.setCellValue(0.0D);
        } else {
            cell.setCellValue(value.doubleValue());
        }

        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createHSSFCell(HSSFRow row, Integer value, HSSFCellStyle style, int m) {
        HSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        if(null == value) {
            cell.setCellValue(0.0D);
        } else {
            cell.setCellValue(value.doubleValue());
        }

        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static XSSFWorkbook createXSSWorkbook() {
        return new XSSFWorkbook();
    }

    public static SXSSFWorkbook createSXSSWorkbook(int count) {
        return new SXSSFWorkbook(count);
    }

    public static XSSFSheet createXSSFSheet(String title, XSSFWorkbook workbook) {
        return workbook.createSheet(title);
    }

    public static Sheet createSXSSFSheet(String title, SXSSFWorkbook workbook) {
        return workbook.createSheet(title);
    }

    public static XSSFCellStyle createCellStyle(XSSFWorkbook workbook, XSSFSheet sheet, String[] head) {
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment((short)2);
        style.setFillForegroundColor((short)9);
        style.setFillPattern((short)1);
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderTop((short)1);
        style.setBorderRight((short)1);

        for(int i = 0; i < head.length; ++i) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(head[i]);
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, 3766);
        }

        return style;
    }

    public static CellStyle createCellStyle(SXSSFWorkbook workbook, Sheet sheet, String[] head) {
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        style.setAlignment((short)2);
        style.setFillForegroundColor((short)9);
        style.setFillPattern((short)1);
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderTop((short)1);
        style.setBorderRight((short)1);

        for(int i = 0; i < head.length; ++i) {
            Cell cell = row.createCell(i);
            cell.setCellValue(head[i]);
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, 3766);
        }

        return style;
    }

    public static void writeModel(XSSFWorkbook workbook, HttpServletResponse response) throws Exception {
        ServletOutputStream out = null;

        try {
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception var6) {
            var6.printStackTrace();
            if(out != null) {
                try {
                    out.close();
                } catch (IOException var5) {
                    var5.printStackTrace();
                }
            }
        }

    }

    public static void writeModel(SXSSFWorkbook workbook, HttpServletResponse response) throws Exception {
        ServletOutputStream out = null;

        try {
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception var6) {
            var6.printStackTrace();
            if(out != null) {
                try {
                    out.close();
                } catch (IOException var5) {
                    var5.printStackTrace();
                }
            }
        }

    }

    public static int createXSSFCell(XSSFRow row, String value, XSSFCellStyle style, int m) {
        XSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createXSSFCell(XSSFRow row, boolean value, XSSFCellStyle style, int m) {
        XSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createXSSFCell(XSSFRow row, double value, XSSFCellStyle style, int m) {
        XSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createXSSFCell(XSSFRow row, Date value, XSSFCellStyle style, int m) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        XSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        if(null == value) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(formatter.format(value));
        }

        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createXSSFCell(XSSFRow row, Calendar value, XSSFCellStyle style, int m) {
        XSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createXSSFCell(XSSFRow row, RichTextString value, XSSFCellStyle style, int m) {
        XSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createXSSFCell(XSSFRow row, Long value, XSSFCellStyle style, int m) {
        XSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        if(null == value) {
            cell.setCellValue("");
        } else {
            cell.setCellValue((double)value.longValue());
        }

        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createXSSFCell(XSSFRow row, BigDecimal value, XSSFCellStyle style, int m) {
        XSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        if(null == value) {
            cell.setCellValue(0.0D);
        } else {
            cell.setCellValue(value.doubleValue());
        }

        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createXSSFCell(XSSFRow row, Integer value, XSSFCellStyle style, int m) {
        XSSFCell cell = row.createCell(m);
        cell.setCellType(1);
        if(null == value) {
            cell.setCellValue(0.0D);
        } else {
            cell.setCellValue(value.doubleValue());
        }

        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createCell(Row row, String value, CellStyle style, int m) {
        Cell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createCell(Row row, boolean value, CellStyle style, int m) {
        Cell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createCell(Row row, double value, CellStyle style, int m) {
        Cell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createCell(Row row, Date value, CellStyle style, int m, String DateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(DateFormat);
        Cell cell = row.createCell(m);
        cell.setCellType(1);
        if(null == value) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(formatter.format(value));
        }

        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createCell(Row row, Date value, CellStyle style, int m) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Cell cell = row.createCell(m);
        cell.setCellType(1);
        if(null == value) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(formatter.format(value));
        }

        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createCell(Row row, Calendar value, CellStyle style, int m) {
        Cell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createCell(Row row, RichTextString value, CellStyle style, int m) {
        Cell cell = row.createCell(m);
        cell.setCellType(1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createCell(Row row, Long value, CellStyle style, int m) {
        Cell cell = row.createCell(m);
        cell.setCellType(1);
        if(null == value) {
            cell.setCellValue("");
        } else {
            cell.setCellValue((double)value.longValue());
        }

        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createCell(Row row, BigDecimal value, CellStyle style, int m) {
        Cell cell = row.createCell(m);
        cell.setCellType(1);
        if(null == value) {
            cell.setCellValue(0.0D);
        } else {
            cell.setCellValue(value.doubleValue());
        }

        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static int createCell(Row row, Integer value, CellStyle style, int m) {
        Cell cell = row.createCell(m);
        cell.setCellType(1);
        if(null == value) {
            cell.setCellValue(0.0D);
        } else {
            cell.setCellValue(value.doubleValue());
        }

        cell.setCellStyle(style);
        ++m;
        return m;
    }

    public static String getTodayStr() throws Exception {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(today);
    }

    public static boolean isExportCountEmpty(Integer count) {
        return count == null || count.intValue() == 0;
    }
}
