package com.example.database.common.record;

import com.example.database.common.record.annotation.NotRecordField;
import com.example.database.common.record.annotation.RecordField;
import com.example.database.common.record.dto.RecordFieldDTO;
import com.example.database.common.redis.RedisUtils;
import com.example.database.common.utils.ScanClassUtils;
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
            String className = classData.getSimpleName();
            if(recordField != null)
                this.recordFieldAnnotation(classData, RecordFieldMap.recordField,
                        recordField.value().equals("")? className: recordField.value());
            else if(notRecordField != null)
                this.notRecordFieldAnnotation(classData, RecordFieldMap.recordField,
                        notRecordField.value().equals("")? className: notRecordField.value());
        });
    }

    private void recordFieldAnnotation(Class<?> classData, Map<String, Set<RecordFieldDTO>> recordMap, String key) {
        Map<String, String> annotationDataMap = new LinkedHashMap<>();
        Arrays.stream(classData.getDeclaredFields())
                .filter(field -> !excludeField.contains(field.getName()))
                .filter(field -> field.getAnnotation(NotRecordField.class) == null)
                .forEach(field -> {
                    RecordField annotation = field.getAnnotation(RecordField.class);
                    annotationDataMap.clear();
                    if(annotation != null) {
                        annotationDataMap.put("fieldName", annotation.value().equals("")? null: annotation.value());
                        annotationDataMap.put("type", annotation.type().equals("")? null: annotation.type());
                        annotationDataMap.put("label", annotation.label().equals("")? null: annotation.label());
                    }
                    if(recordMap.get(key) == null)
                        recordMap.put(key, new LinkedHashSet());
                    recordMap.get(key).add(RecordFieldMap.valueOfFieldDTO(field, annotationDataMap));
                });
    }

    private void notRecordFieldAnnotation(Class<?> classData, Map<String, Set<RecordFieldDTO>> recordMap, String key) {
        Map<String, String> annotationDataMap = new LinkedHashMap<>();
        Arrays.stream(classData.getDeclaredFields())
                .filter(field -> !excludeField.contains(field.getName()))
                .filter(field -> field.getAnnotation(RecordField.class) != null)
                .forEach(field -> {
                    RecordField annotation = field.getAnnotation(RecordField.class);
                    annotationDataMap.clear();
                    annotationDataMap.put("fieldName", annotation.value().equals("")? null: annotation.value());
                    annotationDataMap.put("type", annotation.type().equals("")? null: annotation.type());
                    annotationDataMap.put("label", annotation.label().equals("")? null: annotation.label());

                    if(recordMap.get(key) == null)
                        recordMap.put(key, new LinkedHashSet());
                    recordMap.get(key).add(RecordFieldMap.valueOfFieldDTO(field, annotationDataMap));
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
