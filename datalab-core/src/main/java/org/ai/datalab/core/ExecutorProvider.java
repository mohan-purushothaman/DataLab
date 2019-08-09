/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core;

import java.lang.reflect.Constructor;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.executor.Processor;
import org.ai.datalab.core.executor.Reader;
import org.ai.datalab.core.executor.Writer;

/**
 *
 * @author Mohan Purushothaman
 * @param <T>
 */
public interface ExecutorProvider {

    public ExecutorType getProvidingType();

    public Executor getNewExecutor();

    default public String getResourceID() {
        return null;
    }

    public void setResourceId(String resourceId);

    public static ExecutorProvider getReaderInstance(Class<? extends Reader> reader, Object... construtorArgs) {
        return getInstance(ExecutorType.READER, reader, construtorArgs);
    }

    public static ExecutorProvider getProcessorInstance(Class<? extends Processor> processor, Object... construtorArgs) {
        return getInstance(ExecutorType.PROCESSOR, processor, construtorArgs);
    }

    public static ExecutorProvider getWriterInstance(Class<? extends Writer> writer, Object... construtorArgs) {
        return getInstance(ExecutorType.WRITER, writer, construtorArgs);
    }

    public static ExecutorProvider getInstance(ExecutorType type, Class<? extends Executor> executor, Object... construtorArgs) {
        return new DefaultExecutorProviderImpl(type, executor, construtorArgs);
    }

    //TODO implement multi thread model checks when assigning thread counts
    default public boolean isMultiThreadingSupported() {
        return true;
    }
;

}

class DefaultExecutorProviderImpl implements ExecutorProvider {

    private final Class<? extends Executor> executor;
    private final ExecutorType type;
    private final Object[] arg;
    private final Constructor constructor;

    public DefaultExecutorProviderImpl(ExecutorType type, Class<? extends Executor> executor, Object... construtorArgs) {
        this.executor = executor;
        this.type = type;
        this.arg = construtorArgs;
        Constructor<Executor> exp = null;
        for (Constructor c : executor.getConstructors()) {
            if (isConstructorMatch(c, arg)) {
                exp = c;
                break;
            }
        }
        constructor = exp;
        if (constructor == null) {
            throw new RuntimeException("Not able to find a valid constrctor");
        }

    }

    @Override
    public ExecutorType getProvidingType() {
        return type;
    }

    @Override
    public Executor getNewExecutor() {
        try {
            return (Executor) constructor.newInstance(arg);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString() {
        return "{executor=" + executor + ", type=" + type + '}';
    }

    private static boolean isConstructorMatch(Constructor c, Object[] arg) {
        if (c.getParameterCount() == arg.length) {
            Class[] p = c.getParameterTypes();

            for (int i = 0; i < arg.length; i++) {
                Object a = arg[i];
                Class clazz = p[i];
                if (a != null && !clazz.isAssignableFrom(a.getClass())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void setResourceId(String resourceId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
