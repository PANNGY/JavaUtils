package my.pan.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import my.pan.beanutils.Person;

public class ClassLoaderTest {

	public static void main(String[] args) throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {
		ClassLoader loader = ClassLoaderTest.class.getClassLoader();

			Class a = loader.loadClass("my.pan.classloader.A");
			System.out.println("----------------------------");
			Class.forName("my.pan.classloader.A");
			System.out.println("----------------------------");
			Class.forName("my.pan.classloader.A", false,loader);
			
			System.out.println(ClassLoaderTest.class.getClass().getResource(""));
	}
}
