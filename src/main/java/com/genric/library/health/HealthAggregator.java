package com.genric.library.health;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class HealthAggregator {

    private ConcurrentHashMap<String, Boolean> resourceHealthMap = new ConcurrentHashMap<>();

    HealthAggregator(){
    }

    public void setResource(String resourceName, boolean health){
        resourceHealthMap.put(resourceName, health);
    }

    public boolean isHealthy(){
        if(resourceHealthMap.isEmpty())
            return false;
        for (Boolean value: resourceHealthMap.values()) {
            if(!value)
                return false;
        }
        return true;
    }
}
