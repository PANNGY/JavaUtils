package my.pan.beanutils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class MyBeanUtils {
	
	public static void main(String[] args) {
		Map<String, Object> personMap = new HashMap<String, Object>();
		personMap.put("name", "xiaoming");
		personMap.put("age", 18);
		personMap.put("male", true);
		personMap.put("salary", 12.34);
		Person person = BeanCreateByBeanUtils(Person.class, personMap);
		System.out.println(person);
	}

	/**
	 * @paramsMap生成实体类
	 * @param clazz
	 * @param paramsMap
	 * @return
	 */
	public static <T> T BeanCreate(Class<T> clazz,Map<String, Object> paramsMap){
		T obj;
		try {
			obj = clazz.newInstance();
		}catch (Exception e) {
			throw new RuntimeException("error:new instance failed!");
		}
		Field []fields = clazz.getDeclaredFields();
		for(Field each:fields){
			//获取属性名
			String fieldname = each.getName();
			//取得属性值
			Object value = paramsMap.get(fieldname);
			if(value==null){
				continue;
			}
			//构造set方法名
			String methodname = "set"+fieldname.substring(0,1).toUpperCase()+fieldname.substring(1);
			Class<?> fieldtype = each.getType();
			Method method;
			try {
				method = clazz.getDeclaredMethod(methodname, fieldtype);
			}catch (Exception e) {
				throw new RuntimeException("error:get the method<"+methodname+"> set function failed!");
			}
			try {
				method.invoke(obj, value);
			}catch (Exception e) {
				throw new RuntimeException("error:do invoke<"+methodname+":"+value+"> error!");
			}		
		}
		return obj;	
	}
	
	/**
	 * @paramsMap生成实体类byBeanUtils
	 * @param clazz
	 * @param paramsMap
	 * @return
	 */
	public static <T> T BeanCreateByBeanUtils(Class<T> clazz,Map<String, Object> paramsMap){
		T obj;
		try {
			obj = clazz.newInstance();
		}catch (Exception e) {
			throw new RuntimeException("error:new instance failed!");
		}
		for(String key:paramsMap.keySet()){
			try {
				BeanUtils.copyProperty(obj, key, paramsMap.get(key));
			} catch (Exception e) {
				new RuntimeException("error: copyProperty<"+key+":"+paramsMap.get(key)+">failed!");
			}
		}
		return obj;		
	}
}
