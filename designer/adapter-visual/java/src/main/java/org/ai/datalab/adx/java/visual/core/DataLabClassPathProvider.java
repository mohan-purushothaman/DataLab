/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.visual.core;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.spi.java.classpath.ClassPathProvider;
import org.netbeans.spi.java.classpath.support.ClassPathSupport;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.ai.datalab.adx.java.JavaCodeGenerator;
import org.ai.datalab.adx.java.util.JavaUtil;

/**
 *
 * @author Mohan Purushothaman
 */
@ServiceProviders(
        @ServiceProvider(service = ClassPathProvider.class))
public class DataLabClassPathProvider implements ClassPathProvider {

    private static final AtomicReference<JavaCodeGenerator> ref = new AtomicReference<>();

    @Override
    public ClassPath findClassPath(FileObject file, String type) {
        if (file.hasExt("java")) {
            if (ClassPath.COMPILE.equals(type)) {
                URL url = JavaUtil.getDataLabBaseLib();
                List<URL> libList = new LinkedList<>();
                if (url != null) {
                    libList.add(url);
                }

                JavaCodeGenerator gen = ref.get();
                if (gen != null) {
                    libList.addAll(gen.getLibList());
                }
                return ClassPathSupport.createClassPath(libList.toArray(new URL[0]));
            }
        }

        return null;
    }

    public static void updateCodeGenerator(JavaCodeGenerator generator) {
        ref.set(generator);
    }

}
