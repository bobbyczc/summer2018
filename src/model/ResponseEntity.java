package model;

import java.util.concurrent.CountDownLatch;

public class ResponseEntity {
	public static String failMsg = "{\"success\":\"false\"}";
	private int status;
	private String message;
	private int count;
	private String data;
	
	public ResponseEntity() {
		this.count = 0;
	}
	public ResponseEntity(int status, String message) {
		this.setStatus(status);
		this.setMessage(message);
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "{"
				+ "\"status\":" + this.status+","
				+ "\"message\":\""+this.message+"\""+","
				+ "\"count\":"+this.count+","
				+ "\"data\":"+ this.data
				+ "}";
	}
	
	
}
