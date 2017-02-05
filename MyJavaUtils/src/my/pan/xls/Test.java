package my.pan.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


public class Test {

	private static final int SHEET_MAX_ROWS = 65535;
	private static final String TOP_BRANCH = "**";
	private static final String filepath = "c:"+File.separator+"data"+File.separator;
	
	public static void main(String[] args){
		
		Thread thread1 = new Thread(new Runnable() {			
			@Override
			public void run() {
				try {
					File file = new File(filepath+"branch.xls");
					if(!file.exists()){
						System.out.println("error: the branch.xls is not exists!");
						System.exit(0);
					}
					File out = new File(filepath+"branch.txt");
					if(!out.exists()){
						out.createNewFile();
					}
					PrintWriter writer = new PrintWriter(out);
					List branch = readExcelDocument(file, Branch.class, new String[]{"brno","name","upbrno"}, 1);
					constraction(branch,writer);
					writer.close();
					System.out.println("success: the sql has created in branch.txt!");
				} catch (Exception e) {
				    e.printStackTrace();
				}			
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {		
			@Override
			public void run() {
				try {
					File user = new File(filepath+"user.xls");
					if(!user.exists()){
						System.out.println("error: the user.xls is not exists!");
					}
					File userout = new File(filepath+"user.txt");
					if(!userout.exists()){
						userout.createNewFile();
					}
					
					PrintWriter writer2 = new PrintWriter(userout);
					List users = readExcelDocument(user, User.class, new String[]{"id","name","brno"}, 1);
		            OutUserSQL(users,writer2);
					writer2.close();
					System.out.println("success: the sql has created in user.txt!");
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}	
		});
		
		thread1.start();
		thread2.start();
			
	}
	
	private static void OutUserSQL(List<User> users, PrintWriter writer2) {
		
		for(User each:users){
			StringBuilder sql = new StringBuilder();
			sql.append("insert into t_oper (f_tellerid,f_tellername,f_pwd,f_roles,f_brno) values('");
			sql.append(each.getId().trim()).append("','");
			sql.append(each.getName().trim()).append("',");
			sql.append("'111111','");
			sql.append("R_ZHO','");
			sql.append(each.getBrno().trim()).append("');");
			writer2.println(sql.toString());	
		}		
		
	}
	
	public static void constraction(List branches,PrintWriter writer) throws InterruptedException{
		Map branchMap = new HashMap<String, Branch>();
		Branch branch = null;
		for(int i=0;i<branches.size();i++){
			branch = (Branch) branches.get(i);
			branchMap.put(branch.getBrno(), branch);
		}
		Map<String,Branch> branchvos= MakeDeptlv.deptlv(branches, branchMap, TOP_BRANCH);
		for(String key:branchvos.keySet()){
			Branch each = branchvos.get(key);
			String sqlString = getSqlString(each);
			writer.println(sqlString);
		}
			
	}
	
	private static String getSqlString(Branch each) {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into t_branch (f_brno,f_name,f_upbrno,f_deptlv,f_level) values('");
		builder.append(each.getBrno()).append("','");
		builder.append(each.getName()).append("','");
		builder.append(each.getUpbrno()).append("','");
		builder.append(each.getDeptlv()).append("','");
	    builder.append(each.getDeptlv().split(",").length).append("');");	
	    return builder.toString();
	}


	public static List readExcelDocument(File destFile, Class clazz, String [] excelTitle
			, int startRow) {
		List instances = new ArrayList();
		
		if (excelTitle == null || excelTitle.length < 1) {
			throw new RuntimeException("Excel表头属性传递错误!");
		}
		
		HSSFWorkbook wb = null;
		FileInputStream fileStream = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		
		if (!destFile.exists()) {
			throw new RuntimeException("Excel文件上传读取失败, 请重新上传!");
		}
		try {
			fileStream = new FileInputStream(destFile);
			wb = new HSSFWorkbook(new POIFSFileSystem(fileStream));
			sheet = wb.getSheetAt(0);
			if (sheet == null) {
				throw new RuntimeException("Excel第一个工作表数据为空, 请重新上传");
			}
			Object instance = null;
			String cellValue = "";
			// EXCEL数据总行数
			int rows = sheet.getLastRowNum();
			int startRowIndex = (startRow < 0 || startRow >= SHEET_MAX_ROWS) 
									? 1 : startRow;
			
			// 行循环
			for (int i = startRowIndex; i <= rows; i++) {
				instance = clazz.newInstance();
				row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				boolean isAllEmpty = true;
				
				// 列循环
				for (int j = 0; j < excelTitle.length; j++) {
					cell = row.getCell(j);
					if(cell != null) {
						// 处理空行,因为rows通常大于实际的有效行数，所有需要判断是否是空行 
						cellValue = POIExcelUtil.getStringCellValue(cell);
						if (! StringUtils.isEmpty(cellValue)) {
							isAllEmpty = false;
						}
					cellValue = (cellValue==null?"":cellValue);
					BeanUtils.setProperty(instance, excelTitle[j], cellValue);
					}
				}
				if (!isAllEmpty) {
					instances.add(instance);
				}
			}		
		} catch (InstantiationException e) {
			throw new RuntimeException("文件读取失败!");
		} catch (IllegalAccessException e) {
			throw new RuntimeException("文件读取失败!");
		} catch (InvocationTargetException e) {
			throw new RuntimeException("文件读取失败!");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("文件读取失败!");
		} catch (IOException e) {
			throw new RuntimeException("文件读取失败!");
		} finally {
            try {   
                if (fileStream != null) {
                	fileStream.close(); 
                }   
            } catch (IOException e) {   
    			throw new RuntimeException(e);
            }   
        } 
		return instances;
	}
}
