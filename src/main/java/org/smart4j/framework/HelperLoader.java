package org.smart4j.framework;

import org.smart4j.framework.helper.*;
import org.smart4j.framework.util.ClassUtil;

/**
 * 加载相应的Helper 类
 * Created by xms on 2017/3/4 0004.
 */
public final class HelperLoader {

    /**
     * 加载
     */
    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                ConfigHelper.class,
                ControllerHelper.class,
                IocHelper.class
        };
        for (Class<?> cls : classList){
            ClassUtil.loadClass(cls.getName(),false);
        }
    }
}
