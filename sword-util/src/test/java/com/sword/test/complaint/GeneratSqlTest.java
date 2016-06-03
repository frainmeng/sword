/**
 * 
 */
package com.sword.test.complaint;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import com.sword.util.poi.PoiConstant;
import com.sword.util.poi.PoiUtils;

/**
 * @author mengfanyuan
 *
 */
public class GeneratSqlTest {
	private final static String sqlTmp = "update pop_vender set BC_COMPLAINT_MIND_MOBILE=''{0}'' where VENDER_ID = {1};";
	
	
	public static void main(String[] args) {
		File file = new File("C:\\Users\\mengfanyuan\\Desktop\\work_files\\projects\\20160520_投诉工单日志查询接口\\工单流转提醒信息.xlsx");
		List<DataModel4Complaint> list = null;
		try {
			list = PoiUtils.parseExcel(file, PoiConstant.TYPE_XLSX, DataModel4Complaint.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (DataModel4Complaint model : list) {
			String sqlLine = MessageFormat.format(sqlTmp, model.getMobile().trim().trim(), model.getVenderId());
			System.out.println(sqlLine);
		}
	}
}
