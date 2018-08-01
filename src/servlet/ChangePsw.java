package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ResponseEntity;
import service.UserService;

/**
 * Servlet implementation class ChangePsw
 */
@WebServlet("/ChangePsw")
public class ChangePsw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePsw() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		int userid = Integer.parseInt(request.getParameter("userid"));
		String newpass = request.getParameter("newpsw");
		System.out.println(userid+ " "+ newpass);
		UserService service = new UserService();
		ResponseEntity entity = new ResponseEntity();
		if(service.changePsw(userid, newpass)==1) {
			entity.setStatus(1);
			entity.setMessage("修改成功");
			entity.setData("\"success\"");
		}else {
			entity.setStatus(-1);
			entity.setMessage("失败");
			entity.setData("\"error\"");
		}
		response.getWriter().write(entity.toString());
	}

}
