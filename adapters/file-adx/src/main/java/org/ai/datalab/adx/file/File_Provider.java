/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.file;

import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.adx.misc.MappingHelper;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class File_Provider extends AbstractExecutorProvider {

    private int bufferSize;
    private boolean hasHeader;
    private String lineFormat;
    private String quoteString;

    public File_Provider(MappingHelper mapping, String resourceId, int bufferSize, boolean hasHeader, String lineFormat,String quoteString) {
        super(mapping, resourceId);
        this.bufferSize = bufferSize;
        this.hasHeader = hasHeader;
        this.lineFormat = lineFormat;
        this.quoteString=quoteString;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public boolean hasHeader() {
        return hasHeader;
    }

    public String getLineFormat() {
        return lineFormat;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    public void setLineFormat(String lineFormat) {
        this.lineFormat = lineFormat;
    }

    public String getQuoteString() {
        return quoteString;
    }

    public void setQuoteString(String quoteString) {
        this.quoteString = quoteString;
    }

    
    
     @Override
    public final boolean isMultiThreadingSupported() {
        return false;
    }
}
