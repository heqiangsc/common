package com.jialing.swift.custom;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class SwiftProxy {

    private static String ln = "\r\n";

    public static Object newProxyInstance(SwiftClassLoader classLoader, Class<?>[] interfaces, SwiftInvocationHandler handler) {

        try {

            String proxySrc = generateSrc(interfaces[0]);
            String filePath = SwiftProxy.class.getResource("").getPath();
            File f = new File(filePath + "$Proxy0.java");
            FileWriter fw = new FileWriter(f);
            fw.write(proxySrc);
            fw.flush();
            fw.close();

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
            Iterable iterable = manager.getJavaFileObjects(f);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
            task.call();
            manager.close();

            Class proxyClass = classLoader.findClass("$Proxy0");
            Constructor c = proxyClass.getConstructor(SwiftInvocationHandler.class);
            f.delete();
            return c.newInstance(handler);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private static String generateSrc(Class<?> interfaces) {
        StringBuilder src = new StringBuilder()
        .append("package com.jialing.swift.custom;"+ln)
        .append("import java.lang.reflect.Method;"+ln)
        .append("public class $Proxy0 implements "+ interfaces.getName() + " {" + ln)
        .append("SwiftInvocationHandler h;"+ln)
        .append("public $Proxy0(SwiftInvocationHandler h) {"+ln)
        .append("this.h = h;"+ln)
        .append("}"+ln);

        for (Method m: interfaces.getMethods()) {
            src.append("public "+m.getReturnType().getName() + " " + m.getName() +"(){" +ln)
            .append("try {"+ln)
            .append("Method m =" + interfaces.getName() +".class.getMethod(\""+m.getName()+"\", new Class[]{}); "+ln)
            .append("this.h.invoke(this, m, null);"+ln)
            .append("}catch(Throwable e){e.printStackTrace();}"+ln)
            .append("}"+ln);
        }
        src.append("}"+ln);
        return src.toString();
    }
}
