package com.seeseesea;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * Client
 */
public class Client {
    public static void main(String[] args) throws Exception {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.registerFunction("sayHello", Client.class.getMethod("sayHello"));
        context.registerFunction("isVaild", Client.class.getMethod("isVaild", int.class));
        Object value = parser.parseExpression("#sayHello()").getValue(context);
        System.out.println(value);

        context.setVariable("data", new Data("liuchenglong", 16));
        String value2 = parser.parseExpression("""
                #data.name + " is a " +  #data.age + " man"
                """).getValue(context, String.class);
        System.out.println(value2);

        Boolean value1 = parser.parseExpression("#isVaild(8)").getValue(context, boolean.class);
        System.out.println(value1);
    }

    public static void sayHello() {
        System.out.println("hello,spel!");
    }

    public static boolean isVaild(int i) {
        return i % 2 == 0;
    }

    public static class Data {
        public Data(String name, int age) {
            this.name = name;
            this.age = age;
        }

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
