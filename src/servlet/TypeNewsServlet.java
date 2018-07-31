package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NewsDao;
import model.News;
import model.ResponseEntity;

/**
 * Servlet implementation class TypeNewsServlet
 */
@WebServlet("/TypeNewsServlet")
public class TypeNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TypeNewsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		NewsDao dao = new NewsDao();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		System.out.println(type);
		ResponseEntity entity = new ResponseEntity();
		List<News> newss = dao.getNewsByType(type);
		if(!newss.isEmpty()) {
			builder.append("[");
			for(News n:newss){
				builder.append(n.toString());
				builder.append(",");
			}
			builder.deleteCharAt(builder.lastIndexOf(","));
			builder.append("]");
			entity.setStatus(1);
			entity.setMessage("获取成功");
			entity.setData(builder.toString());
		}else {
			entity.setStatus(0);
			entity.setMessage("没有数据");
			entity.setData("无数据");
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
