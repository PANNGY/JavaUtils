package my.pan.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MyTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Cat cat = new Cat("lili", 2);
		System.out.println(cat);
		File file = new File("c:\\data\\cat.out");
		if(!file.exists()){
			file.createNewFile();
		}
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(cat);
		out.flush();
		out.close();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		Cat readCat = (Cat) in.readObject();
		in.close();
		System.out.println(readCat);
		
	}
}
