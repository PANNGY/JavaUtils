package my.pan.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import my.pan.beanutils.Person;


public class MyClassLoader extends ClassLoader{

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte [] result = null;
		try {
			InputStream in = new FileInputStream(name);
			byte [] buffer = new byte[1024];
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int len = 0;
			while((len=in.read(buffer))!=-1){
				out.write(buffer, 0, len);
			}
			in.close();
			out.close();
			result = out.toByteArray();
		} catch (Exception e) {	
			e.printStackTrace();
		} 
		Class clazz = defineClass("my.pan.beanutils.Person", result, 0, result.length);
		return clazz;
	}


	public static void main(String[] args) throws Exception {
		Class clazz = new MyClassLoader().findClass("C:\\Users\\PanJunjie\\Downloads\\Workspaces\\MyEclipse 2015\\MyJavaUtils\\bin\\my\\pan\\beanutils\\Person.class");
	    Object object = clazz.newInstance();
	    System.out.println((Person)object);
	}
}
