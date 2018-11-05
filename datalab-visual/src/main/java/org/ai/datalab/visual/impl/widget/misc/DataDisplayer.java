/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget.misc;

import java.util.Collection;
import org.ai.datalab.core.Data;

/**
 *
 * @author Mohan Purushothaman
 */
public interface DataDisplayer {
    public void addData(Collection<Data> data);
    public void addData(Data... data);
}
