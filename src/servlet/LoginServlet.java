package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.ResponseEntity;
import service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		response.setHeader("content-type","text/html;charset=UTF-8");
		String username = request.getParameter("username");
		System.out.println(username);
		String password =request.getParameter("password");
		UserService service = new UserService();
		PrintWriter out = response.getWriter();
		ResponseEntity entity = new ResponseEntity();
		int result = service.login(username, password);
		if(result==-1) {
			entity.setStatus(-1);
			entity.setMessage("用户不存在");
			entity.setData(ResponseEntity.failMsg);
		}else if(result==0) {
			entity.setStatus(0);
			entity.setMessage("密码不正确");
			entity.setData(ResponseEntity.failMsg);
		}else {
			entity.setStatus(1);
			entity.setMessage("登录成功");
			entity.setData("{"
					+ "\"userid\":"+ result
					+"}");
		}
		out.write(entity.toString());
		out.flush();
		out.close();
	}

}
