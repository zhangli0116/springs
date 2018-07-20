package com.frame.springmvc.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author user
 * @Date 2018/7/20
 * @Time 14:44
 */
public class DBUtils {
    /**
     * 获得完整sql 语句
     * @param mappedStatement
     * @param boundSql
     * @return
     */
    //get paramValueList
    public static String builderLogSql(MappedStatement mappedStatement,BoundSql boundSql)
    {
        List<Object> paramValueList = new ArrayList<Object>();

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        //借鉴 DefaultParameterHandler setParameters(PreparedStatement ps)
        if(parameterMappings != null)
        {
            TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
            Configuration configuration = mappedStatement.getConfiguration();
            Object parameterObject = boundSql.getParameterObject();
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                //if (parameterMapping.getMode() != ParameterMode.OUT) {
                Object value;
                final String propertyName = parameterMapping.getProperty();
                PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                if (parameterObject == null) {
                    value = null;
                } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                    value = parameterObject;
                } else if (boundSql.hasAdditionalParameter(propertyName)) {
                    value = boundSql.getAdditionalParameter(propertyName);
                } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
                        && boundSql.hasAdditionalParameter(prop.getName())) {
                    value = boundSql.getAdditionalParameter(prop.getName());
                    if (value != null) {
                        value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
                    }
                } else {
                    value = metaObject == null ? null : metaObject.getValue(propertyName);
                }

                paramValueList.add(value);
                // }
            }
        }
        return preparedSqlBuilder(boundSql.getSql(),paramValueList);
    }

    // replace '?'
    public static String preparedSqlBuilder(String sql, List<Object> columnValues) {
        String str = sql;
        for (Object object : columnValues) {
            str = StringUtils.replaceOnce(str, "?", getBindVariableText(object));
        }
        return str;
    }
    // Object to String
    public static String getBindVariableText(Object bindVariable) {
        if (bindVariable instanceof String) {
            return quote(bindVariable.toString());
        } else if (bindVariable instanceof Number) {
            return bindVariable.toString();
        } else if (bindVariable instanceof Timestamp) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
            return quote(sdf.format((java.util.Date) bindVariable));
        } else if (bindVariable instanceof java.util.Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return quote(sdf.format((java.util.Date) bindVariable));
        } else if (bindVariable instanceof Boolean) {
            return bindVariable.toString();
        } else if (bindVariable == null) {
            return "null";
        } else {
            return quote(bindVariable.toString());
        }
    }
    protected static String quote(String text) {
        StringBuilder result = new StringBuilder("'");
        return result.append(text).append("'").toString();
    }


}
