/**
 * 
 */
package com.sword.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sword.util.json.JsonUtils;
import com.sword.util.poi.PoiConstant;
import com.sword.util.poi.PoiUtils;
import com.sword.util.poi.TestAccount;
import com.sword.util.poi.TestModel;

/**
 * @author mengfanyuan
 *
 */
public class Main {
	private static SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat dateFormateSimple = new SimpleDateFormat("yyyy-MM-dd");
	private static final String insertSqlTemplet = "INSERT INTO ecuser_account_record (accountId,changeDate,acountChange,backCountChange,remark,operator,typeName,typeId,ecuserId,applyTime,freeAccountChange,freeBackCountChange,userName,beforeAccount,afterAccount,beforeBackCount,afterBackCount,orderId) VALUES({0},''{1}'',0,10,''{2}'',''{3}'',''移动端首单返利'',15,{4},''{5}'',0,0,''{6}'',{7},{8},{9},{10},''{11}'');\n";
	
	private static final String updateSqlTemplet = "update ecuser_account set backCount=backCount+{0} ,lastModifyDate = ''{1}'' where  ecuserId = {2} and userName = ''{3}'';\n";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String changePathname = "D:\\tmp\\未返利订单最终结果.xlsx";
		String accountPathname = "D:\\tmp\\test.json";
		String toFile = "D:\\tmp\\updateSql.sql";
		List<TestModel> changeList = getChangeInfo(changePathname);
		Map<String, TestAccount> accountMap = change2Map(getAccountInfo(accountPathname));
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(toFile));
			for ( TestModel changeInfo : changeList) {
				String updateSql = generateupdateSql(changeInfo);
				String insertSql = generateInsertSql(changeInfo, accountMap.get(String.valueOf(changeInfo.getUserid())));
				fw.write(updateSql);
				fw.write(insertSql);
			}
			fw.flush();
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
		
		System.out.println("End with success!!!!");
		/*String changePathname = "D:\\tmp\\未返利订单最终结果.xlsx";
		String accountPathname = "D:\\tmp\\test.json";
		String toFile = "D:\\tmp\\updateSql.sql";
		File file = new File(changePathname);
		try {
			List<TestModel> list = PoiUtils.parseExcel(file, PoiConstant.TYPE_XLSX, TestModel.class);
			PoiUtils.generateSqlScript(list, toFile);
			System.out.println("End with success!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	
	
	private static List<TestAccount> getAccountInfo(String pathname) {
		List<TestAccount> list = null;
		File file = new File(pathname);
		BufferedReader br = null;
		try {
			StringBuilder sb = new StringBuilder();
			br = new BufferedReader(new FileReader(file));
			String lineStr =null;
			while ((lineStr = br.readLine()) != null) {
				sb.append(lineStr);
			}
			list = JsonUtils.json2ObjectList(sb.toString(), TestAccount.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	private static List<TestModel> getChangeInfo (String pathname) {
		List<TestModel> list = null;
		File file = new File(pathname);
		 try {
			 list = PoiUtils.parseExcel(file, PoiConstant.TYPE_XLSX, TestModel.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	private static Map<String, TestAccount> change2Map(List<TestAccount> list) {
		Map<String, TestAccount> map = new HashMap<String, TestAccount>();
		for (TestAccount account : list) {
			map.put(String.valueOf(account.getEcuserId()), account);
		}
		return map;
	}
	
	private static String generateInsertSql(TestModel change,TestAccount account){
		String currentDateStr = dateFormate.format(new Date());
		String[] arguments = new String[12];
		arguments[0] = String.valueOf(account.getAccountId());
		arguments[1] = String.valueOf(currentDateStr);
		arguments[2] = String.valueOf("移动端首单返利,父订单:"+change.getOrderId()+",返利金额:10.0");
		arguments[3] = String.valueOf(change.getUserName());
		arguments[4] = String.valueOf(change.getUserid());
		arguments[5] = String.valueOf(currentDateStr);
		arguments[6] = String.valueOf(change.getUserName());
		arguments[7] = String.valueOf(account.getAccount());
		arguments[8] = String.valueOf(account.getAccount());
		arguments[9] = String.valueOf(account.getBackCount());
		arguments[10] = String.valueOf(account.getBackCount()+10);
		arguments[11] = String.valueOf(change.getOrderId());
		return MessageFormat.format(insertSqlTemplet, arguments);
	}
	
	private static String generateupdateSql(TestModel change){
		String currentDateStr = dateFormateSimple.format(new Date());
		return MessageFormat.format(updateSqlTemplet, new Object[]{change.getAmount(),currentDateStr,String.valueOf(change.getUserid()),change.getUserName()});
	}
	
	
}
