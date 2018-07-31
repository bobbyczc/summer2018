package model;

public class News {
	private int id;
	private String type;
	private String title;
	private String url;
	private String content;
	private String date;
	private String source;
	
	public News() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Override
	public String toString() {
		return "{"
				+ "\"id\":"+"\""+this.id+"\""+","
				+ "\"type\":"+"\""+this.type+"\""+","
				+ "\"title\":"+"\""+this.title+"\""+","
				+ "\"url\":"+"\""+this.url+"\""+","
//				+ "\"content\":"+"\""+this.content+"\""+","
				+ "\"date\":"+"\""+this.date+"\""+","
				+ "\"source\":"+"\""+this.source+"\""
				+ "}" ;
	}
	
}
