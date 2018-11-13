/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.excel.visual;

import org.openide.nodes.Node;
import org.ai.datalab.adapter.excel.adx.ExcelProvider;
import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.misc.Property;
import org.ai.datalab.core.misc.PropertyChangeListener;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
public class ExcelExecutionUnit extends DescriptiveExecutionUnit {

    public static final Property HAS_HEADER = new Property("Header Present", "header present", "first line is header", Boolean.class);
    public static final Property SHEET_NAME = new Property("Sheet Name", "sheet name", "excel sheet name", String.class);

    public ExcelExecutionUnit(String suggestedDescription, AbstractExecutorProvider provider, Data usedInputFields) {
        super(suggestedDescription, provider, usedInputFields);
        if (provider instanceof ExcelProvider) {
            ExcelProvider r = (ExcelProvider) provider;
            setProperty(HAS_HEADER, r.isHasHeader(), new PropertyChangeListener<Boolean>() {
                @Override
                public void valueUpdated(Boolean newValue) {
                    r.setHasHeader(newValue);
                }
            }, false);

            setProperty(SHEET_NAME, r.getSheetName(), new PropertyChangeListener<String>() {
                @Override
                public void valueUpdated(String newValue) {
                    r.setSheetName(newValue);
                }
            }, false);
        }
    }

    @Override
    protected void prepareNode(Node node) {

    }

}
