package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserActionDao;
import model.ResponseEntity;
import model.User;
import utils.TransUtil;

/**
 * Servlet implementation class ViewKeywordServlet
 */
@WebServlet("/ViewKeywordServlet")
public class ViewKeywordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewKeywordServlet() {
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
		 response.setHeader("content-type", "text/html;charset=UTF-8");
		 UserActionDao actionDao = new UserActionDao();
		 String userid = request.getParameter("userid");
		 List<String> keList = TransUtil.strToList(actionDao.getKeyWordsStr(Integer.parseInt(userid)));
		 ResponseEntity entity = new ResponseEntity();
		 StringBuilder builder  = new StringBuilder();
		 builder.append("[");
		 for(String keyword:keList) {
			 builder.append(keyword);
			 builder.append(",");
		 }
		 builder.deleteCharAt(builder.lastIndexOf(","));
		 builder.append("]");
		 entity.setStatus(1);
		 entity.setMessage("获取数据");
		 entity.setData(builder.toString());
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
