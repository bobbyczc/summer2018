package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.ResponseEntity;

/**
 * Servlet implementation class CheckPassword
 */
@WebServlet("/CheckPassword")
public class CheckPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String password = request.getParameter("password");
		int userid = Integer.parseInt(request.getParameter("userid"));
		UserDao dao = new UserDao();
		ResponseEntity entity = new ResponseEntity();
		if(dao.checkPsw(userid, password)) {
			entity.setStatus(1);
			entity.setMessage("密码正确");
			entity.setData("{\"isCorrect\": true}");
		}else {
			entity.setStatus(0);
			entity.setMessage("密码错误");
			entity.setData("{\"isCorrect\": false}");
		}
		response.getWriter().write(entity.toString());
	}

}
