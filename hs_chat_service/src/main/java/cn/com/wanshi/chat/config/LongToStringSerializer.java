package cn.com.wanshi.chat.config;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Long类型默认返回数字字符串， 如果为null则返回“0”
 *
 * @author zengjia
 * @date 2020-12-19 11:28:42
 */
public class LongToStringSerializer implements ObjectSerializer {
    public static final LongToStringSerializer instance = new LongToStringSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;

        if (object == null) {
            out.writeString("0");
        } else {
            out.writeString(String.valueOf(object));
        }
    }
}
