package com.globo.globonetwork.client.model;

import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;

/*
 * Use this object when you want have empty tag (like <xxx />) when
 * there is a null value
 */
public class ObjectWithNullTag<T> extends GenericData {
    @Key("text()")
    private T value;
    public ObjectWithNullTag(T value) {
        this.value = value;
    }
    public ObjectWithNullTag() {
        this(null);
    }
    public void setValue(T value) {
        this.value = value;
    }
    public T getValue() {
        return this.value;
    }
}
