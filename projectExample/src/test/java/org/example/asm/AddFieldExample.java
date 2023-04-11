package org.example.asm;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AddFieldExample {

    public static String targetPath="/Users/zhaolin/Desktop/github/example/projectExample/target/classes/";
    @Test
    public void testAddField() throws IOException, InstantiationException, IllegalAccessException {
// 读取类的字节码
        String className = "org.example.asm.MyClass";

        Path classFilePath = Paths.get(targetPath + className.replaceAll("\\.", "/") + ".class");
        byte[] classBytes = Files.readAllBytes(classFilePath);

        // 修改字节码并将其写入到ClassWriter中
        ClassReader classReader = new ClassReader(classBytes);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        AddFieldVisitor addFieldVisitor = new AddFieldVisitor(classWriter, Opcodes.ACC_PUBLIC, "newField", "Ljava/lang/String;");
        classReader.accept(addFieldVisitor, ClassReader.EXPAND_FRAMES);
        byte[] modifiedClassBytes = classWriter.toByteArray();

        // 定义新的类并创建实例
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> modifiedClass = myClassLoader.defineClass(className, modifiedClassBytes);
        Object modifiedObject = modifiedClass.newInstance();

        // 输出结果
        System.out.println("Original class fields:");
        for (java.lang.reflect.Field field : MyClass.class.getDeclaredFields()) {
            System.out.println(field.getName() + " (" + field.getType().getName() + ")");
        }

        System.out.println("\nModified class fields:");
        for (java.lang.reflect.Field field : modifiedClass.getDeclaredFields()) {
            System.out.println(field.getName() + " (" + field.getType().getName() + ")");
        }
    }

}
