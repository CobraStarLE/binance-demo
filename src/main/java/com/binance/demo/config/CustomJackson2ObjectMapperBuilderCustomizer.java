package com.binance.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CustomJackson2ObjectMapperBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer, Ordered {
    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        //若POJO对象的属性值为null，序列化时不进行显示
//        jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);
        //若POJO对象的属性值为""，序列化时不进行显示
        jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.ALWAYS);
        //DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES相当于配置，JSON串含有未知字段时，反序列化依旧可以成功
        jacksonObjectMapperBuilder.failOnUnknownProperties(false);
        //序列化时的命名策略——驼峰命名法
//        jacksonObjectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        //针对于Date类型，文本格式化
        jacksonObjectMapperBuilder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //针对于JDK新时间类。序列化时带有T的问题，自定义格式化字符串
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        Module[] moduleArr=new Module[]{javaTimeModule,module};
        jacksonObjectMapperBuilder.modules(moduleArr);


//            jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //默认关闭，将char[]数组序列化为String类型。若开启后序列化为JSON数组。
        jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS);

        //默认开启，若map的value为null，则不对map条目进行序列化。(已废弃)。
        // 推荐使用：jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);
        jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.WRITE_NULL_MAP_VALUES);

        //默认开启，将Date类型序列化为数字时间戳(毫秒表示)。关闭后，序列化为文本表现形式(2019-10-23T01:58:58.308+0000)
        //若设置时间格式化。那么均输出格式化的时间类型。
        jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //默认关闭，在类上使用@JsonRootName(value="rootNode")注解时是否可以包裹Root元素。
        // (https://blog.csdn.net/blueheart20/article/details/52212221)
//            jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRAP_ROOT_VALUE);
        //默认开启：如果一个类没有public的方法或属性时，会导致序列化失败。关闭后，会得到一个空JSON串。
        jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        //默认关闭，即以文本(ISO-8601)作为Key，开启后，以时间戳作为Key
        jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);

        //默认禁用，禁用情况下，需考虑WRITE_ENUMS_USING_TO_STRING配置。启用后，ENUM序列化为数字
        jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_INDEX);

        //仅当WRITE_ENUMS_USING_INDEX为禁用时(默认禁用)，该配置生效
        //默认关闭，枚举类型序列化方式，默认情况下使用Enum.name()。开启后，使用Enum.toString()。注：需重写Enum的toString方法;
        jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);

        //默认开启，空Collection集合类型输出空JSON串。关闭后取消显示。(已过时)
        // 推荐使用serializationInclusion(JsonInclude.Include.NON_EMPTY);
        jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);

        //默认关闭，当集合Collection或数组一个元素时返回："list":["a"]。开启后，"list":"a"
        //需要注意，和DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY 配套使用，要么都开启，要么都关闭。
//            jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);

        //默认关闭。打开后BigDecimal序列化为文本。(已弃用)，推荐使用JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN配置
//            jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        //默认关闭，即使用BigDecimal.toString()序列化。开启后，使用BigDecimal.toPlainString序列化，不输出科学计数法的值。
        jacksonObjectMapperBuilder.featuresToEnable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);

        /**
         * JsonGenerator.Feature的相关参数（JSON生成器）
         */

        //默认关闭，即序列化Number类型及子类为{"amount1":1.1}。开启后，序列化为String类型，即{"amount1":"1.1"}
//        jacksonObjectMapperBuilder.featuresToEnable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
        /******
         *  反序列化
         */
        //默认关闭，当JSON字段为""(EMPTY_STRING)时，解析为普通的POJO对象抛出异常。开启后，该POJO的属性值为null。
        jacksonObjectMapperBuilder.featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        //默认关闭
//            jacksonObjectMapperBuilder.featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        //默认关闭，若POJO中不含有JSON中的属性，则抛出异常。开启后，不解析该字段，而不会抛出异常。
        jacksonObjectMapperBuilder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}