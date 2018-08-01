package dao;

import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.TransUtil;
import utils.TransUtil.UpdateMode;

public class UserActionDao extends BaseDao{
	private Connection conn;
	private PreparedStatement pstm;
	
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
	 * �ж������ڲ����û��ղ��б�
	 * @param userid
	 * @param newsid
	 * @return
	 */
	public boolean isCollected(int userid, int newsid) {
		return getUserCollectionsStr(userid).contains(String.valueOf(newsid));
	}
	
	/**
	 * �����û��ղ���
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
	 * �û��ղ�ʱ����
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
	 * �û�ȡ���ղ�ʱ
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
	 * ��ȡ�û���ע�ؼ���
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
	 * ��ע�ؼ��ʸı�ʱ����
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
	 * ����û���ע�ؼ���
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
	 * ȡ����ע
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
	
	private String deleteAThird(int userid) {
		String views = getViewStr(userid);
		List<String> list = TransUtil.strToList(views);
		int index = list.size()/3;
		for(int i = 0; i < index ; i++) {
			list.remove(i);
		}
		return TransUtil.listToStr(list);
	}
	
	public void deleteviews(int userid) {
		
	}
	
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
	
		
	
}
