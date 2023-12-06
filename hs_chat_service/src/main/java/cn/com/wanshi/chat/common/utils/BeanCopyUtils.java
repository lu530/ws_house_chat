package cn.com.wanshi.chat.common.utils;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  copy util,CachedBeanCopier 
 *  class name as key , format: a+b
 *  @author h5
 *  @version 1.0
 */
public class BeanCopyUtils {

    public static final Map<String, BeanCopier> BEAN_COPIERS = new HashMap<String, BeanCopier>(); 

    /**
     * 拷贝属性到指定对象中
     * @param fromObject
     * @param toObject
     */
    public static void copy(Object fromObject, Object toObject) {  
        String key = genKey(fromObject.getClass(), toObject.getClass());  
        BeanCopier copier = null;  
        if (!BEAN_COPIERS.containsKey(key)) {  
            copier = BeanCopier.create(fromObject.getClass(), toObject.getClass(), false);  
            BEAN_COPIERS.put(key, copier);  
        } else {  
            copier = BEAN_COPIERS.get(key);  
        }  
        copier.copy(fromObject, toObject, null);  
    } 
    

    
    /**
     * 拷贝属性到新的对象中
     * @param from
     * @param to
     * @param <T>
     * @return
     */
	public static <T> T copy(Object fromObject, Class<T> toClass){
        if(fromObject == null){
            return null;
        }

        T toObject = null;
        try {
        	toObject = toClass.newInstance();
        } catch (InstantiationException  e) {
            throw new IllegalArgumentException("Bean copy error for instance");
        }   catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Bean copy error for instance");
        } catch (Exception e){
            throw new IllegalArgumentException("Bean copy error for instance");
        }

        copy(fromObject, toObject);
        return toObject;
    }
    
    /**
     * list copy
     * @param fromList
     * @param toClass
     * @return
     */
    public static <T, F> List<T> copys(List<F> fromList,final Class<T> toClass){
    	Function<F, T> beanCopier = new Function<F, T>() {
    		    public T apply(F input) {
    		    	return copy(input, toClass);
    		    }
    		};
        List<T> transform = Lists.transform(fromList, beanCopier);
		return transform;
    } 
    
	private static String genKey(Class<?> fromClass, Class<?> toClass) {  
        return fromClass.getName() + toClass.getName();  
    } 
	
}
