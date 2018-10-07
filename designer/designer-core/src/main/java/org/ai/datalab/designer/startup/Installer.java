/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.startup;

import org.openide.modules.ModuleInstall;
import org.ai.datalab.designer.visual.resource.ResourceStore;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        ResourceStore.getResourceList();
    }

}
