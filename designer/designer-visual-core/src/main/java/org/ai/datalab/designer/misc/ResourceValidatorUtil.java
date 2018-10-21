/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.misc;

import java.awt.Dialog;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.ai.datalab.core.DataJob;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.designer.visual.resource.ResourceStore;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Mohan Purushothaman
 */
public class ResourceValidatorUtil {

    public static Set<String> findMissingResources(DataJob job) {
        Set<String> missing = new HashSet<>();

        for (String s : getRequiredResources(job)) {
            ResourcePool resourcePool = ResourceStore.getResourcePool(s);
            if (resourcePool == null) {
                missing.add(s);
            }
        }
        return missing;
    }

    public static Set<String> getRequiredResources(DataJob job) {
        ExecutionUnit unit = job.getReaderUnit();
        Set<String> resourceSet = new HashSet<>();
        addResources(unit, resourceSet);
        return resourceSet;
    }

    private static void addResources(ExecutionUnit unit, Set<String> resourceSet) {

        if (unit != null) {
            String resourceID = unit.getExecutorProvider().getResourceID();
            if (resourceID != null) {
                resourceSet.add(resourceID);
            }

            for (int i = 0; i < unit.getChildCount(); i++) {
                ExecutionUnit e = unit.getChildAt(i);
                addResources(e, resourceSet);
            }
        }

    }

    public static boolean autoCorrectResourcesMismatches(DataJob job) {

        ResourceFixPanel fixPanel = new ResourceFixPanel(job);

        DialogDescriptor d = new DialogDescriptor(fixPanel, "Fix Missing Resources");

        if (NotifyDescriptor.OK_OPTION.equals(DialogDisplayer.getDefault().notify(d))) {
            Map<String, String> reMap = fixPanel.getReMapppingMap();
            remap(job.getReaderUnit(), reMap);
            return true;
        }
        return false;
    }

    //move this remap logic to common place
    private static void remap(ExecutionUnit unit, Map<String, String> remap) {
        if (unit != null) {
            String resourceID = unit.getExecutorProvider().getResourceID();
            if (resourceID != null) {
                if (remap.containsKey(resourceID)) {
                    String s = remap.get(resourceID);
                    if (s != null) {
                        unit.getExecutorProvider().setResourceId(s);
                    }
                }
            }

            for (int i = 0; i < unit.getChildCount(); i++) {
                ExecutionUnit e = unit.getChildAt(i);
                remap(e, remap);
            }
        }
    }

}
