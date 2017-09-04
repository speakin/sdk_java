package mobi.speakin.sdk.util;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;
import mobi.speakin.sdk.bson.BsonProperty;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by panleiming on 17-7-20.
 */
public class BsonUtil {
    public static byte[] getBytes(Object obj) throws Exception {
        Map<String, Object> map = convertToMap(obj);
        BsonDocument.Builder builder = BsonDocuments.builder();
        for (String key : map.keySet()) {
            builder.put(key, map.get(key));
        }
        BsonDocument doc = builder.build();
        int bufSize = BsonDocuments.binarySize(doc);
        byte[] buf = new byte[bufSize];
        ByteBuffer byteBuf = ByteBuffer.wrap(buf);
        byteBuf.order(ByteOrder.LITTLE_ENDIAN);
        BsonDocuments.writeTo(byteBuf, doc);
        return buf;
    }

    private static Map<String, Object> convertToMap(Object obj) throws Exception {
        Map<String, Object> retMap = new HashMap<String, Object>();
        Field[] fields = obj.getClass().getFields();
        for (Field field : fields) {
            BsonProperty prop = field.getAnnotation(BsonProperty.class);
            if (null == prop) {
                continue;
            }
            Class<?> type = field.getType();
            if (null == field.get(obj)) {
                retMap.put(prop.value(), null);
            } else if (type.isPrimitive() || type == String.class) {
                retMap.put(prop.value(), field.get(obj));
            } else if (type.isArray()) {
                Object arry = field.get(obj);
                if (null == arry) {
                    retMap.put(prop.value(), arry);
                } else {
                    int len = Array.getLength(arry);
                    Object destArry = Array.newInstance(type.getComponentType(), len);
                    for (int i = 0; i < len; i++) {
                        Object value = Array.get(arry, i);
                        if (type.getComponentType().isPrimitive() || type.getComponentType() == String.class) {
                            Array.set(destArry, i, value);
                        } else {
                            System.out.println(convertToMap(value));
                            Array.set(destArry, i, convertToMap(value));
                        }
                    }
                    retMap.put(prop.value(), destArry);
                }
            } else {
                retMap.put(prop.value(), convertToMap(field.get(obj)));
            }
        }
        return retMap;
    }

    public static void setBytes(Object obj, byte[] data) throws Exception {
        ByteBuffer byteBuf = ByteBuffer.wrap(data);
        byteBuf.order(ByteOrder.LITTLE_ENDIAN);
        BsonDocument doc = BsonDocuments.readFrom(byteBuf);
        setMapData(obj, doc);
    }

    private static void setMapData(Object obj, Map<String, Object> doc) throws Exception {
        Field[] fields = obj.getClass().getFields();
        for (Field field : fields) {
            BsonProperty prop = field.getAnnotation(BsonProperty.class);
            if (null == prop) {
                continue;
            }
            if (false == doc.containsKey(prop.value())) {
                continue;
            }
            Class<?> type = field.getType();
            if (type.isPrimitive() || type == String.class) {
                field.set(obj, doc.get(prop.value()));
            } else if (type.isArray()) {
                Object arryObj = doc.get(prop.value());
                if (arryObj instanceof Map) {
                    Map<String, Object> arryMap = (Map<String, Object>) arryObj;
                    int len = arryMap.size();
                    Object destArry = Array.newInstance(type.getComponentType(), len);
                    for (int i = 0; i < len; i++) {
                        Object arryValue = arryMap.get(Integer.toString(i));
                        if (null == arryValue) {
                            Array.set(destArry, i, null);
                        }
                        if (arryValue.getClass().isPrimitive() || arryValue.getClass() == String.class) {
                            Array.set(destArry, i, arryValue);

                        } else {
                            if (arryValue instanceof Map) {
                                Object arryValueObj = type.getComponentType().newInstance();
                                Array.set(destArry, i, arryValueObj);
                                setMapData(arryValueObj, (Map<String, Object>) arryValue);
                            }
                        }
                    }
                    field.set(obj, destArry);
                } else if (arryObj.getClass().isArray()) {
                    field.set(obj, arryObj);
                }
            } else {
                try {
                    Object mapObj = type.newInstance();
                    setMapData(mapObj, (Map<String, Object>) doc.get(prop.value()));
                    field.set(obj, mapObj);
                }catch (Exception e){
                    System.out.println(e);
                    throw e;
                }
            }
        }
    }

}
