/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.visual.resource;

import org.ai.datalab.core.misc.SimpleData;
import org.ai.datalab.visual.DataUtil;

/**
 *
 * @author Mohan Purushothaman
 */
public class FileNameUtil {

    public static String normailzeName(String fileName) {
        return DataUtil.normalizeFieldKey(fileName, new SimpleData());
    }
}
