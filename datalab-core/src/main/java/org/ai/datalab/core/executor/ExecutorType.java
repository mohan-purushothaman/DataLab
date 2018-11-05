/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.executor;

import org.ai.datalab.core.Executor;

/**
 *
 * @author Mohan Purushothaman
 */
public enum ExecutorType {
    READER(Reader.class),
    PROCESSOR(Processor.class),
    CONDITION(Condition.class),
    WRITER(Writer.class);

    private final Class baseClass;

    private ExecutorType(Class baseClass) {
        this.baseClass = baseClass;
    }

    public boolean isInstance(Executor obj) {
        return baseClass.isInstance(obj);
    }

    public boolean doesProduceOutput() {
        return this == READER || this == PROCESSOR;
    }

}
