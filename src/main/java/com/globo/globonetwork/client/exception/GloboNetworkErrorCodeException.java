package com.globo.globonetwork.client.exception;

public class GloboNetworkErrorCodeException extends GloboNetworkException {

	private static final long serialVersionUID = -7272459136902597384L;

	private int code;
	
	private String description;

	public GloboNetworkErrorCodeException(int code, String description) {
		super(code + ":" + description);
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static final int EQUIPMENT_NOT_REGISTERED = 117;
	public static final int IP_NOT_REGISTERED = 119;
	public static final int VIP_NOT_REGISTERED = 152;
	public static final int NO_PARAMETERS = 287;
}
