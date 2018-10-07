/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adapter.excel.adx;

import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.adx.misc.MappingHelper;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class ExcelProvider extends AbstractExecutorProvider {

    private String sheetName;
    private boolean hasHeader;

    public ExcelProvider(MappingHelper<Integer> mapping, String resourceId, String sheetName, boolean hasHeader) {
        super(mapping, resourceId);
        this.sheetName = sheetName;
        this.hasHeader = hasHeader;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    
    
    public boolean isHasHeader() {
        return hasHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }
  

    @Override
    public boolean isMultiThreadingSupported() {
        return false;
    }

}
