/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.wiz.panels;

/**
 *
 * @author Mohan Purushothaman
 */
public enum WIZARD_PANEL {
    TYPE_FILTER_PANEL,
    INPUT_PANEL,
    OUTPUT_PANEL,
    CONFIG_PANEL;

    public String getName() {
        switch (this) {
            case TYPE_FILTER_PANEL:
                return "Select Connector";
            case INPUT_PANEL:
                return "Connector Details";
            case OUTPUT_PANEL:
                return "Output Details";
            case CONFIG_PANEL:
                return "Configurations";
        }
        return "unknown";
    }

}
