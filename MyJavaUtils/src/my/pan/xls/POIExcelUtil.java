package my.pan.xls;

import java.util.Date;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

/**
 * POI读取EXCEL的工具类
 * @version 2010-06-01
 * @author yxq
 */
public class POIExcelUtil {
	
	/**  
     * 以字符串方式读取, 用Math.abs(value - (long)value)<EXP 来判断是整数还是浮点数  
     */  
    public static final double EXP = 0.0000000000000001; 
	
	/**
	 * 获取单元格数据内容为字符串类型的数据  
	 * @param cell EXCEL单元格对象
	 * @return
	 */
	public static String getStringCellValue(HSSFCell cell) { 
		if (cell == null) {   
			return "";   
	    }   
        String retValue = "";   
        int type = cell.getCellType();
        switch (type) {   
	        case HSSFCell.CELL_TYPE_STRING:                     //类型为字符型
	            retValue = cell.getRichStringCellValue().getString();   
	            break;   
	        case HSSFCell.CELL_TYPE_NUMERIC:                    //类型为数值型
        	   	if (HSSFDateUtil.isCellDateFormatted(cell)) {
	        		Date date = cell.getDateCellValue();
	        		retValue = DateFormatUtils.format(date, "yyyyMMdd");
	        	} else {                                          //真正的数值型
	        		double cellnum = cell.getNumericCellValue();
	        		if (Math.abs(cellnum - (long)cellnum) < EXP) {//相差很小的话说明是整数
	        			retValue = String.valueOf((long)cellnum);
	        		} else {
	        			retValue = String.valueOf(cellnum);
	        		}
	        	}
	            break;   
	        case HSSFCell.CELL_TYPE_BOOLEAN:                 //类型为布尔型
	            retValue = String.valueOf(cell.getBooleanCellValue());   
	            break;   
	        case HSSFCell.CELL_TYPE_BLANK:                   //为空
	            retValue = "";   
	            break; 
	        case HSSFCell.CELL_TYPE_ERROR:                  //类型错误
	        	retValue = "";
	        	break;
	        case HSSFCell.CELL_TYPE_FORMULA:                //类型为公式
	        	retValue = String.valueOf(cell.getNumericCellValue());
	        	break;
	        default:                                  
	            retValue = "";   
	            break;   
        }   
       // return retStr.replaceAll("\\s", ""); 去空格
        return retValue.trim();   
    }   
	
	/**
	 * 获取单元格数据内容为日期类型的数据  
	 * @param cell Excel单元格
	 * @param format 需要转换成的日期格式
	 * @return
	 */
	public String getDateCellValue(HSSFCell cell, String format) {
        String retValue = "";
		int cellType = cell.getCellType();   
		if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				 Date date = cell.getDateCellValue();   
				 retValue = DateFormatUtils.format(date, format);
			}
		} else if (cellType == HSSFCell.CELL_TYPE_STRING) {   
		    String date = getStringCellValue(cell);   
		    retValue = date.replaceAll("[年月]", "-").replace("日", "").trim();   
		}
        
        return retValue;   
    }   
	
	/**
	 * 获取数值
	 * @param cell
	 * @return
	 */
	public double getDoubleCellValue(HSSFCell cell) {
		double retValue = 0.00;
		int cellType = cell.getCellType();   
		if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
			retValue = cell.getNumericCellValue();
		}
		return retValue;
	}
	
}
