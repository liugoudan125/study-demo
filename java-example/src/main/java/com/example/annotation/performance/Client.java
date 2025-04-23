package com.example.annotation.performance;

import com.example.annotation.Student;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @date 2023/8/4 15:11
 * @desc Client
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 5, time = 5)
@State(Scope.Benchmark)
@Fork(1)
public class Client {
    private Field field;
    private String name;
    private Say student;

    private Say studentProxy;

    @Setup
    public void init() throws NoSuchFieldException {
        field = Student.class.getDeclaredField("name");
        field.setAccessible(true);
        student = new Student();
        name = "aaaaaaaa";

        studentProxy = (Say) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Say.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
    }


    @Benchmark
    public void testReflect() throws IllegalAccessException, NoSuchFieldException {
//        field.set(student, name);
        studentProxy.say();
    }

    @Benchmark
    public void testCall() {
//        student.setName(name);
        student.say();
    }

    //    @Benchmark
    public void testSum() throws InterruptedException {
        Thread.sleep(999L);
    }


    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(Client.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}
