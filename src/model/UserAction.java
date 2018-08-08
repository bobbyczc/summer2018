package model;

import java.util.List;

public class UserAction {
	private int id;
	private int userid;
	private List<String> topics;
	private List<String> viewList;
	private List<String> collectionList;
	private int warnOn;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public List<String> getTopics() {
		return topics;
	}
	public void setTopics(List<String> topics) {
		this.topics = topics;
	}
	public List<String> getViewList() {
		return viewList;
	}
	public void setViewList(List<String> viewList) {
		this.viewList = viewList;
	}
	public List<String> getCollectionList() {
		return collectionList;
	}
	public void setCollectionList(List<String> collectionList) {
		this.collectionList = collectionList;
	}
	public int getWarnOn() {
		return warnOn;
	}
	public void setWarnOn(int warnOn) {
		this.warnOn = warnOn;
	}
	
}
