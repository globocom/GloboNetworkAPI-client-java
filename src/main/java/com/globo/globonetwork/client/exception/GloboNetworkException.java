package com.globo.globonetwork.client.exception;

public class GloboNetworkException extends Exception {

	private static final long serialVersionUID = -7948528759682667770L;

	public GloboNetworkException(String msg) {
		super(msg);
	}

	public GloboNetworkException(String msg, Throwable e) {
		super(msg, e);
	}

}
