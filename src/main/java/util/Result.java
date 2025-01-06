package util;

import lombok.Data;

@Data
public class Result {
	private int code;
	private String message;

	public Result() {
	}
	
	public Result(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
}
