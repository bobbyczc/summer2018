package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.NewsDao;
import model.News;
import model.ResponseEntity;
import utils.CFUtil;

/**
 * Servlet implementation class RecommendServlet
 */
@WebServlet("/RecommendServlet")
public class RecommendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		NewsDao newsDao = new NewsDao();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		String userid = request.getParameter("userid");
		System.out.println(userid);
		ResponseEntity entity = new ResponseEntity();
		Gson gson = new Gson();
		List<Integer> list = CFUtil.recommend(Integer.parseInt(userid));
		if(!list.isEmpty()) {
			builder.append("[");
			for(int i:list){
				builder.append(gson.toJson(newsDao.getNewsById(i)));
				builder.append(",");
			}
			builder.deleteCharAt(builder.lastIndexOf(","));
			builder.append("]");
			entity.setStatus(1);
			entity.setMessage("获取成功");
			entity.setCount(list.size());
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
