package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import model.News;
import model.User;

public class NewsDao extends BaseDao{
	private Connection conn;
	private PreparedStatement pstm;
	
	
	public List<News> getAllNews(){
		List<News> list = new ArrayList<>();
		String sql;
		ResultSet rs;
		try {
			conn = this.getConn();
			sql = "select * from news";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()) {
				News news = new News();
				System.out.println(rs.getInt("id"));
				news.setId(rs.getInt("id"));
				news.setUrl(rs.getString("url"));
				news.setType(rs.getString("type"));
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setSource(rs.getString("source"));
				news.setDate(rs.getString("date"));
				list.add(news);
			}
			conn.close();
			pstm.close();
			rs.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 
	 * @param type
	 * @return
	 */
	public List<News> getNewsByType(String type,int page){
		List<News> list = new ArrayList<>();
		String sql;
		ResultSet rs;
		try {
			conn = this.getConn();
			sql = "select * from news where type = ? order by date desc limit "+(page-1)*8+","+ 8;
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, type);
			rs = pstm.executeQuery();
			while(rs.next()) {
				News news = new News();
				System.out.println(rs.getInt("id"));
				news.setId(rs.getInt("id"));
				news.setUrl(rs.getString("url"));
				news.setType(rs.getString("type"));
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setSource(rs.getString("source"));
				news.setDate(rs.getString("date"));
				list.add(news);
			}
			conn.close();
			pstm.close();
			rs.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 根据关键词查找最新的number条新闻
	 * @param keyword
	 * @param number
	 * @return
	 */
	public List<News> getTopNewsByKeyword(String keyword,int number){
		List<News> list = new ArrayList<>();
		int index = 0;
		String sql;
		ResultSet rs;
		try {
			conn = this.getConn();
			sql = "select * from news where content like \'%"+keyword+"%\' order by date desc";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()) {
				index ++;
				News news = new News();
				System.out.println(rs.getInt("id"));
				news.setId(rs.getInt("id"));
				news.setUrl(rs.getString("url"));
				news.setType(rs.getString("type"));
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setSource(rs.getString("source"));
				news.setDate(rs.getString("date"));
				list.add(news);
				if(index==number) {
					break;
				}
			}
			conn.close();
			pstm.close();
			rs.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 通过关键词查找新闻
	 * @param keyword
	 * @return
	 */
	public List<News> getNewsByKeyword(String keyword, int page){
		List<News> list = new ArrayList<>();
		String sql;
		ResultSet rs;
		try {
			conn = this.getConn();
			sql = "select * from news "
					+ "where content like \'%"+keyword+"%\' "
							+ "order by date desc "
							+ "limit "+(page-1)*8+","+ 8;
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()) {
				News news = new News();
				System.out.println(rs.getInt("id"));
				news.setId(rs.getInt("id"));
				news.setUrl(rs.getString("url"));
				news.setType(rs.getString("type"));
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setSource(rs.getString("source"));
				news.setDate(rs.getString("date"));
				list.add(news);
			}
			conn.close();
			pstm.close();
			rs.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 根据关键词获取全部新闻
	 * @param keyword
	 * @return
	 */
	public List<News> getAllNewsByKeyword(String keyword){
		List<News> list = new ArrayList<>();
		String sql;
		ResultSet rs;
		try {
			conn = this.getConn();
			sql = "select * from news "
					+ "where content like \'%"+keyword+"%\' ";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()) {
				News news = new News();
				System.out.println(rs.getInt("id"));
				news.setId(rs.getInt("id"));
				news.setUrl(rs.getString("url"));
				news.setType(rs.getString("type"));
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setSource(rs.getString("source"));
				news.setDate(rs.getString("date"));
				list.add(news);
			}
			conn.close();
			pstm.close();
			rs.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 根据type获取新闻数量
	 * @param type
	 * @return
	 */
	public int getNewsCountByType(String type) {
		int result = 0;
		String sql = null;
		ResultSet rs;
		try {
			conn = this.getConn();
			sql = "select count(id) as count from news where type = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, type);
			rs = pstm.executeQuery();
			if(rs.next()) {
				result = rs.getInt("count");
			}
			conn.close();
			pstm.close();
			rs.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * 根据关键词获取新闻数量
	 * @param keyword
	 * @return
	 */
	public int getNewsCountByKeyWord(String keyword) {
		int result = 0;
		String sql = null;
		ResultSet rs;
		try {
			conn = this.getConn();
			sql = "select count(id) as count from news where content like \'%"+keyword+"%\'";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			if(rs.next()) {
				result = rs.getInt("count");
			}
			conn.close();
			pstm.close();
			rs.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 根据时间或者关键词获取新闻条数
	 * @param parm
	 * @return
	 */
	private int getNewsCountByTimeOrKeyword(String[] parm) {
		int result = 0;
		String sql = null;
		ResultSet rs;
		try {
			conn = this.getConn();
			if(parm.length==1) {
				sql = "select count(id) as count from news where date = ?";
				
			}else if(parm.length==2){
				sql = "select count(id) as count from news where date = ? and content like \'%"+parm[1]+"%\'";
			}
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, parm[0]);
			
			rs = pstm.executeQuery();
			if(rs.next()) {
				result = rs.getInt("count");
			}
			conn.close();
			pstm.close();
			rs.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * ͨ��ʱ��ɸѡ����
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<News> getNewsByTime(String startDate, String endDate) {
		List<News> list = new ArrayList<>();
		String sql;
		ResultSet rs;
		try {
			conn = this.getConn();
			sql = "select * from news where date between ? and ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, startDate);
			pstm.setString(2, endDate);
			rs = pstm.executeQuery();
			while(rs.next()) {
				News news = new News();
				news.setId(rs.getInt("id"));
				news.setUrl(rs.getString("url"));
				news.setType(rs.getString("type"));
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setSource(rs.getString("source"));
				news.setDate(rs.getString("date"));
				list.add(news);
			}
			conn.close();
			pstm.close();
			rs.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * ͨ������ɸѡ����
	 * @param area
	 * @return
	 */
	public List<News> getNewsByArea(String area){
		List<News> list = new ArrayList<>();
		String sql;
		ResultSet rs;
		try {
			conn = this.getConn();
			sql = "select * from news where area = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, area);
			rs = pstm.executeQuery();
			while(rs.next()) {
				News news = new News();
				news.setId(rs.getInt("id"));
				news.setUrl(rs.getString("url"));
				news.setType(rs.getString("type"));
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setSource(rs.getString("source"));
				news.setDate(rs.getString("date"));
				list.add(news);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * ͨ根据id获取新闻
	 * @param newsid
	 * @return
	 */
	public News getNewsById(int newsid) {
		News news = new News();
		ResultSet rs;
		try {
			String sql = "select * from news where id = ?";
			conn = this.getConn();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, newsid);
			rs = pstm.executeQuery();
			if(rs.next()) {
				news.setId(rs.getInt("id"));
				news.setUrl(rs.getString("url"));
				news.setType(rs.getString("type"));
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setSource(rs.getString("source"));
				news.setDate(rs.getString("date"));
			}
			rs.close();
			conn.close();
			pstm.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return news;
	}
	 
	/**
	 * 获取关键词的时间和热度的数据
	 * @param keyword
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public HashMap<String, Float> getKeywordHotList(String keyword,String startDate,String endDate){
		HashMap<String, Float> result = new HashMap<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
	
		try {
			Date start = dateFormat.parse(startDate);
			Date end = dateFormat.parse(endDate);
			calendar.setTime(start);
			while(start.getTime()<=end.getTime()) {
				String date = dateFormat.format(calendar.getTime());
				float hot = getKeywordHotByDate(keyword, date);
				System.out.println(date+" "+hot );
				result.put(date, hot);
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				start = calendar.getTime();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取某一天的关键词热度
	 * @param keyword
	 * @param date
	 * @return
	 */
	public float getKeywordHotByDate(String keyword,String date) {
		int count = getNewsCountByTimeOrKeyword(new String[] {date,keyword});
		int total = getNewsCountByTimeOrKeyword(new String[] {date});
		if(total==0) {
			return 0;
		}else {
			float hot = Float.parseFloat(String.format("%.4f", (double)count/total));
			return hot;
		}		
	}
}
