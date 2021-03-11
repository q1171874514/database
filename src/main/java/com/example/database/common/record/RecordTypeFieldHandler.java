package com.example.database.common.record;

import com.example.database.common.record.annotation.RecordTypeField;
import com.example.database.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;


/**
 * RecordTypeField处理
 */
@Component
public class RecordTypeFieldHandler {
    @Autowired
    private RedisUtils redisUtils;

    @Value("${record.resource-path}")
    private String resourcePath;

    @Value("${record.class-path}")
    private String classPath;

    @Value("${record.redis-key}")
    private String redisKey;

    @Value("${record.exclude-field}")
    private Set<String> excludeField;

    @Bean
    public void recordTypeField() {
        List<Class<?>> recordTypeFieldClass = getRecordTypeFieldClass();
        recordTypeFieldClass.stream().forEach(classData -> {
            String key = classData.getAnnotation(RecordTypeField.class).value();
            if(RecordTypeFieldMap.recordTypeField.get(key) == null)
                RecordTypeFieldMap.recordTypeField.put(key, new LinkedList<>());
            Arrays.stream(classData.getDeclaredFields())
                    .map(field -> field.getName())
                    .filter(fieldName -> !excludeField.contains(fieldName))
                    .forEach(fieldName -> RecordTypeFieldMap.recordTypeField.get(key).add(fieldName));
        });
    }

    private List<Class<?>> getRecordTypeFieldClass() {
        List<Class<?>> recordTypeFieldClassList = new ArrayList<>();
        //spring工具类，可以获取指定路径下的全部类
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(resourcePath) + classPath;

        //MetadataReader 的工厂类
        MetadataReaderFactory readerfactory = new CachingMetadataReaderFactory(resourcePatternResolver);

        try {
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            for (Resource resource: resources) {
                Class<?> classData = Class.forName(readerfactory.getMetadataReader(resource).getClassMetadata().getClassName());
                RecordTypeField recordTypeField = classData.getAnnotation(RecordTypeField.class);
                if(recordTypeField != null) {
                    System.out.println(classData.getName());
                    recordTypeFieldClassList.add(classData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return recordTypeFieldClassList;
    }


}
