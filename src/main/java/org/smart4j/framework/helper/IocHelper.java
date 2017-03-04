package org.smart4j.framework.helper;


import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 * Created by xms 2017/3/4 0004.
 */
public final class IocHelper {

    static {
        // 获取所有的Bean类 与 Bean 实例之间的映射关系（简称 Bean Map）
        Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)){
            // 遍历 Bean Map
            for(Map.Entry<Class<?>,Object> beamEntry : beanMap.entrySet()){
                // 从BeanMap 中获取Bean 类与 bean 的实例
                Class<?> beanClass = beamEntry.getKey();
                Object beanInstance = beamEntry.getValue();

                // 获取 Bean 类定义的所有成员变量（简称 Bean Field）
                Field[] beanFields = beanClass.getFields();
                if(ArrayUtil.isNotEmpty(beanFields)){
                    // 遍历 Bean Field
                    for (Field beanField : beanFields){
                        //判断当前 Bean Field 是否带有Inject 注解
                        if (beanField.isAnnotationPresent(Inject.class)){
                            // 在 Bean Map 中获取 bean field 对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (null != beanFieldInstance){
                                // 通过反射初始化 BeanField 的值
                                ReflectionUtil.setField(beanInstance,beanField,beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }

    }
}
