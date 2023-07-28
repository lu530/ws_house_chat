package cn.com.wanshi.common.enums;

/**
 * @author zhongzhicheng
 */

public enum YesNoEnum {
		/**
		 * 是
		 */
		YES("是", 1),
		/**
		 * 否
		 */
		NO("否", 0);
		/**
		 *  名字
		 */
	    public final String name;
		/**
		 * 值
		 */
		public final int value;
	    

	    YesNoEnum(String name, int value) {
	        this.name = name;  
	        this.value = value;  
	    }   
	}