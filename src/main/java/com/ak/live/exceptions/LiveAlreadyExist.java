package com.ak.live.exceptions;

public class LiveAlreadyExist extends RuntimeException {
	private static final long serialVersionUID = 87214071728310561L;

	public LiveAlreadyExist(String string) {
		super("Movie is already exists with same name and language");
	}
}
