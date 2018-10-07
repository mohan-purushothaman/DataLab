/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.file;

import java.io.File;
import org.ai.datalab.core.resource.AbstractResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class FileResourcePool extends AbstractResourcePool<File> {

    public FileResourcePool(String filePath, int maxResourceCount) throws Exception {
        super(filePath, maxResourceCount,File.class);
    }

    public FileResourcePool(File file) throws Exception {
        this(file,1);
    }
    
    public FileResourcePool(File file,int maxResourceCount) throws Exception {
        this(file.getPath(),maxResourceCount);
    }

    @Override
    protected File createResource() throws Exception {
        return new File(getResourceId());
    }

    @Override
    protected void releaseResource(File resource) throws Exception {
    }

}
