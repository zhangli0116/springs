package com.test.mspringboot.aspect;

import com.test.mspringboot.annotation.DataSource;
import com.test.mspringboot.utils.datasource.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Create by Administrator on 2018/9/25
 * 动态数据源切换 aop
 * @author admin
 */
@Aspect
@Component
@Order(1) //需要加入切面排序
public class DynamicDataSourceAspect {
    private Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    //切入点只对@Service注解的类上的@DataSource方法生效
    @Pointcut(value="@within(org.springframework.stereotype.Service) && @annotation(dataSource)" )
    public void dynamicDataSourcePointCut(DataSource dataSource){}

    @Before(value = "dynamicDataSourcePointCut(dataSource)")
    public void switchDataSource(DataSource dataSource) throws Throwable{
        logger.info("##############数据源 ：{}###############",dataSource.value());
        DataSourceContextHolder.setDataSource(dataSource.value());
    }

    @After(value="dynamicDataSourcePointCut(dataSource)")
    public void after(DataSource dataSource){
        DataSourceContextHolder.cleanDataSource();
    }
}
