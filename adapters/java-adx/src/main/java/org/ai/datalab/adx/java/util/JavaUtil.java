/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.ai.datalab.adx.java.JavaCodeGenerator;
import org.ai.datalab.core.Data;

/**
 *
 * @author Mohan Purushothaman
 */
public class JavaUtil {

    public static <V> Class<V> createClass(JavaCodeGenerator generator) {
        return createClass(generator.getClazzName(), generator.generate(), generator.getLibList());
    }

    public static <V> Class<V> createClass(String className, String code, List<URL> libList) {
        try {
            InMemoryJavaCompiler compiler = InMemoryJavaCompiler.newInstance();
            List<URL> lib = new LinkedList<>(libList);
            lib.add(getDataLabBaseLib());
            URL[] libs = lib.toArray(new URL[0]);
            compiler.useOptions("-classpath", getClasspathString(libs));

            URLClassLoader classLoader = URLClassLoader.newInstance(libs, JavaUtil.class.getClassLoader());

            compiler.useParentClassLoader(classLoader);

            Class<V> clazz = (Class<V>) compiler.compile(className, code);
            return clazz;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static final String getFileName(String clazzName) {
        return clazzName.substring(clazzName.lastIndexOf(".") + 1);
    }

    public static URL getDataLabBaseLib() {
        CodeSource src = Data.class.getProtectionDomain().getCodeSource();
        if (src != null) {
            return src.getLocation();
        }
        return null;
    }

    private static String getClasspathString(URL[] url) {
        StringBuilder sb = new StringBuilder();

        for (URL u : url) {
            try {
                sb.append(getFilePath(u)).append(";");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return sb.toString();
    }

    public static String getFilePath(URL url) throws MalformedURLException {
        String s = url.toString();
        if (s.startsWith("jar:")) {
            s = s.substring(4);

        }
        if (s.endsWith("!/")) {
            s = s.substring(0, s.length() - 2);
        }
        return FileUtils.toFile(new URL(s)).getPath();
    }

}
