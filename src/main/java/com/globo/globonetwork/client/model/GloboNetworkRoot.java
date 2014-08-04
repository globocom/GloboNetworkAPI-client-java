package com.globo.globonetwork.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.xml.GenericXml;

public class GloboNetworkRoot<T> extends GenericXml {

	private List<T> objectList;
	
	public List<T> getObjectList() {
		return this.objectList;
	}
	
	public void setObjectList(List<T> objectList) {
		this.objectList = objectList;
	}
	
	public GloboNetworkRoot() {
		this.name = "networkapi";
		this.objectList = new ArrayList<T>();
	}
	
	public GloboNetworkRoot(String elementName, T obj) {
		this();
		this.getObjectList().add(obj);
		this.set(elementName, obj);
	}
	
	/**
	 * Return first object in list or <code>null</code> if list is empty
	 * @return
	 */
	public T getFirstObject() {
		if (this.getObjectList() == null || this.getObjectList().isEmpty()) {
			return null;
		}
		return this.getObjectList().get(0);
	}
}