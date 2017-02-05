package my.pan.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUtils {
	
	public static void main(String[] args) throws ParseException  {
		    String config_properties = null;
		    if(args.length!=1){
			   config_properties = "/home/app/data";
		    }else{
			   config_properties = args[0];
		    }
			Properties properties = new Properties();
			InputStream in;
			try {
			      in = new FileInputStream(config_properties+File.separator+"config.properties");
    			  properties.load(in);
			} catch (Exception e) {
				//ImportLogger.writeLog("load config.properties failed !");
			}
			String username = properties.getProperty("ftp.username");
			String password = properties.getProperty("ftp.password");
			String ftpHostName = properties.getProperty("ftp.ip");
			String remotePath = properties.getProperty("ftp.remotepath");
			String localPath = properties.getProperty("ftp.localpath");
			String logPath = properties.getProperty("ftp.logpath");
			ImportLogger.logpath = logPath;
		    downFile(ftpHostName, 21, username, password, remotePath, localPath);
				
	}

	public static boolean downFile(String url, int port, String username,
			String password, String remotePath, String localPath) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		String date = DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");
		try {
			int reply;
			ftp.connect(url, port);
			ftp.login(username, password);
			ImportLogger.writeLog(date+":------------------- ftp login ---------------");
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			
			downFileOrDir(remotePath, localPath, ftp);

			ftp.logout();
			ImportLogger.writeLog(date+":------------------- ftp logout ---------------");
			success = true;
		} catch (IOException e) {
			ImportLogger.writeLog(date+":------------------- ftp failed ---------------");
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
	
	private  static void downFileOrDir(String ftpFileName, String localDir,FTPClient ftpClient) {
		try {
			File file = new File(ftpFileName);

			File temp = new File(localDir);

			OutputStream fos = null;
			
			if (!temp.exists()) {
				temp.mkdirs();
			}
			// 判断是否是目录
			if (isDir(ftpFileName,ftpClient)) {
				String[] names = ftpClient.listNames();
				for (int i = 0; i < names.length; i++) {
					if (isDir(names[i],ftpClient)) {
						downFileOrDir(ftpFileName + "/" + names[i], localDir
								+ File.separator + names[i],ftpClient);
					} else {
						File localfile = new File(localDir + File.separator
								+ names[i]);
						if (!localfile.exists()) {
							fos = new FileOutputStream(localfile);
							ftpClient.retrieveFile(names[i], fos);
							fos.flush();
							fos.close();
							ImportLogger.writeLog("......... "+names[i]+" download..............");

						} else {
							file.delete();
							fos = new FileOutputStream(localfile);
							ftpClient.retrieveFile(ftpFileName, fos);
							fos.flush();
							fos.close();
							ImportLogger.writeLog("......... "+names[i]+" download..............");

						}
					}
					
				}
				ftpClient.changeToParentDirectory();
			} else {
				
				File localfile = new File(localDir + File.separator
						+ file.getName());
				if (!localfile.exists()) {
				    fos = new FileOutputStream(localfile);
					ftpClient.retrieveFile(ftpFileName, fos);
					fos.flush();
					fos.close();
					ImportLogger.writeLog("......... "+ftpFileName+" download..............");

				} else {
					file.delete();
					fos = new FileOutputStream(localfile);
					ftpClient.retrieveFile(ftpFileName, fos);
					fos.flush();
					fos.close();
					ImportLogger.writeLog("......... "+ftpFileName+" download..............");
				}
			}

		} catch (SocketException e) {
			ImportLogger.writeLog("......... "+ftpFileName+" download failed ..............");
		} catch (IOException e) {
			ImportLogger.writeLog("......... "+ftpFileName+" download failed ..............");
		}

	}
	
	// 判断是否是目录
		public static boolean isDir(String fileName,FTPClient ftpClient) {
			try {
				// 切换目录，若当前是目录则返回true,否则返回true。
				boolean falg = ftpClient.changeWorkingDirectory(fileName);
				return falg;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return false;
		}
	
}
