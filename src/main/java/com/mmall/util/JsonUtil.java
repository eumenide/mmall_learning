package com.mmall.util;

import com.google.common.collect.Lists;
import com.mmall.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author: eumes
 * @date: 2019/12/5
 **/

@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 序列化相关属性
        // 对象所有字段全部列入
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);
        // 取消默认转换timestamps格式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 忽略空Bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        //所有的日期格式都统一为以下样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        // 反序列化相关属性
        // 忽略在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Parse Object to String error: ", e);
            return null;
        }
    }

    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Parse Object to String error: ", e);
            return null;
        }
    }

    public static <T> T string2Obj(String string, Class<T> clazz) {
        if (StringUtils.isEmpty(string) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) string : objectMapper.readValue(string, clazz);
        } catch (IOException e) {
            log.warn("Parse String to Object error: ", e);
            return null;
        }
    }

    public static <T> T string2Obj(String string, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(string) || typeReference == null) {
            return null;
        }
        try {
            return typeReference.getType().equals(String.class) ? (T) string : objectMapper.readValue(string, typeReference);
        } catch (IOException e) {
            log.warn("Parse String to Object error:", e);
            return null;
        }
    }

    public static <T> T string2Obj(String string, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return objectMapper.readValue(string, javaType);
        } catch (IOException e) {
            log.warn("Parse String to Object error:", e);
            return null;
        }
    }


    public static void main(String[] args) {
        User user1 = new User();
        user1.setId(1);
        user1.setEmail("xxxxxxx@qq.com");

        User user2 = new User();
        user2.setId(2);
        user2.setEmail("yyyyyy@qq.com");

        String userJson = JsonUtil.obj2String(user1);
        String userPrettyJson = JsonUtil.obj2StringPretty(user1);

        log.info("userJson:{}", userJson);
        log.info("userPrettyJson:{}", userPrettyJson);

        User user = JsonUtil.string2Obj(userJson, User.class);
        log.info("user.id:{}", user.getId());
        log.info("user.email:{}", user.getEmail());

        List<User> userList = Lists.newArrayList();
        userList.add(user1);
        userList.add(user2);

        String userListStr = JsonUtil.obj2StringPretty(userList);
        log.info("userListStr:{}", userListStr);

        List<User> userListObj = JsonUtil.string2Obj(userListStr, new TypeReference<List<User>>(){});

        List<User> userListObj2 = JsonUtil.string2Obj(userListStr, List.class, User.class);

        System.out.println("end");

    }

}
