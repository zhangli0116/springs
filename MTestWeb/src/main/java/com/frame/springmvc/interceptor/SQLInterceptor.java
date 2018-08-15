package com.frame.springmvc.interceptor;

import com.frame.springmvc.utils.DBUtils;
import com.frame.springmvc.utils.SqlLogger;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;

/**
 * mybatis 拦截器
 * 1)拦截 statementId 包含query的查询语句 添加limit 0,4
 * 2)打印sql语句
 * @author user
 * @Date 2018/7/20
 * @Time 14:41
 */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class,Integer.class})})
public class SQLInterceptor implements Interceptor {

    private String SQL_PATTERN =".*query.*";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler)invocation.getTarget();
        //RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        //   StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
        if(!(handler instanceof StatementHandler)) {
            return invocation.proceed();
        }
        MetaObject metaHandler = SystemMetaObject.forObject((RoutingStatementHandler)handler);
        String statementId = (String)metaHandler.getValue("delegate.mappedStatement.id");
        BoundSql boundSql = handler.getBoundSql();
        String sql = boundSql.getSql();
        //过滤
        if(statementId.matches(SQL_PATTERN)){
            StringBuffer sqlBuffer = new StringBuffer(sql);
            sqlBuffer.append(" limit 0,4");
            MetaObject meteObject = SystemMetaObject.forObject(boundSql);
            meteObject.setValue("sql", sqlBuffer.toString());
        }
        MappedStatement mappedStatement = (MappedStatement)metaHandler.getValue("delegate.mappedStatement");
        SqlLogger.log("SQLInterceptor|| " + DBUtils.builderLogSql(mappedStatement, boundSql));
        //SqlLogger.log("SQLInterceptor change to || " +boundSql.getSql());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);

    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub

    }



}
