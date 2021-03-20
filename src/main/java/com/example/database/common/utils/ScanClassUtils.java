package com.example.database.common.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScanClassUtils {
    public static List<Class<?>> scanClass(String path) {
        //spring工具类，可以获取指定路径下的全部类
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        //MetadataReader 的工厂类
        MetadataReaderFactory readerfactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        List<Class<?>> classList = new ArrayList<>();

        try {
            Resource[] resources = resourcePatternResolver.getResources(path);
            for (Resource resource: resources) {
                Class<?> classData = Class.forName(readerfactory.getMetadataReader(resource).getClassMetadata().getClassName());
                classList.add(classData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classList;
    }
}
