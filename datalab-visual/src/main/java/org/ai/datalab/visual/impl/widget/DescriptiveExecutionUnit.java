/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.DefaultExecutionUnit;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.misc.Property;
import org.ai.datalab.core.misc.SimpleData;
import org.ai.datalab.core.misc.DataUtil;
import org.ai.datalab.visual.DataLabVisualUtil;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class DescriptiveExecutionUnit extends DefaultExecutionUnit {

    private final Data inputFields;

    public Data getInputFields() {
        return inputFields;
    }

    public Data getGeneratedOutputData() {
        if (generateData(getProvidingType())) {
            return getMapping().getSampleData();
        }
        return null;
    }

    public Data getFinalOutputData() {
        Data finalData = new SimpleData();
        Data data = getInputFields();
        if (data != null) {
            for (Entry<String, Object> e : data.getEntrySet()) {
                try {
                    finalData.setValue(e.getKey(), null, e.getValue());
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        data = getGeneratedOutputData();
        if (data != null) {
            for (Entry<String, Object> e : data.getEntrySet()) {
                try {
                    finalData.setValue(e.getKey(), null, e.getValue());
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        return finalData;
    }

    public DescriptiveExecutionUnit(String suggestedDescription, AbstractExecutorProvider provider, Data usedInputFields) {
        super(suggestedDescription, provider, null, null);
        this.inputFields = usedInputFields;
    }

    public void setSuggestedDescription(String suggestedDescription) {
        setDescription(suggestedDescription);
        if (graphScene != null) {
            Widget w = graphScene.findWidget(this);
            if (w != null) {
                if (w instanceof ExecutionUnitWidget) {
                    ((ExecutionUnitWidget) w).getLabelWidget().setLabel(suggestedDescription);
                }
            }
            recreateNode(graphScene);
        }

    }

    private transient Node node;

    private transient GraphScene graphScene;

    public Node getNode(GraphScene graph) {
        if (node == null) {
            node = createNode(graph);
            prepareNode(node);
        }

        return node;
    }

    public Node recreateNode(GraphScene graph) {
        node = createNode(graph);
        prepareNode(node);
        return node;
    }

    protected Sheet.Set createPropertySheet() {

        Sheet.Set set = Sheet.createPropertiesSet();
        for (Property p : getProperties().keySet()) {
            set.put(new PropertySupport.ReadWrite(p.getName(), p.getClazz(), p.getDescription(), p.getShortDesc()) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return getProperty(p);
                }

                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    assert val == null || p.getClazz().isInstance(val);
                    setProperty(p, val);
                }

            });
        }
        set.setName("Options");
        return set;
    }

    protected abstract void prepareNode(Node node);

    private Node createNode(GraphScene graph) {

        Node newNode = new DescriptiveAbstractNode(this);
        newNode.setDisplayName(getDescription());
        this.graphScene = graph;
        return newNode;
    }

    public GraphScene getGraphScene() {
        return graphScene;
    }

    public Sheet.Set[] getBasicPropertiesSheet() {
        List<Sheet.Set> props = new LinkedList<>();
        if (this instanceof ExecutionUnit) {
            ExecutionUnit provider = (ExecutionUnit) this;
            Sheet.Set set = Sheet.createPropertiesSet();
            set.put(new PropertySupport.ReadWrite<String>("Display Name", String.class, "Display Name", "Display Name") {

                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return getDescription();
                }

                @Override
                public void setValue(String val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    setSuggestedDescription(val);
                }
            });
//
//            Node.Property threadCount = getPropertyImpl(PrimitiveBlock.THREAD_COUNT, Integer.class, !(provider.getProvidingType() == Reader.class || provider.getProvidingType() == Writer.class));
//            //Node.Property readerTimeout = getPropertyImpl(PrimitiveBlock.READER_TIMEOUT_IN_SECONDS, String.class, false);
//            //Node.Property processTimeout = getPropertyImpl(PrimitiveBlock.PROCESSING_TIMEOUT_IN_SECONDS, String.class, false);
//
//            if (provider.getProvidingType() == Reader.class) {
//                //TODO implement startindex and endindex for readers
//            }

//            threadCount.setName("Threads to use");
//            readerTimeout.setName("Input timeout in seconds");
//            processTimeout.setName("Processing timeout");
//
//            set.put(readerTimeout);
//            set.put(threadCount);
//            set.put(processTimeout);
            set.setName("Properties");
            String tabName = "Details";
            set.setValue("tabName", tabName);
            set.setPreferred(true);
            props.add(set);
            Sheet.Set inputSet = DataLabVisualUtil.getDataSheet(getInputFields(), "Input Available", tabName);
            props.add(inputSet);
            if (generateData(getProvidingType())) {
                try {
                    props.add(DataLabVisualUtil.getDataSheet(getGeneratedOutputData(), "Output Generated", tabName));
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

        }

        return props.toArray(new Sheet.Set[0]);
    }

    public Node.Property getPropertyImpl(final Property property, Class type, final boolean canWrite) {
//        if (getProperty(propertyName) == null) {
//            setProperty(propertyName, JobUtil.getDefaultValue(propertyName));
//        }
        return new Node.Property(type) {

            @Override
            public boolean canRead() {
                return true;
            }

            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return getProperty(property);
            }

            @Override
            public boolean canWrite() {
                return canWrite;
            }

            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                setProperty(property, val);
            }
        };
    }

    @Override
    public final void firePropertyChanged(String name, Object value) {
        if (graphScene != null) {
            //TODO modified logic
            //graphScene.setModified();
        }
    }

    @Override
    public AbstractExecutorProvider getExecutorProvider() {
        return (AbstractExecutorProvider) super.getExecutorProvider();
    }

    public MappingHelper getMapping() {
        return getExecutorProvider().getMapping();
    }

    private static final EnumSet noOutput = EnumSet.of(ExecutorType.WRITER, ExecutorType.CONDITION);

    private boolean generateData(ExecutorType type) {
        return !noOutput.contains(type);
    }

}
