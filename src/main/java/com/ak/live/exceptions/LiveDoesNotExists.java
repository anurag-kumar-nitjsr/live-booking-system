package com.ak.live.exceptions;

public class LiveDoesNotExists extends RuntimeException {
	private static final long serialVersionUID = -5385129013790060351L;

	public LiveDoesNotExists() {
		super("Movie dose not Exists");
	}
}
