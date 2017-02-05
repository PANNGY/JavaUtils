package my.pan.serializable;

import java.io.Serializable;

public class Cat implements Serializable{

	private static final long serialVersionUID = -8413939592860637473L;
	
	//transient 关键字修饰的变量值不会被序列化
	private transient String name;
	private int age;

	public Cat(String name,int age){
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Cat [name=" + name + ", age=" + age + "]";
	}
		

}
