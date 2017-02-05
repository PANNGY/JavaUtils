package my.pan.classloader;

public class A {

	static{
		System.out.println("do static!");
	}
	public A(){
		System.out.println("do init");
	}
}
