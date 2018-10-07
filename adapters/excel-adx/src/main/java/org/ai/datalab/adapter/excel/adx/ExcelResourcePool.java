/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adapter.excel.adx;

import java.io.File;
import org.ai.datalab.adx.file.FileResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class ExcelResourcePool extends FileResourcePool{

    public ExcelResourcePool(String filePath, int maxResourceCount) throws Exception {
        super(filePath, maxResourceCount);
    }

    public ExcelResourcePool(File file) throws Exception {
        super(file);
    }

    public ExcelResourcePool(File file, int maxResourceCount) throws Exception {
        super(file, maxResourceCount);
    }
    
}
