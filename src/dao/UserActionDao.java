package dao;

import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ietf.jgss.GSSManager;

import model.UserAction;
import utils.TransUtil;
import utils.TransUtil.UpdateMode;

public class UserActionDao extends BaseDao{
	private Connection conn;
	private PreparedStatement pstm;

	/**
	 * 根据用户ID获取用户行为
	 * @param userid
	 * @return
	 */
	public UserAction getUserActionByUid(int userid){
		ResultSet resultSet;
		UserAction action = new UserAction();
		try {
			String sql = "select * from user_action where user_id=?";
			conn = this.getConn();		
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, userid);
			resultSet = pstm.executeQuery();
			if(resultSet.next()) {
				action.setId(resultSet.getInt("id"));
				action.setUserid(resultSet.getInt("user_id"));
				action.setTopics(TransUtil.strToList(resultSet.getString("topics")));
				action.setViewList(TransUtil.strToList(resultSet.getString("news_viewed")));
				action.setCollectionList(TransUtil.strToList(resultSet.getString("news_collected")));
				action.setWarnOn(resultSet.getInt("warn_on"));
			}
			conn.close();
			pstm.close();
			resultSet.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return action;
	}
	/**
	 * 获取所有用户行为
	 * @return
	 */
	public List<UserAction> getAllUserAction(){
		ResultSet resultSet;
		List<UserAction> actions = new ArrayList<>();
		try {
			String sql = "select * from user_action";
			conn = this.getConn();		
			pstm = conn.prepareStatement(sql);
			resultSet = pstm.executeQuery();
			while(resultSet.next()) {
				UserAction action = new UserAction();
				action.setId(resultSet.getInt("id"));
				action.setUserid(resultSet.getInt("user_id"));
				action.setTopics(TransUtil.strToList(resultSet.getString("topics")));
				action.setViewList(TransUtil.strToList(resultSet.getString("news_viewed")));
				action.setCollectionList(TransUtil.strToList(resultSet.getString("news_collected")));
				action.setWarnOn(resultSet.getInt("warn_on"));
				actions.add(action);
			}
			conn.close();
			pstm.close();
			resultSet.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actions;
	}
	/**
	 * 用户注册时在user_action表添加一条记录
	 * @param userid
	 */
	public void addRecord(int userid) {
		try {
			conn = this.getConn();
			String sql= "insert into user_action(user_id) values(?)";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, userid);
			pstm.executeUpdate();
			conn.close();
			pstm.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取用户收藏
	 * @param userid
	 * @return
	 */
	public String getUserCollectionsStr(int userid){
		ResultSet resultSet;
		String news_collected = null;
		try {
			String sql = "select news_collected from user_action where user_id ="+ userid;
			conn = this.getConn();		
			pstm = conn.prepareStatement(sql);
			resultSet = pstm.executeQuery();
			if(resultSet.next()) {
				news_collected = resultSet.getString("news_collected");
			}
			conn.close();
			pstm.close();
			resultSet.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return news_collected;
	}
	
	/**
	 * 判断新闻是否已经被用户收藏
	 * @param userid
	 * @param newsid
	 * @return
	 */
	public boolean isCollected(int userid, int newsid) {
		return getUserCollectionsStr(userid).contains(String.valueOf(newsid));
	}
	
	/**
	 * 更新用户收藏
	 * @param userid
	 * @param newcl
	 */
	public void updateCollection(int userid, int newsid, UpdateMode mode) {
		String newcl = null;
		if(mode.equals(UpdateMode.ADD)) {
			newcl = addCollection(userid, newsid);
		}else if(mode.equals(UpdateMode.DELETE)) {
			newcl = concelCollection(userid, newsid);
		}
		try {
			String sql = "update user_action set news_collected = ? where user_id=?";
			conn = this.getConn();		
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, newcl);
			pstm.setInt(2, userid);
			pstm.executeUpdate();
			conn.close();
			pstm.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 增加用户收藏
	 * @param userid
	 * @param newsid
	 */
	private String addCollection(int userid, int newsid) {
		String collections = getUserCollectionsStr(userid);
		List<String> list = TransUtil.strToList(collections);
		if(!list.contains(String.valueOf(newsid))) {
			list.add(String.valueOf(newsid));
		}
		return TransUtil.listToStr(list);
	}
	/**
	 * 取消用户收藏
	 * @param userid
	 * @param newsid
	 * @return
	 */
	private String concelCollection(int userid, int newsid) {
		String collections = getUserCollectionsStr(userid);
		List<String> list = TransUtil.strToList(collections);
		if(list.contains(String.valueOf(newsid))) {
			list.remove(String.valueOf(newsid));
		}
		return TransUtil.listToStr(list);
	}
	
	/**
	 * 获取用户关注
	 * @param userid
	 * @return
	 */
	public String getKeyWordsStr(int userid) {
		String result = null;
		ResultSet rs = null;
		try {
			String sql = "select topics from user_action where user_id = ?";
			conn = this.getConn();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, userid);
			rs = pstm.executeQuery();
			if(rs.next()) {
				result = rs.getString("topics");
			}
			pstm.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;	
	}
	
	/**
	 * 更新用户关注
	 * @param userid
	 * @param newTps
	 */
	public void updateKeywords(int userid, String word, TransUtil.UpdateMode mode) {
		String newTps = null;
		if(mode.equals(TransUtil.UpdateMode.DELETE)) {
			newTps = removeKeyword(userid, word);
		}else if(mode.equals(TransUtil.UpdateMode.ADD)) {
			newTps = addKeywords(userid, word);
		}
		try {
			String sql = "update user_action set topics = ? where user_id = ?";
			conn = this.getConn();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, newTps);
			pstm.setInt(2, userid); 
			pstm.executeUpdate();
			pstm.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 增加用户关注
	 * @param userid
	 * @param word
	 * @return
	 */
	private String addKeywords(int userid, String word) {
		String kewords = getKeyWordsStr(userid);
		List<String> list = TransUtil.strToList(kewords);
		if(!list.contains(word)) {
			list.add(word);
		}
		return TransUtil.listToStr(list);
	}
	
	/**
	 * 取消用户关注
	 * @param userid
	 * @param word
	 * @return
	 */
	private String removeKeyword(int userid,String word) {
		String keywords = getKeyWordsStr(userid);
		List<String> list = TransUtil.strToList(keywords);
		if(list.contains(word)) {
			list.remove(word);
		}
		return TransUtil.listToStr(list);
	}
	
	/**
	 * 获取用户浏览列表
	 * @param userid
	 * @return
	 */
	public String getViewStr(int userid) {
		String views = null;
		ResultSet rs;
		try {
			conn = this.getConn();
			String sql = "select news_viewed from user_action where user_id = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, userid);
			rs = pstm.executeQuery();
			if(rs.next()) {
				views = rs.getString("news_viewed");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return views;
	}
	/**
	 * 增加用户浏览
	 * @param userid
	 * @param newsid
	 * @return
	 */
	private String addViewed(int userid, int newsid) {
		String views = getViewStr(userid);
		List<String> list = TransUtil.strToList(views);
		String newsstr = String.valueOf(newsid);
		if(list.contains(newsstr)) {
			list.remove(newsstr);
		}
		list.add(newsstr);
		return TransUtil.listToStr(list);
	}
	
	/**
	 * 删除用户浏览
	 * @param userid
	 * @return
	 */
	private String deleteAThird(int userid) {
		String views = getViewStr(userid);
		List<String> list = TransUtil.strToList(views);
		int index = list.size()/3;
		for(int i = 0; i < index ; i++) {
			list.remove(i);
		}
		return TransUtil.listToStr(list);
	}
	/**
	 * 更新用户浏览
	 * @param userid
	 * @param newsid
	 * @param mode
	 */
	public void updateViewList(int userid, int newsid, UpdateMode mode) {
		String newViews = null;
		if(mode.equals(TransUtil.UpdateMode.DELETE)) {
			newViews = deleteAThird(userid);
		}else if(mode.equals(TransUtil.UpdateMode.ADD)) {
			newViews = addViewed(userid, newsid);				
		}
		try {
			String sql = "update user_action set news_viewed = ? where user_id = ?";
			conn = this.getConn();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, newViews);
			pstm.setInt(2, userid); 
			pstm.executeUpdate();
			pstm.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取开启了预警提示的用户id
	 * @return
	 */
	public List<Integer> getWarnedUid(){
		ResultSet resultSet;
		List<Integer> result = new ArrayList<>();
		try {
			String sql = "select user_id from user_action where warn_on = 1";
			conn = this.getConn();		
			pstm = conn.prepareStatement(sql);
			resultSet = pstm.executeQuery();
			while(resultSet.next()) {
				result.add(resultSet.getInt("user_id"));
			}
			conn.close();
			pstm.close();
			resultSet.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取所有用户ID
	 * @return
	 */
	public List<Integer> getUserIds(){
		ResultSet resultSet;
		List<Integer> result = new ArrayList<>();
		try {
			String sql = "select user_id from user_action";
			conn = this.getConn();		
			pstm = conn.prepareStatement(sql);
			resultSet = pstm.executeQuery();
			while(resultSet.next()) {
				result.add(resultSet.getInt("user_id"));
			}
			conn.close();
			pstm.close();
			resultSet.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 判断用户是否开启了预警提示
	 * @param userid
	 * @return
	 */
	public int isWarnOn(int userid) {
		ResultSet resultSet;
		int result = 0;
		try {
			String sql = "select warn_on from user_action where user_id = ?";
			conn = this.getConn();		
			pstm = conn.prepareStatement(sql);
			resultSet = pstm.executeQuery();
			if(resultSet.next()) {
				result = resultSet.getInt("warn_on");
			}
			conn.close();
			pstm.close();
			resultSet.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 更新用户预警开启状态
	 * @param userid
	 * @param state
	 */
	public void changeWarnState(int userid,int state) {
		
		try {
			String sql = "update user_action set warn_on = ? where user_id = ?";
			conn = this.getConn();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, state);
			pstm.setInt(2, userid); 
			pstm.executeUpdate();
			pstm.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	
}
