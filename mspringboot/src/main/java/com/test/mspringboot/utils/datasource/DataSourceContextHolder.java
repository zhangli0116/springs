package com.test.mspringboot.utils.datasource;

/**
 * Create by Administrator on 2018/9/25
 *
 * @author admin
 */
public class DataSourceContextHolder {
    public static final String Mater = "master";
    public static final String Slave1 = "slave1";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSource(String name){
        contextHolder.set(name);
    }

    public static String getDataSource(){
        return contextHolder.get();
    }

    public static void cleanDataSource(){
        contextHolder.remove();
    }

}
