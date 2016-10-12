package main.exception;

public class PinsErrorExcpetion extends RuntimeException {

	private static final long serialVersionUID = 1959828909654287395L;

	private static final String message = "球瓶數量不符";
	
	public PinsErrorExcpetion() {
		super(message);
	}
	
	public PinsErrorExcpetion(Throwable throwable) {
		super(message, throwable);
	}
}
