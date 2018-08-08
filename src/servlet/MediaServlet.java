package servlet;

import java.io.IOException;
import java.util.HashMap;
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

/**
 * Servlet implementation class MediaServlet
 */
@WebServlet("/MediaServlet")
public class MediaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MediaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String keyword = request.getParameter("keyword");
		NewsDao dao = new NewsDao();
		List<News> list = dao.getAllNewsByKeyword(keyword);
		int total = list.size();
		HashMap<String, Integer> newscount = new HashMap<>();
		for(News n:list) {
			String source = n.getSource();
			if(newscount.containsKey(source)) {
				newscount.put(source,newscount.get(source)+1);
			}else {
				newscount.put(source, 1);
			}
		}
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for(String key:newscount.keySet()) {
			builder.append("{");
			builder.append("\"source\":\""+key+"\",");
			int count = newscount.get(key);
			builder.append("\"count\":"+count+",");
			float value = Float.parseFloat(String.format("%.4f",(float)count/total ));
			builder.append("\"ratio\":"+value);
			builder.append("},");
		}
		builder.deleteCharAt(builder.lastIndexOf(","));
		builder.append("]");
		ResponseEntity entity = new ResponseEntity();
		entity.setStatus(1);
		entity.setMessage("获取成功");
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
