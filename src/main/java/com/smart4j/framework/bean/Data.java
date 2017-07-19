package com.smart4j.framework.bean;

/**
 * 返回数据对象
 * Created by Administrator on 2017/7/19.
 */
public class Data {
    /**
     * 模型数据
     */
    private Object model;
    public Data(Object model){
        this.model=model;
    }
    public Object getModel(){
        return model;
    }
}
