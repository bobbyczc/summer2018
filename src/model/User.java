package model;

public class User {
	private int id;
	private String email;
	private String password;
	private String phone;
	private String nickname;
	public User() {
		
	}
	public User(String email, String password, String phone,String nickname) {
		super();
		this.setEmail(email);
		this.setPassword(password);
		this.setPhone(phone);
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
	public String toString() {
		return "{"
				+ "\"nickname\":"+"\""+this.nickname+"\""+","
				+ "\"email\":"+"\""+this.email+"\""+","
				+ "\"phone\":"+"\""+this.phone+"\""
				+ "}" ;
	}
}
