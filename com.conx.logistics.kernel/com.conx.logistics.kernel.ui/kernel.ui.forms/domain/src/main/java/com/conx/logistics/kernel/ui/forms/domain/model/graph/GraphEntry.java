package com.conx.logistics.kernel.ui.forms.domain.model.graph;

import java.util.Map.Entry;

public class GraphEntry implements Entry<String, String> {

    private final String key;
    private String value;
    
    public GraphEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String setValue(String value) {
        String aux = this.value;
        this.value = value;
        return aux;
    }

}
