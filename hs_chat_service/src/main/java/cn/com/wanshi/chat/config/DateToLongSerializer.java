package cn.com.wanshi.chat.config;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * Date类型默认返回long时间戳， 如果Date为null则返回0
 *
 * @author zengjia
 * @date 2020-12-19 11:28:42
 */
public class DateToLongSerializer implements ObjectSerializer {
    public static final DateToLongSerializer instance = new DateToLongSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;

        if (object == null) {
            out.writeLong(0);
        } else {
            Date date = (Date) object;
            out.writeLong(date.getTime());
        }
    }
}
