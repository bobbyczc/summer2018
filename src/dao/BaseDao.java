package dao;

import java.sql.*; 


public class BaseDao {
    public static final String DBURL = "jdbc:mysql://138.68.62.57:3306/webtext?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String UNAME = "root";
    public static final String PWD = "MyNewPass4!";


    public Connection getConn()throws  ClassNotFoundException, SQLException{
        Class.forName(DRIVER);
        Connection conn = DriverManager.getConnection(DBURL,UNAME,PWD);
        return conn;
    }

    public void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs ) {
  
        if(rs != null){
            try { rs.close();} catch (SQLException e) {e.printStackTrace();}
        }

        if(pstmt != null){
            try { pstmt.close();} catch (SQLException e) {e.printStackTrace();}
        }
        if(conn != null){
            try { conn.close();} catch (SQLException e) {e.printStackTrace();}
        }
    }

    public int executeSQL(String preparedSql,String[] param) {
        Connection        conn  = null;
        PreparedStatement pstmt = null;
        int               num   = 0;

        try {
            conn = getConn();                              
            pstmt = conn.prepareStatement(preparedSql);    
            if( param != null ) {
                for( int i = 0; i < param.length; i++ ) {
                    pstmt.setString(i+1, param[i]);
                }
            }
            num = pstmt.executeUpdate();                    
        } catch (ClassNotFoundException e) {
            e.printStackTrace();                            
        } catch (SQLException e) {
            e.printStackTrace();                            
        } finally {
            closeAll(conn,pstmt,null);                     
        }
        return num;
    }
    public ResultSet getResultSetBySql(String sql,String[] param){
        ResultSet rs = null;
        try {
            Connection conn = getConn();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if( param != null ) {
                for( int i = 0; i < param.length; i++ ) {
                    pstmt.setString(i+1, param[i]);         
                }
            }
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs;
    }

}
