package cn.com.wanshi.chat.config;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @description:
 * @author: 张俊涛
 * @date: 2021/1/20 9:12
 */
public class BigDecimalToStringSerializer implements ObjectSerializer {

    public static final BigDecimalToStringSerializer instance = new BigDecimalToStringSerializer();
    public static final Set<String> SPECIAL_FIELDS = Sets.newHashSet("longitude", "latitude");

    public BigDecimalToStringSerializer() {
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
        } else {
            if (SPECIAL_FIELDS.contains((String) fieldName)) {
                BigDecimal bigDecimal = (BigDecimal) object;
                out.writeString(bigDecimal.toString());
            } else {
                String strVal = object.toString();
                Double str1=  Double.valueOf(strVal);
                String result = String.format("%.2f", str1);
                out.writeString(result);
            }
        }
    }
}
