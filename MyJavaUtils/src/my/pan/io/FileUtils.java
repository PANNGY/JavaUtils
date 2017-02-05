package my.pan.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.zip.CRC32;

/**
 * <b>java nio(jdk>=1.7)</b>
 * @author Pan
 *
 */
public class FileUtils {
	
	/**
	 *  <b>读取文件至字符串</b>
	 */
	public static String file_read_all(String filepath,String charset) throws IOException{
		Path path = Paths.get(filepath);
		byte [] bytes = Files.readAllBytes(path);
		return new String(bytes, charset);
	}	
	/**
	 *  <b>读取文件至字符串数组</b>
	 */
	public static List<String> file_read_lines(String filepath,String charset) throws IOException{
		Path path = Paths.get(filepath);
		List<String> strs = Files.readAllLines(path, Charset.forName(charset));
		return strs;
	}	
	/**
	 *  <b>指定文件追加内容</b>
	 */
	public static void file_add(String filepath,String content,String charset) throws IOException{
		Path path = Paths.get(filepath);
		Files.write(path, content.getBytes(Charset.forName(charset)), StandardOpenOption.APPEND);
	}
	/**
	 *  <b>文件移动</b>
	 */
	public static void file_move(String from,String to) throws IOException{
		Path from_path = Paths.get(from);
		Path to_path = Paths.get(to);
		Files.move(from_path, to_path, StandardCopyOption.ATOMIC_MOVE);
	}
	/**
	 *  <b>文件复制</b>
	 */
	public static void file_copy(String from,String to,boolean replace) throws IOException{
		Path from_path = Paths.get(from);
		Path to_path = Paths.get(to);
		if(replace)
			Files.copy(from_path, to_path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
		else
		    Files.copy(from_path, to_path, StandardCopyOption.COPY_ATTRIBUTES);
	}
	/**
	 *  <b>文件删除</b>
	 * 
	 */
	public static void file_delete(String filepath) throws IOException{
		Path path = Paths.get(filepath);
        Files.delete(path);
	}
	/**
	 *  <b>获取文件CRC32值</b>
	 * 
	 */
	public static long crc_check(String filepath) throws IOException{
		Path path = Paths.get(filepath);
		InputStream in = Files.newInputStream(path);
		CRC32 crc = new CRC32();
		byte [] b = new byte[512];
		int len = 0;
		while((len=in.read(b))!=-1){
			crc.update(b,0,len);
		}
		return crc.getValue();
		
	}
	
	

	public static void main(String[] args) {
		
		try {
		//	List<String> str = file_read_lines("C:\\test\\my.txt", "utf-8");
			long value = crc_check("C:\\test\\my.txt");
			System.out.println(value);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
