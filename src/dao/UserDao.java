package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jws.soap.SOAPBinding.Use;

import org.apache.catalina.tribes.transport.RxTaskPool;

import model.User;

public class UserDao extends BaseDao {
	private Connection conn;
	private PreparedStatement pstm;
	
	
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
	 * ͨ���û���Ѱ���û�
	 * @param username
	 * @return
	 */
	public User getUserByname(String username) {
		User user = null;
		String sql = null;
		ResultSet rs = null;
		try {
			conn = this.getConn();
			if(username.contains("@")) {
				sql = "select * from user where email = ?";
			}else {
				sql = "select * from user where phone = ?";
			}
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, username);
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
	 * �û�ע��
	 * @param user
	 * @return
	 */
	public int register(User user) {
		if(getUserByname(user.getEmail())==null&&getUserByname(user.getPhone())==null) {
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
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 1;
		}else {
			return 0;
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
