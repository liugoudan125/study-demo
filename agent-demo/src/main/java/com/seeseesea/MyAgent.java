package com.seeseesea;// Agent类
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class MyAgent {
    // JVM启动时自动调用该方法
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Agent loaded at startup with args: " + agentArgs);
        
        // 添加类文件转换器
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, 
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) throws IllegalClassFormatException {
                
                // 这里可以使用ASM、Byte Buddy等库修改字节码
                if (className.equals("com/example/TargetClass")) {
                    System.out.println("Transforming class: " + className);
                    // 返回修改后的字节码
                    return modifyClass(classfileBuffer);
                }
                return classfileBuffer;
            }
        });
    }
    
    private static byte[] modifyClass(byte[] classBytes) {
        // 实际项目中通常使用字节码操作库（如ASM）修改字节码
        return classBytes;
    }
}