package main.exception;

public class GameOverExcpetion extends RuntimeException {

	private static final long serialVersionUID = 1959828909654287395L;
	
	private static final String message = "遊戲已結束";

	public GameOverExcpetion() {
		super(message);
	}
	
	public GameOverExcpetion(Throwable throwable) {
		super(message, throwable);
	}
}
