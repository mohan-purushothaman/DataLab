/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.file.visual;

import org.openide.nodes.Node;
import org.ai.datalab.adx.file.File_Provider;
import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.misc.Property;
import org.ai.datalab.core.misc.PropertyChangeListener;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
public class FileExecutionUnit extends DescriptiveExecutionUnit {

    public static final Property FILE_NAME = new Property("File Name", "file name", "file name", String.class);
    public static final Property BUFFER_SIZE = new Property("Buffer Size", "buffer size", "buffer size", Integer.class);
    public static final Property HAS_HEADER = new Property("Header Present", "header present", "first line is header", Boolean.class);
    public static final Property DATA_FORMAT = new Property("DATA_FORMAT", "Line Format", "line format", String.class);

    public FileExecutionUnit(String suggestedDescription, AbstractExecutorProvider provider, Data usedInputFields) {
        super(suggestedDescription, provider, usedInputFields);
        if (provider instanceof File_Provider) {
            File_Provider r = (File_Provider) provider;
            setProperty(FILE_NAME, r.getResourceID(), null,true);
            setProperty(BUFFER_SIZE, r.getBufferSize(), new PropertyChangeListener<Integer>() {
                @Override
                public void valueUpdated(Integer newValue) {
                    r.setBufferSize(newValue);
                }
            },false);
            setProperty(HAS_HEADER, r.hasHeader(), new PropertyChangeListener<Boolean>() {
                @Override
                public void valueUpdated(Boolean newValue) {
                    r.setHasHeader(newValue);
                }
            },false);
            setProperty(DATA_FORMAT, r.getLineFormat(), null,true);
        }
    }

    @Override
    protected void prepareNode(Node node) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
