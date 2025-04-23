package com.beiming.loader;

/**
 * MyClassLoader-2024/3/29-14:25
 */
public class MyClassLoader extends ClassLoader {
    public MyClassLoader(String name, ClassLoader parent) {
        super(name, parent);
    }

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    public MyClassLoader() {
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        
        return super.findClass(name);
    }
}
