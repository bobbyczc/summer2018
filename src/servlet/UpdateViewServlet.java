package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserActionDao;
import model.ResponseEntity;
import utils.TransUtil.UpdateMode;

/**
 * Servlet implementation class UpdateViewServlet
 */
@WebServlet("/UpdateViewServlet")
public class UpdateViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int userid = Integer.parseInt(request.getParameter("userid"));
		int newsid = Integer.parseInt(request.getParameter("newsid"));
		String mode = request.getParameter("mode");
		UserActionDao actionDao = new UserActionDao();
		ResponseEntity entity = new ResponseEntity();
		if(mode.equals("add")) {
			actionDao.updateViewList(userid, newsid, UpdateMode.ADD);
		}else if(mode.equals("delete")) {
			actionDao.updateViewList(userid, newsid, UpdateMode.DELETE);
		}
		entity.setStatus(1);
		entity.setMessage("success");
		entity.setData("\""+newsid+"\"");
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
