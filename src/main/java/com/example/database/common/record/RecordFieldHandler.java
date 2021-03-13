package com.example.database.common.record;

import com.example.database.common.record.annotation.NotRecordField;
import com.example.database.common.record.annotation.RecordField;
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
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;


/**
 * RecordTypeField处理
 */
@Component
public class RecordFieldHandler {

    @Value("${record.resource-path}")
    private String resourcePath;

    @Value("${record.class-path}")
    private String classPath;

    @Value("${record.redis-key}")
    private String redisKey;

    @Value("${record.exclude-field}")
    private Set<String> excludeField;

    @Bean
    public void recordMain() {
        List<Class<?>> recordFieldClass = getAnnotationClass(RecordField.class, NotRecordField.class);
        recordFieldClass.stream().forEach(classData -> {
            RecordField recordField = classData.getAnnotation(RecordField.class);
            NotRecordField notRecordField = classData.getAnnotation(NotRecordField.class);
            if(recordField != null && !recordField.value().equals("") )
                recordFieldAnnotation(classData, RecordFieldMap.recordField, recordField.value());
            else if(notRecordField != null && !notRecordField.value().equals(""))
                notRecordFieldAnnotation(classData, RecordFieldMap.recordField, notRecordField.value());
        });
        Map<String, List<String>> recordField = RecordFieldMap.recordField;
    }

    private void recordFieldAnnotation(Class<?> classData, Map<String, List<String>> recordMap, String key) {
        Arrays.stream(classData.getDeclaredFields())
                .filter(field -> !excludeField.contains(field.getName()))
                .filter(field -> field.getAnnotation(NotRecordField.class) == null)
                .forEach(field -> {
                    RecordField annotation = field.getAnnotation(RecordField.class);
                    String value = field.getName();
                    if(annotation != null && !annotation.value().equals(""))
                        value = annotation.value();
                    if(recordMap.get(key) == null)
                        recordMap.put(key, new LinkedList<String>());
                    recordMap.get(key).add(value);
                });
    }

    private void notRecordFieldAnnotation(Class<?> classData, Map<String, List<String>> recordMap, String key) {
        Arrays.stream(classData.getDeclaredFields())
                .filter(field -> !excludeField.contains(field.getName()))
                .filter(field -> field.getAnnotation(RecordField.class) != null)
                .forEach(field -> {
                    String value = field.getAnnotation(RecordField.class).value();
                    if(value.equals(""))
                        value = field.getName();
                    if(recordMap.get(key) == null)
                        recordMap.put(key, new LinkedList<String>());
                    recordMap.get(key).add(value);
                });
    }

    private List<Class<?>> getAnnotationClass(Class<? extends Annotation>... annotations) {
        List<Class<?>> classList = new ArrayList<>();
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
                if(isAnnotation(classData, annotations)) {
                    classList.add(classData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classList;
    }

    private Boolean isAnnotation(Class<?> classData, Class<? extends Annotation>... annotations) {
        for (int i = 0; i < annotations.length; i++) {
            if(classData.getAnnotation(annotations[i]) != null)
                return true;
        }
        return false;
    }

}
