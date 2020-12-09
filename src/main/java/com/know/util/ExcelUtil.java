package com.know.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Demo class
 * 
 * @author Test
 * @date 2018/10/31
 */
public class ExcelUtil {
	public static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);	
    private static final String POSITION_TITLE = "title";
    private static final String POSITION_BODY = "body";

//    private static ExcelUtil singleton;

    private ExcelUtil() {
    } // 不能实例化

    // 单例模式,双重锁检查,线程安全
//    public static ExcelUtil getInstance() {
//        if (null == singleton) {
//            synchronized (ExcelUtil.class) {
//                if (null == singleton) {
//                    singleton = new ExcelUtil();
//                }
//            }
//        }
//        return singleton;
//    }

//    list,UserDTO.class,filename,"Sheet",list.size(),request,response
    public static void exportObj2ExcelForSpring(List<?> objs, Class<?> clz, String filename, String sheetName, int pageSize,
                                                HttpServletRequest request, HttpServletResponse response){
    	OutputStream ouputStream = null;
    	try {
    		HSSFWorkbook wb =handleObj2Excel(objs, clz, sheetName, pageSize);
    		// 返回的文件名
    		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			StringBuilder resName = new StringBuilder();
			resName.append(new String(filename.getBytes("gb2312"), "ISO8859-1")).append("");
			//2017-04-07新增判断，模板不加后缀
			String key="模板";
			if(filename.endsWith(key)){
				resName.append(".xls");
			}else{
				resName.append("_").append(time).append(".xls");
			}
			// excel的导出设置(文件名必须去掉空格,否则出现问题)
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + resName.toString().replaceAll("\\s*", ""));
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			// 刷新流
			ouputStream.flush();
		} catch (Exception e) {
			logger.info(e.getMessage());

		} finally {
            closeStream(ouputStream);
        }
    }

    /**
     * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于路径的导出
     * @param objs
     * @param clz
     * @param sheetName
     * @param pageSize
     * @return
     */
    public static InputStream exportObj2Excel(List<?> objs, Class<?> clz, String sheetName, int pageSize) {
    	String key="导出对象【objs】为null！";
        if (objs == null) { throw new NullPointerException(key);}
        ByteArrayOutputStream fos = null;
        try {
            fos = new ByteArrayOutputStream();
            Workbook wb = handleObj2Excel(objs, clz, sheetName, pageSize);
            wb.write(fos);
            byte[] b = fos.toByteArray();
            // 返回输入流,在strusts2导出时可用
            return new ByteArrayInputStream(b);
        } catch (Exception e) {
        	logger.info(e.getMessage());
        } finally {
            closeStream(fos);
        }
        return null;
    }

    /**
     * 关闭流
     * @param stream
     */
    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
            	logger.info(e.getMessage());
            }
        }
    }

    /**
     * 创建导出Excel的对象
     * @param objs
     * @param clz
     * @param sheetName
     * @param pageSize
     * @return
     * @throws Exception
     */
    private static HSSFWorkbook handleObj2Excel(List<?> objs, Class<?> clz, String sheetName, int pageSize) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        // 获取excel标题列表并排序
        List<ExcelHeader> headers = getHeaderList(clz);
        Collections.sort(headers);
        // 导出对象列表,objs已判断,此处不可能为null
        int size = objs.size();
        if (size > 0) {
            int sheetCount = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;
            HSSFSheet sheet;
            // 设置第i个sheet页的数据
            for (int i = 1; i <= sheetCount; i++) {
                // 创建sheet页
                if (!StringUtils.isEmpty(sheetName)) {
                    sheet = wb.createSheet(sheetName + i);
                } else {
                    sheet = wb.createSheet();
                }
                // 设置head的数据格式
                HSSFRow row = sheet.createRow(0);
                CellStyle titleStyle = setCellStyle(wb, POSITION_TITLE);
                for (int m = 0, len = headers.size(); m < len; m++) {
                    HSSFCell head = row.createCell(m);
                    head.setCellStyle(titleStyle);
                    head.setCellValue(headers.get(m).getTitle());
                    // 设置每列的宽度
                    sheet.setColumnWidth(m, 40 * headers.get(m).getWidth()); 
                }
                // 设置body数据部分的格式
                CellStyle bodyStyle = setCellStyle(wb, POSITION_BODY);
                int begin = (i - 1) * pageSize;
                int end = (begin + pageSize) > objs.size() ? objs.size() : (begin + pageSize);
                int rowCount = 1;
                for (int n = begin; n < end; n++) {
                    row = sheet.createRow(rowCount);
                    rowCount++;
                    Object obj = objs.get(n);
                    for (int x = 0, len = headers.size(); x < len; x++) {
                        Cell body = row.createCell(x);
                        body.setCellStyle(bodyStyle);
                        body.setCellValue(BeanUtils.getProperty(obj, headers.get(x).getFieldName()));
                    }
                }
            }
        }
        return wb;
    }

    /**
     * 获取excel标题列表
     * @param clz
     * @return
     */
    public static List<ExcelHeader> getHeaderList(Class<?> clz) {
        List<ExcelHeader> headers =new ArrayList<ExcelHeader>();
        Field[] ms = clz.getDeclaredFields();
        for (Field m : ms) {
            String field = m.getName();
            // 找到注解方法
            if (m.isAnnotationPresent(ExcelResources.class)) {
                ExcelResources er = m.getAnnotation(ExcelResources.class);
                // 标题,序号,宽度与其成员变量
                headers.add(new ExcelHeader(er.title(), er.order(), er.width(), field));
            }
        }
        return headers;
    }

    /**
     * 设置单元格样式
     * @param workBook
     * @param position
     * @return
     */
    static CellStyle setCellStyle(Workbook workBook, String position) {
    	CellStyle cellStyle = workBook.createCellStyle();
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平位置
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直位置
    	// 水平位置
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直位置
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置单元格字体
        Font headerFont = workBook.createFont();
        if (POSITION_TITLE.equals(position)) {
            headerFont.setFontHeightInPoints((short) 12);
//            headerFont.setBoldweight((short) 12);
            headerFont.setBold(true);
        } else {
            headerFont.setFontHeightInPoints((short) 10);
        }
        headerFont.setFontName("宋体");
        // 字体
        cellStyle.setFont(headerFont); 
        // 设置自动换行
        cellStyle.setWrapText(true); 
        // 设置单元格边框及颜色
        cellStyle.setFillBackgroundColor(FillPatternType.THICK_FORWARD_DIAG.getCode());
//        cellStyle.setBorderBottom((short) 1);
//        cellStyle.setBorderLeft((short) 1);
//        cellStyle.setBorderRight((short) 1);
//        cellStyle.setBorderTop((short) 1);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        // 设置边框颜色
        cellStyle.setLeftBorderColor(HSSFColorPredefined.BLACK.getIndex());
        cellStyle.setRightBorderColor(HSSFColorPredefined.BLACK.getIndex());
        cellStyle.setTopBorderColor(HSSFColorPredefined.BLACK.getIndex());
        cellStyle.setBottomBorderColor(HSSFColorPredefined.BLACK.getIndex());
//        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
//        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
//        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
//        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        return cellStyle;
    }
}
