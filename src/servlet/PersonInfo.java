package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.ResponseEntity;
import model.User;
import service.UserService;

/**
 * Servlet implementation class PersonInfo
 */
@WebServlet("/PersonInfo")
public class PersonInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		UserService service = new UserService();
		int userid = Integer.parseInt(request.getParameter("userid"));
		User user = service.getUserById(userid);
		ResponseEntity entity = new ResponseEntity();
		if(user!=null) {
			entity.setData(user.toString());
			entity.setStatus(1);
			entity.setMessage("success");
		}else {
			entity.setStatus(0);
			entity.setMessage("error");
			entity.setData(ResponseEntity.failMsg);
		}
		response.getWriter().write(entity.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
