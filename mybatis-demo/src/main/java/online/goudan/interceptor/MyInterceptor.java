package online.goudan.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import online.goudan.annotation.DataIsolation;
import online.goudan.util.ThreadLocalUtil;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 * @author goudan
 * @date 2023/7/28 10:11
 * @desc MyIntercepts
 */
@Intercepts({
        @Signature(type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class}
        )
})
@Component
@Slf4j
public class MyInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        DataIsolation dataIsolation = ThreadLocalUtil.get("dataIsolation");
        if (null != dataIsolation) {
            List<String> list = new ArrayList<>() {
                {
                    add("Fukuda Kasumi");
                    add("Lok Ka Man");
                }
            };

            RoutingStatementHandler target = (RoutingStatementHandler) invocation.getTarget();
            StatementHandler delegate = (StatementHandler) getField(target, "delegate");
            MappedStatement mappedStatement = (MappedStatement) getField(delegate, "mappedStatement");
            if (SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
                String extSql = new StringBuilder(dataIsolation.value()).append(" in ('").append(list.get(0)).append("','").append(list.get(1))
                        .append("')").toString();
                log.info("MyInterceptor.intercept: extSql {}", extSql);
                BoundSql boundSql = delegate.getBoundSql();
                changeSql(boundSql, extSql);
            }
        }
        return invocation.proceed();
    }

    private void changeSql(BoundSql boundSql, String extSql) throws Exception {
        String sql = boundSql.getSql();
        Statement statement = CCJSqlParserUtil.parse(sql);
        Select select = (Select) statement;
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        Expression where = plainSelect.getWhere();
        Expression extExpression = CCJSqlParserUtil.parseCondExpression(extSql);
        if (null == where) {
            plainSelect.setWhere(extExpression);
        } else {
            plainSelect.setWhere(new AndExpression(extExpression, where));
        }
        setField(boundSql, "sql", statement.toString());
    }

    private DataIsolation getDataIsolation(MappedStatement mappedStatement) throws ClassNotFoundException {
        String mappedStatementId = mappedStatement.getId();
        String className = mappedStatementId.substring(0, mappedStatementId.lastIndexOf("."));
        String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(".") + 1);
        Class<?> aClass = Class.forName(className);
        for (Method declaredMethod : aClass.getDeclaredMethods()) {
            if (methodName.equals(declaredMethod.getName())) {
                return AnnotatedElementUtils.findMergedAnnotation(declaredMethod, DataIsolation.class);
            }
        }
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }

    private Object getField(Object target, String fieldName) throws Exception {
        Class<?> searchClass = target.getClass();
        while (!Object.class.equals(searchClass)) {
            Field[] declaredFields = searchClass.getDeclaredFields();
            if (Arrays.stream(declaredFields)
                    .anyMatch(field -> field.getName().equals(fieldName))) {
                Field field = searchClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            }
            searchClass = searchClass.getSuperclass();
        }
        return null;
    }

    private void setField(Object target, String fieldName, Object arg) throws Exception {
        Class<?> searchClass = target.getClass();
        while (!Object.class.equals(searchClass)) {
            Field[] declaredFields = searchClass.getDeclaredFields();
            if (Arrays.stream(declaredFields)
                    .anyMatch(field -> field.getName().equals(fieldName))) {
                Field field = searchClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, arg);
            }
            searchClass = searchClass.getSuperclass();
        }
    }
}
