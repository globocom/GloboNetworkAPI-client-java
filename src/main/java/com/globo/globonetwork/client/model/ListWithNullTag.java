package com.globo.globonetwork.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.xml.GenericXml;

public class ListWithNullTag<T> extends GenericXml {

    public ListWithNullTag(String tagName) {
        super.name = tagName;
        setValues(null);
    }
    
    public String superName() {
        return super.name;
    }

    @SuppressWarnings("unchecked")
    public List<T> getValues() {
        List<T> list = new ArrayList<T>();
        if (this.get(super.name) != null) {
            for (ObjectWithNullTag<T> value : (List<ObjectWithNullTag<T>>) this.get(super.name)) {
                list.add(value.getValue());
            }
            if (!list.isEmpty() && list.get(0) == null) {
                // this is the trick to have null tag
                return new ArrayList<T>();
            }
        }
        return list;
    }

    public void setValues(List<T> listOfValues) {
        List<ObjectWithNullTag<T>> internallistOfValues = new ArrayList<ObjectWithNullTag<T>>();
        if (listOfValues == null || listOfValues.isEmpty()) {
            internallistOfValues.add(new ObjectWithNullTag<T>(null));
        } else {
            for (T value : listOfValues) {
                if (value != null) {
                    internallistOfValues.add(new ObjectWithNullTag<T>(value));
                }
            }
        }
        this.set(super.name, internallistOfValues);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object x) {
        if (x == null) {
            return false;
        } else if (!(x instanceof ListWithNullTag)) {
            return false;
        }
        return this.getValues().equals(((ListWithNullTag<T>) x).getValues());
    }
}
