/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.visual.resource;

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.ai.datalab.core.resource.ResourceFactory;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.core.resource.ResourcePoolQualifier;

/**
 *
 * @author Mohan Purushothaman
 */
public class ResourceStore {

    public static final String RESOURCE_PANEL_ID = "Resources";

    public static final String RESOURCE_PATH = "Resources/Store";

    public static final String EXTENSION = "xml";

    static {
        try {
            loadResources();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadResources() throws Exception {
        FileObject store = FileUtil.getConfigFile(RESOURCE_PATH);
        for (FileObject obj : store.getChildren()) {
            try (InputStream i = obj.getInputStream()) {
                Object o = getStream().fromXML(i);
                if (o instanceof ResourcePool) {
                    ResourcePool pool = (ResourcePool) o;
                    pool.init();
                    ResourceFactory.addResourcePool(pool);
                }
            }
        }

    }

    public static void addResourcePool(ResourcePool pool) throws Exception {
        if (ResourceFactory.getResourcePool(pool.getResourceId()) != null) {
            throw new Exception(pool.getResourceId() + " already exists");
        }
        FileObject store = FileUtil.getConfigFile(RESOURCE_PATH);
        FileObject r = FileUtil.createData(store, FileUtil.findFreeFileName(store, FileNameUtil.normailzeName(pool.getResourceId()), EXTENSION) + "." + EXTENSION);
        try (OutputStream o = r.getOutputStream()) {
            getStream().toXML(pool, o);
        }

        ResourceFactory.addResourcePool(pool);
    }

    public static void deleteResourcePool(ResourcePool pool) throws Exception {
        if (ResourceFactory.getResourcePool(pool.getResourceId()) == null) {
            throw new Exception(pool.getResourceId() + " not exists");
        }
        FileObject store = FileUtil.getConfigFile(RESOURCE_PATH);
        for (FileObject obj : store.getChildren()) {
            try (InputStream i = obj.getInputStream()) {
                Object o = getStream().fromXML(i);
                if (o instanceof ResourcePool) {
                    if (pool.getResourceId().equals(((ResourcePool) o).getResourceId())) {
                        obj.delete();
                    }
                }
            }
        }
        
    }

    public static FileObject findFile(ResourcePool pool) throws Exception {
        FileObject store = FileUtil.getConfigFile(RESOURCE_PATH);
        for (FileObject obj : store.getChildren()) {
            try (InputStream i = obj.getInputStream()) {
                Object o = getStream().fromXML(i);
                if (o instanceof ResourcePool) {
                    if (pool.getResourceId().equals(((ResourcePool) o).getResourceId())) {
                        return obj;
                    }
                }
            }
        }
        throw new Exception("Not able to find the resource file");
    }

    public static void updateResource(ResourcePool pool) throws Exception {
        FileObject file = findFile(pool);
        try (OutputStream o = file.getOutputStream()) {
            getStream().toXML(pool, o);
        }
    }

    public static final ResourcePool getResourcePool(String id) {
        return ResourceFactory.getResourcePool(id);
    }

    public static Collection<ResourcePool> getResourceList() {
        return ResourceFactory.getResourceList();
    }

    public static List<ResourcePool> getSortedResourceList(Comparator<ResourcePool> c) {
        if (c == null) {
            c = new Comparator<ResourcePool>() {
                @Override
                public int compare(ResourcePool o1, ResourcePool o2) {
                    return o1.getResourceId().compareToIgnoreCase(o2.getResourceId());
                }
            };
        }
        ArrayList<ResourcePool> list = new ArrayList<>(getResourceList());
        Collections.sort(list, c);

        return list;
    }

    public static <V> Collection<ResourcePool> findResourcePools(Class<V> expectedResourceClazz, ResourcePoolQualifier<V> qualifier) {
        return ResourceFactory.findResourcePools(expectedResourceClazz, qualifier);
    }

    public static void refresh() {
        try {
            loadResources();
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }
    }

    private static XStream getStream() {
        XStream xStream = new XStream();
        return xStream;
    }

}
