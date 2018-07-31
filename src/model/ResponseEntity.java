package model;

public class ResponseEntity {
	public static String failMsg = "{\"success\":\"false\"}";
	private int status;
	private String message;
	private String data;
	
	public ResponseEntity() {
		
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
	
	
	@Override
	public String toString() {
		return "{"
				+ "\"status\":" + this.status+","
				+ "\"message\":\""+this.message+"\""+","
				+ "\"data\":"+ this.data
				+ "}";
	}
	
}
