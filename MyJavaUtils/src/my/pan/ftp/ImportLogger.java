package my.pan.ftp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ImportLogger {

	public static String logpath = "/home/app"; 
	public static void writeLog(String message){
		try {
			File file = new File(logpath+File.separator+"log.txt");
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file,true);
			BufferedWriter bWriter = new BufferedWriter(fileWriter);
			PrintWriter printWriter = new PrintWriter(bWriter);
			printWriter.println(message);
			printWriter.flush();
			bWriter.flush();	
			printWriter.close();
			bWriter.close();
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
