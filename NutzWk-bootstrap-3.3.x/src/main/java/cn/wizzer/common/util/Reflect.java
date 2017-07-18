package cn.wizzer.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;

public class Reflect {
	private static final Log log = Logs.get();
	
	public static NutMap reflect(Object model,NutMap conditionMap){
		NutMap nutMap = new NutMap();
		try{
			Field[] field = model.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组  
	        for(int j=0 ; j<field.length ; j++){     //遍历所有属性
	                String fieldName = field[j].getName();    //获取属性的名字
	                String name = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1); //将属性的首字符大写，方便构造get，set方法
	                String type = field[j].getGenericType().toString();    //获取属性的类型
	                if(type.equals("class java.lang.String")){   //如果type是类类型，则前面包含"class "，后面跟类名
	                    Method m = model.getClass().getMethod("get"+name);
	                    String value = (String) m.invoke(model);    //调用getter方法获取属性值
	                    if(!Strings.isEmpty(value)){
	                    	if(conditionMap.containsKey(fieldName)){
	                    		StringBuffer sb = new StringBuffer(conditionMap.getString(fieldName));
	                    		sb.append("~");
	                    		sb.append(fieldName);
	                    		nutMap.put(sb.toString(), value);
	                    	}else{
	                    		nutMap.put(fieldName, value);
	                    	}
	                    }
	                }
	                if(type.equals("class java.lang.Integer")){     
	                    Method m = model.getClass().getMethod("get"+name);
	                    Integer value = (Integer) m.invoke(model);
	                    if(value != null){
	                    	if(conditionMap.containsKey(fieldName)){
	                    		StringBuffer sb = new StringBuffer(conditionMap.getString(fieldName));
	                    		sb.append("~");
	                    		sb.append(fieldName);
	                    		nutMap.put(sb.toString(), value);
	                    	}else{
	                    		nutMap.put(fieldName, value);
	                    	}
	                    }
	                }
	                if(type.equals("class java.lang.Short")){     
	                    Method m = model.getClass().getMethod("get"+name);
	                    Short value = (Short) m.invoke(model);
	                    if(value != null){
	                    	if(conditionMap.containsKey(fieldName)){
	                    		StringBuffer sb = new StringBuffer(conditionMap.getString(fieldName));
	                    		sb.append("~");
	                    		sb.append(fieldName);
	                    		nutMap.put(sb.toString(), value);
	                    	}else{
	                    		nutMap.put(fieldName, value);
	                    	}                    }
	                }       
	                if(type.equals("class java.lang.Double")){     
	                    Method m = model.getClass().getMethod("get"+name);
	                    Double value = (Double) m.invoke(model);
	                    if(value != null){                    
	                    	if(conditionMap.containsKey(fieldName)){
	                    		StringBuffer sb = new StringBuffer(conditionMap.getString(fieldName));
	                    		sb.append("~");
	                    		sb.append(fieldName);
	                    		nutMap.put(sb.toString(), value);
	                    	}else{
	                    		nutMap.put(fieldName, value);
	                    	} 
	                    }
	                }                  
	                if(type.equals("class java.lang.Boolean")){
	                    Method m = model.getClass().getMethod("get"+name);    
	                    Boolean value = (Boolean) m.invoke(model);
	                    if(value != null){                      
	                    	if(conditionMap.containsKey(fieldName)){
	                    		StringBuffer sb = new StringBuffer(conditionMap.getString(fieldName));
	                    		sb.append("~");
	                    		sb.append(fieldName);
	                    		nutMap.put(sb.toString(), value);
	                    	}else{
	                    		nutMap.put(fieldName, value);
	                    	}
	                    }
	                }
	                if(type.equals("class java.util.Date")){
	                    Method m = model.getClass().getMethod("get"+name);                    
	                    Date value = (Date) m.invoke(model);
	                    if(value != null){
	                    	if(conditionMap.containsKey(fieldName)){
	                    		StringBuffer sb = new StringBuffer(conditionMap.getString(fieldName));
	                    		sb.append("~");
	                    		sb.append(fieldName);
	                    		nutMap.put(sb.toString(), value);
	                    	}else{
	                    		nutMap.put(fieldName, value);
	                    	}
	                    }
	                }                
	            }
		}catch (Exception e) {
			// TODO: handle exception
			log.debug(e.getMessage());
		}
        return nutMap;
    }
}
