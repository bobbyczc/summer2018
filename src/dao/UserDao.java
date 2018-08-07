package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jws.soap.SOAPBinding.Use;


import model.User;

public class UserDao extends BaseDao {
	private Connection conn;
	private PreparedStatement pstm;
	
	/**
	 * 登录
	 * @param username
	 * @param passwrod
	 * @return
	 */
	public int login(String username,String passwrod) {
		User user = null;
		if((user=getUserByPhone(username))==null) {
			if((user=getUserByEmail(username))==null) {
				if((user=getUserByNickname(username))==null) {
					return -1;
				}
			}
		}
		if(user.getPassword().equals(passwrod)) {
			return user.getId();
		}else {
			return 0;
		}
	}
	/**
	 * 通过id获取用户
	 * @param userId
	 * @return
	 */
	public User getUserById(int userId) {
		User user = null;
		String sql = null;
		ResultSet rs = null;
		try {
			conn = this.getConn();
			sql = "select * from user where id = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, userId);
			rs = pstm.executeQuery();
			
			if(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setNickname(rs.getString("nickname"));
			}
			conn.close();
			pstm.close();
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	/**
	 * ͨ通过昵称获取用户
	 * @param email
	 * @return
	 */
	public User getUserByEmail(String email) {
		User user = null;
		String sql = null;
		ResultSet rs = null;
		try {
			conn = this.getConn();
			sql = "select * from user where email = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, email);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setNickname(rs.getString("nickname"));
			}
			conn.close();
			pstm.close();
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
			
	}
	/**
	 * ͨ通过电话获取用户
	 * @param phone
	 * @return
	 */
	public User getUserByPhone(String phone) {
		User user = null;
		String sql = null;
		ResultSet rs = null;
		try {
			conn = this.getConn();
			sql = "select * from user where phone = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, phone);
			rs = pstm.executeQuery();
			
			if(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setNickname(rs.getString("nickname"));
			}
			conn.close();
			pstm.close();
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
			
	}
	/**
	 * ͨ通过昵称获取用户
	 * @param nickname
	 * @return
	 */
	public User getUserByNickname(String nickname) {
		User user = null;
		String sql = null;
		ResultSet rs = null;
		try {
			conn = this.getConn();
			sql = "select * from user where nickname = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, nickname);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setNickname(rs.getString("nickname"));
			}
			conn.close();
			pstm.close();
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
			
	}
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	public void register(User user) {
			String sql = null;
			try {
				conn = this.getConn();
				sql = "insert into user(email,password,phone,nickname) values(?,?,?,?)";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, user.getEmail());
				pstm.setString(2, user.getPassword());
				pstm.setString(3, user.getPhone());
				pstm.setString(4, user.getNickname());
				pstm.executeUpdate();
				conn.close();
				pstm.close();
				UserActionDao actionDao = new UserActionDao();
				actionDao.addRecord(getUserByPhone((user.getPhone())).getId());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	/**
	 * 判断用户的密码是否正确
	 * @param userid
	 * @param psw
	 * @return
	 */
	public boolean checkPsw(int userid, String psw) {
		return getUserById(userid).getPassword().equals(psw);
	}
	/**
	 * 更改密码
	 * @param userid
	 * @param newpsw
	 * @return
	 */
	public int changePsw(int userid, String newpsw) {
		String sql = null;
		try {
			conn = this.getConn();
			sql = "update user set password = ? where id = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, newpsw);
			pstm.setInt(2, userid);
			return 	pstm.executeUpdate();	
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			return -1;
		}
	}
}
