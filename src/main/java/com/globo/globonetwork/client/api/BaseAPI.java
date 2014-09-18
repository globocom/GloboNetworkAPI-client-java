package com.globo.globonetwork.client.api;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.google.api.client.util.ArrayMap;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.xml.GenericXml;

public class BaseAPI<T> {
	
	private final RequestProcessor transport;
	
	public BaseAPI(RequestProcessor transport) {
		this.transport = transport;
	}
	
	protected RequestProcessor getTransport() {
		if (this.transport == null) {
			throw new RuntimeException("No transport configured");
		}
		return this.transport;
	}
	
	protected <T extends GenericXml> Class<T> getBaseClass() {
		return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	protected GloboNetworkRoot<T> get(String suffixUrl) throws GloboNetworkException {
		GloboNetworkRoot<T> answer = (GloboNetworkRoot<T>) this.getTransport().get(suffixUrl, getBaseClass());
		return answer;
	}
	
	protected GloboNetworkRoot<T> post(String suffixUrl, Object payload) throws GloboNetworkException {
		GloboNetworkRoot<T> answer = (GloboNetworkRoot<T>) this.getTransport().post(suffixUrl, payload, getBaseClass());
		return answer;
	}
	
	protected GloboNetworkRoot<T> put(String suffixUrl, Object payload) throws GloboNetworkException {
		GloboNetworkRoot<T> answer = (GloboNetworkRoot<T>) this.getTransport().put(suffixUrl, payload, getBaseClass());
		return answer;
	}
	
	protected GloboNetworkRoot<T> delete(String suffixUrl) throws GloboNetworkException {
		GloboNetworkRoot<T> answer = (GloboNetworkRoot<T>) this.getTransport().delete(suffixUrl, getBaseClass());
		return answer;
	}
	
    @SuppressWarnings("rawtypes")
	protected void assignTo(GenericXml orig, GenericXml dest) {
	    if (dest == null || orig == null) {
	        return;
	    }
	    for (String key: orig.keySet()) {
            ArrayList listValue = (ArrayList) orig.get(key);
	        ArrayMap mapValue = (ArrayMap) listValue.get(0);
	        Object rawValue = mapValue.getValue(0);
	        
            FieldInfo fieldInfo = dest.getClassInfo().getFieldInfo(key);
            Object value = null;
            if (rawValue == null || fieldInfo.getType().isAssignableFrom(rawValue.getClass())) {
                value = rawValue;
            } else if (fieldInfo.getType() == Integer.class) {
                value = Integer.valueOf(String.valueOf(rawValue));
            } else if (fieldInfo.getType() == Long.class) {
                value = Long.valueOf((String.valueOf(rawValue)));
            } else if (fieldInfo.getType() == Boolean.class) {
                value = Boolean.valueOf(String.valueOf(rawValue));
            }
            fieldInfo.setValue(dest, value);
	    }
	}
	
}