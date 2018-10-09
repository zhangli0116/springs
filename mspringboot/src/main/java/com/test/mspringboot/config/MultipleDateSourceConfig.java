package com.test.mspringboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.test.mspringboot.utils.datasource.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by Administrator on 2018/9/25
 * 动态创建数据源 静态
 * @author admin
 */
@Configuration
public class MultipleDateSourceConfig {

    /**
     *
     * @return
     */
    @Bean("master")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource creeateMasterDataSource(){
        return new DruidDataSource();
    }

    @Bean("slave1")
    @ConfigurationProperties(prefix = "spring.datasource.slave1")
    public DataSource creeateSlave1DataSource(){
        return new DruidDataSource();
    }

    /**
     * 设置动态数据源，通过@Primary 来确定主DataSource
     * @return
     */
    @Bean
    @Primary
    public DataSource createDynamicdataSource(@Qualifier("master") DataSource master,@Qualifier("slave1") DataSource slave1){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(master);
        //配置多数据源
        Map<Object, Object> map = new HashMap<>();
        map.put("master",master);
        map.put("slave1",slave1);
        dynamicDataSource.setTargetDataSources(map);
        return  dynamicDataSource;
    }
}
