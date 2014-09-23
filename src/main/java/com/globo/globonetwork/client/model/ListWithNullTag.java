package com.globo.globonetwork.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.ArrayMap;
import com.google.api.client.xml.GenericXml;

public class ListWithNullTag<T> extends GenericXml {
    
    public ListWithNullTag(String tagName) {
        super.name = tagName;
        setValues(null);
    }
    
//    public ListWithNullTag() {
//        setValues(null);
//    }
    
    @SuppressWarnings("unchecked")
    public List<T> getValues() {
        List<ArrayMap<String, String>> internalList = getInternalList();
        List<T> result = new ArrayList<T>();
        for (ArrayMap<String, String> a: internalList) {
            String rawValue = a.get("text()");
            if (rawValue != null) {
                result.add(convert(rawValue));
            }
        }
        return result;
    }
    
    private T convert(String rawValue) {
        return (T) Integer.valueOf(rawValue);
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<ArrayMap<String, String>> getInternalList() {
        List l1 = null;
        if (!this.getUnknownKeys().isEmpty()) {
            l1 = (List) this.getUnknownKeys().values().iterator().next();
        }
        if (l1 == null) {
            l1 = new ArrayList();
            this.getUnknownKeys().put(super.name, l1);
        }
        ArrayMap<String, List> a2 = null;
        if (!l1.isEmpty()) {
            a2 = (ArrayMap) l1.get(0);
        }
        return l1;
    }
    
    public void setValues(List<T> listOfValues) {
        List<ArrayMap<String, String>> internalList = getInternalList();
        internalList.clear();
        if (listOfValues == null || listOfValues.isEmpty()) {
            ArrayMap<String, String> a = ArrayMap.create();
            a.put("text()", null);
            internalList.add(a);
        } else {
            for (T value : listOfValues) {
                if (value != null) {
                    ArrayMap<String, String> a = ArrayMap.create();
                    a.put("text()", value.toString());
                    internalList.add(a);
                }
            }
        }
    }
}
