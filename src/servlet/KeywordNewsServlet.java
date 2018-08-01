package servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.mail.search.IntegerComparisonTerm;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.NewsDao;
import model.News;
import model.ResponseEntity;
import utils.SearchUtil;

/**
 * Servlet implementation class KeywordNewsServlet
 */
@WebServlet("/KeywordNewsServlet")
public class KeywordNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KeywordNewsServlet() {
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
		response.setHeader("content-type","text/html;charset=UTF-8");
		String keyword = request.getParameter("keyword");
		String pagestr = request.getParameter("page");
		int page = 1;
		if(pagestr!=null) {
			page = Integer.parseInt(pagestr);
		}
		System.out.println(keyword);
		System.out.println(page);
		ResponseEntity entity = new ResponseEntity();
		List<News> newss = dao.getNewsByKeyword(keyword,page);
		Gson gson = new Gson();
		if(!newss.isEmpty()) {
			builder.append("[");
			for(News n:newss){
				builder.append(gson.toJson(n));
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
