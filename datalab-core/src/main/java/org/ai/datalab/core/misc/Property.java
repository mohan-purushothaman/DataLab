/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.misc;

import java.util.Objects;

/**
 *
 * @author Mohan Purushothaman
 */
public class Property {

    public static final Property THREAD_COUNT = new Property("THREAD_COUNT","Thread Count", "thread to use", Integer.class);
    public static final Property TIMEOUT_IN_MILLI_SECONDS = new Property("TIMEOUT_IN_MILLI_SECONDS","Processing Timeout", "processing timeout in milliseconds", Long.class);

    private final String name;
    private final String description;
    private final String shortDesc;
    private final Class clazz;

    public Property(String name,String description, String shortDesc, Class clazz) {
        this.name=name;
        this.description = description;
        this.shortDesc = shortDesc;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    
    
    public String getDescription() {
        return description;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public Class getClazz() {
        return clazz;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Property other = (Property) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    
    
    
}
