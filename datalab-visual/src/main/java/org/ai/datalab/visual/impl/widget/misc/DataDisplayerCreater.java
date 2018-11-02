/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget.misc;

import org.openide.util.Lookup;

/**
 *
 * @author Mohan Purushothaman
 */
public interface DataDisplayerCreater {

    public DataDisplayer displayData(String tabName);

    public static DataDisplayer getDataDisplayer(String tabName) {
        DataDisplayerCreater lookup = Lookup.getDefault().lookup(DataDisplayerCreater.class);

        if (lookup == null) {
            lookup = new DataDisplayerCreater() {
                @Override
                public DataDisplayer displayData(String tabName) {
                    SimpleDataDisplayer simpleDataDisplayer = new SimpleDataDisplayer();

                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            simpleDataDisplayer.setTitle(tabName);
                            simpleDataDisplayer.setVisible(true);
                        }
                    });
                    return simpleDataDisplayer.getDataDisplayer();
                }
            };
        }
        return lookup.displayData(tabName);
    }

}
