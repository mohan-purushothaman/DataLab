/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.misc;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class PropertyHandler {

    private final Map<Property, Object> properties = new LinkedHashMap<>();
    
    private final Map<String,PropertyChangeListener> listenerMap=new HashMap<>();

    public final Object getProperty(Property property) {
        return properties.get(property);
    }
    
    public <T> T getProperty(Property property,Class<T> clazz){
        return clazz.cast(getProperty(property));
    }
    public final void setProperty(Property property, Object value){
        setProperty(property, value, null);
    }

    public final void setProperty(Property property, Object value,PropertyChangeListener listener) {
        assert property.getClazz().isInstance(value);
        properties.put(property, value);
        firePropertyChanged(property.getName(),value);
        if(listener!=null){
            listenerMap.put(property.getName(), listener);
        }
    }
    
    public void copyProperties(PropertyHandler p){
        this.properties.putAll(p.properties);
    }

    public Map<Property, Object> getProperties() {
        return properties;
    }
    
    
    
    protected void firePropertyChanged(String name,Object value){
        PropertyChangeListener listener = listenerMap.get(name);
        if(listener!=null){
            listener.valueUpdated(value);
        }
    };
    
    
}

