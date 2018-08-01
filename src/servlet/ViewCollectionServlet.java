package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.NewsDao;
import dao.UserActionDao;
import model.News;
import model.ResponseEntity;

/**
 * Servlet implementation class ViewCollectionServlet
 */
@WebServlet("/ViewCollectionServlet")
public class ViewCollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewCollectionServlet() {
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
		String userid = request.getParameter("userid");
		UserActionDao actionDao = new UserActionDao();
		NewsDao dao = new NewsDao();
		String collectionStr = actionDao.getUserCollectionsStr(Integer.parseInt(userid));
		String[] nids = collectionStr.split(",");
		StringBuilder builder = new StringBuilder();
		ResponseEntity entity = new ResponseEntity();
		Gson gson = new Gson();
		builder.append("[");
		for(String id:nids) {
			News news = dao.getNewsById(Integer.parseInt(id));
			builder.append(gson.toJson(news));
			builder.append(",");
		}
		builder.deleteCharAt(builder.lastIndexOf(","));
		builder.append("]");
		entity.setStatus(1);
		entity.setMessage("success");
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
