/**
 * 
 */
package com.sword.util.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

/**
 * @author FrainMeng
 *
 */
public class PoiUtils {
	private static DataFormatter formatter = new DataFormatter(); 
	private static final String updateSqlTemplet = "update ecuser_account set backCount=backCount+{0} ,lastModifyDate = ''{1}'' where  ecuserId = {2} and userName = ''{3}'';\n";
	public static <T> List<T> parseExcel(File file,String fileType,Class<T> objectClass) throws Exception {
		if (PoiConstant.TYPE_XLS.equalsIgnoreCase(fileType)) {
			return parseXLS(file, objectClass);
		} else if(PoiConstant.TYPE_XLSX.equalsIgnoreCase(fileType)) {
			return parseXLSX(file, objectClass);
		} else {
			throw new IllegalArgumentException("Illegal File Type");
		}
	}
	
	
	public static <T> List<T> parseXLS(File file,Class<T> objectClass) throws Exception {
		List<T> list = null ;
		HSSFWorkbook workbook = null ;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.rowIterator();
			list = new ArrayList<T>();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				T obj = objectClass.newInstance();
				Field[] fields = objectClass.getDeclaredFields();
				for(Field field : fields){
					PoiCell poiCell = field.getAnnotation(PoiCell.class);
					if (null != poiCell) {
						int cellIndex = poiCell.cellIndex();
						Cell cell = row.getCell(cellIndex);
						field.setAccessible(true);
						field.set(obj, generateData(poiCell.dataType(), cell));
					}
				}
				list.add(obj);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != workbook) {
				workbook.close();
			}
		}
		return list;
	}
	
	public static <T> List<T> parseXLSX(File file,Class<T> objectClass) throws Exception {
		List<T> list = null ;
		XSSFWorkbook workbook = null ;
		Row errorRow = null;
		Cell errorCell = null;
		try {
			list = new ArrayList<T>();
			workbook = new XSSFWorkbook (file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int i1 = sheet.getLastRowNum();
			int i2 = sheet.getPhysicalNumberOfRows();
			
			Iterator<Row> rowIterator = sheet.rowIterator();
			list = new ArrayList<T>();
			rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (isEnd(row)) {
					break;
				}
				errorRow = row;
				T obj = objectClass.newInstance();
				Field[] fields = objectClass.getDeclaredFields();
				for(Field field : fields){
					PoiCell poiCell = field.getAnnotation(PoiCell.class);
					if (null != poiCell) {
						int cellIndex = poiCell.cellIndex();
						Cell cell = row.getCell(cellIndex);
						errorCell = cell;
						field.setAccessible(true);
						field.set(obj, generateData(poiCell.dataType(), cell));
					}
				}
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (null != errorRow) {
				if (errorCell != null) {
					System.out.println("坐标[" + (errorRow.getRowNum()+1)+","+(errorCell.getColumnIndex()+1)+"]单元格内容不正确");
				}
			}
			
			throw e;
		} finally {
			if (null != workbook) {
				workbook.close();
			}
		}
		return list;
	}
	private static Object generateData(DataTypeEnum dateType,Cell cell){
		switch (dateType) {
		case DATE :
			return cell.getDateCellValue();
		case INT :
			return Integer.parseInt(formatter.formatCellValue(cell));
		case DOUBLE :
			return cell.getNumericCellValue();
		default:
			return formatter.formatCellValue(cell);
		}
	}
	
	/**
	 * 检查是否结束
	 * @param row
	 * @return
	 */
	private static boolean isEnd(Row row){
		String value = formatter.formatCellValue(row.getCell(0));
		if (StringUtils.isEmpty(value) || "end".equalsIgnoreCase(value.trim())) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		
		String pathname = "D:\\tmp\\未返利订单最终结果.xlsx";
		String toFile = "D:\\tmp\\updateSql.sql";
		File file = new File(pathname);
		try {
			List<TestModel> list = parseExcel(file, PoiConstant.TYPE_XLSX, TestModel.class);
			generateSqlScript(list, toFile);
			System.out.println("End with success!!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void generateSqlScript(List<TestModel> list,String pathname){
		FileWriter fw = null;
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("(");
			fw = new FileWriter(new File(pathname));
			for (TestModel model:list) {
				
				if(sb.length() == 1) {
					sb.append(model.getUserid());
				} else {
					sb.append(",").append(model.getUserid());
				}
				String sql = MessageFormat.format(updateSqlTemplet, new Object[]{model.getAmount(),formate.format(currentDate),String.valueOf(model.getUserid()),model.getUserName()});
				fw.write(sql);
			}
			fw.flush();
			sb.append(")");
			System.out.println(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != fw) {
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
