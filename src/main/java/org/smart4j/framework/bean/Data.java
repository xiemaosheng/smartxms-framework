package org.smart4j.framework.bean;

/**
 * 返回数据对象
 * Created by xms on 2017/3/4 0004.
 */
public class Data {

    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model){
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
