package com.globo.globonetwork.client.model;

import java.util.List;

import com.google.api.client.util.Key;
import com.google.api.client.xml.GenericXml;

public class Ip extends GenericXml {

	@Key
	private Integer oct1;
	@Key
	private Integer oct2;
	@Key
	private Integer oct3;
	@Key
	private Integer oct4;
	@Key("equipamentos")
	private List<String> equipments;
	@Key
	private Long id;
	
	public Ip() {
		this.name = "ip";
	}

	public Integer getOct1() {
		return oct1;
	}
	public void setOct1(Integer oct1) {
		this.oct1 = oct1;
	}
	public Integer getOct2() {
		return oct2;
	}
	public void setOct2(Integer oct2) {
		this.oct2 = oct2;
	}
	public Integer getOct3() {
		return oct3;
	}
	public void setOct3(Integer oct3) {
		this.oct3 = oct3;
	}
	public Integer getOct4() {
		return oct4;
	}
	public void setOct4(Integer oct4) {
		this.oct4 = oct4;
	}
	public List<String> getEquipments() {
		return equipments;
	}
	public void setEquipments(List<String> equipments) {
		this.equipments = equipments;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public static class Ipv4 extends Ip {
		public Ipv4() {
			this.name = "ipv4";
		}
	}
	
}
