package com.example.sql;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * @author goudan
 * @date 2023/7/28 13:52
 * @desc SqlParseDemo
 */
public class SqlParseDemo {
    public static void main(String[] args) throws Exception {
        String sql = "select t1.id,t1.name,t1.age from student t1 where t1.id in ('1','2') and t1.name = 'agc' and (t1.age < 18 or t1.age > 70) order by t1.name";
//        String sql = "select t1.id,t1.name,t1.age from student t1  where id in ('1','2') order by t1.name";
        Statement statement = CCJSqlParserUtil.parse(sql);
        Select select = (Select) statement;
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        Expression where = plainSelect.getWhere();
        List<String> list = new ArrayList<>() {
            {
                add("Fukuda Kasumi");
                add("Lok Ka Man");
            }
        };
        String extSql = new StringBuilder("name in ('").append(list.get(0)).append("','").append(list.get(1))
                .append("')").toString();
        Expression expression = CCJSqlParserUtil.parseCondExpression(extSql);
        System.out.println(expression);
        if (where != null) {
            AndExpression newExpression = new AndExpression(expression, where);
            plainSelect.setWhere(newExpression);
        }
        System.out.println(plainSelect);

    }

}
