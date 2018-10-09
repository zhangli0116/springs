package com.test.mspringboot.utils.datasource;

import com.test.mspringboot.utils.datasource.DataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Create by Administrator on 2018/9/25
 * 动态数据源bean
 * @author admin
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }
}
