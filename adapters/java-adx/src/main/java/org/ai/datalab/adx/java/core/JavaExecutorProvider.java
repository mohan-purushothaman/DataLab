/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.core;

import org.ai.datalab.adx.java.JavaCodeGenerator;
import org.ai.datalab.adx.java.util.JavaUtil;
import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.Executor;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.executor.ExecutorType;

/**
 *
 * @author Mohan Purushothaman
 */
public class JavaExecutorProvider extends AbstractExecutorProvider {

    private final ExecutorType type;

    private final JavaCodeGenerator codeGenerator;

    public JavaExecutorProvider(ExecutorType type, JavaCodeGenerator generator, MappingHelper mapping) {
        super(mapping, null);
        this.type = type;
        this.codeGenerator = generator;
    }

    @Override
    public ExecutorType getProvidingType() {
        return type;
    }

    public JavaCodeGenerator getCodeGenerator() {
        return codeGenerator;
    }

    @Override
    public Executor getNewExecutor() {
        try {
            Class<Executor> clazz = JavaUtil.createClass(codeGenerator);
            return clazz.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
