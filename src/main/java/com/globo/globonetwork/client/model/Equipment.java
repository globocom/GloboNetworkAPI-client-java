package com.globo.globonetwork.client.model;

import com.google.api.client.util.Key;
import com.google.api.client.xml.GenericXml;

public class Equipment extends GenericXml {
	
	@Key
	private Long id;	

	@Key("nome")
	private String name;
	
	@Key("id_tipo_equipamento")
	private Long equipmentTypeId;
	
	@Key("nome_tipo_equipamento")
	private String equipmentTypeName;
	
	@Key("id_modelo")
	private Long modelId;
	
	@Key("nome_modelo")
	private String modelName;
	
	@Key("id_marca")
	private Long brandId;
	
	@Key("nome_marca")
	private String brandName;

	@Key("id_grupo")
	private Long groupId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getEquipmentTypeId() {
		return equipmentTypeId;
	}

	public void setEquipmentTypeId(Long equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}

	public String getEquipmentTypeName() {
		return equipmentTypeName;
	}

	public void setEquipmentTypeName(String equipmentTypeName) {
		this.equipmentTypeName = equipmentTypeName;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Equipment() {
		super.name = "equipamento";
	}
	
	public String superName() {
		return super.name;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
}
