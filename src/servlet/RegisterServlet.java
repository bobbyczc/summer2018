package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.ResponseEntity;
import model.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String nickname = request.getParameter("nickname");
		UserDao dao = new UserDao();
		ResponseEntity entity = new ResponseEntity();
		if(dao.getUserByPhone(phone)!=null) {
			entity.setStatus(0);
			entity.setMessage("手机号已被注册");
		}else if(dao.getUserByEmail(email)!=null) {
			entity.setStatus(-1);
			entity.setMessage("邮箱已被注册");
		}else if(dao.getUserByNickname(nickname)!=null) {
			entity.setStatus(-2);
			entity.setMessage("昵称已被注册");
		}else {
			User user = new User(email, password, phone,nickname);
			dao.register(user);
			entity.setStatus(1);
			entity.setMessage("注册成功");
		}
		writer.write(entity.toString());
		writer.flush();
		writer.close();
	}

}
