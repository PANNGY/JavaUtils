package my.pan.io;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import oracle.sql.CHAR;

public class JavaIOUtils {

	public static String utils_ByteArrayOutputStream(InputStream in,String charset) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte [] b = new byte[512];
		int used = 0;
		while((used = in.read(b))!=-1){
			out.write(b, 0, used);
		}
		out.close();
		return new String(out.toByteArray(), charset);		
	}
	
	
	
	public static void main(String[] args) {
		InputStream in = JavaIOUtils.class.getResourceAsStream("/my/pan/log4j/log4j.xml");
		try {
			String string = JavaIOUtils.utils_ByteArrayOutputStream(in, "GB2312");
			System.out.println(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		try {
//			FileReader reader = new FileReader("C:\\Users\\PanJunjie\\Downloads\\Workspaces\\MyEclipse 2015\\MyJavaUtils\\src\\my\\pan\\log4j\\log4j.xml");
//			PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out, "GB2312"));
//			char [] buffer = new char[50];
//			int used = 0;
//			while((used = reader.read(buffer))!=-1){
//				writer.write(buffer, 0, used);
//			}
//			reader.close();
//			writer.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
}
