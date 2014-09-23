package com.globo.globonetwork.client.model;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.ArrayMap;
import com.google.api.client.xml.GenericXml;

public class ListWithNullTag<T> extends GenericXml {
    
    protected ListWithNullTag(String tagName) {
        super.name = tagName;
        setValues(null);
    }
    
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

    @SuppressWarnings("unchecked")
    protected Class<T> getBaseClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    private T convert(String rawValue) {
        if (rawValue == null) {
            return null;
        }
        Class<T> baseClass = getBaseClass();
        if (baseClass == Integer.class) {
            return (T) Integer.valueOf(rawValue);
        } else if (baseClass == Long.class) {
            return (T) Long.valueOf(rawValue);
        } else if (baseClass == String.class) {
            return (T) rawValue;
        }
        throw new IllegalArgumentException("Unsupported type: " + baseClass);
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
