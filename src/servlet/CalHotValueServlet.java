package servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.NewsDao;
import model.ResponseEntity;

/**
 * Servlet implementation class CalHotValueServlet
 */
@WebServlet("/CalHotValueServlet")
public class CalHotValueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalHotValueServlet() {
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
		String keyword = request.getParameter("keyword");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		System.out.println(keyword+" "+startDate+" "+endDate);
		NewsDao dao = new NewsDao();
		HashMap<String, Float> info = dao.getKeywordHotList(keyword, startDate, endDate);
		Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();
		ResponseEntity entity = new ResponseEntity();
		if(info.size()>0) {
			entity.setStatus(1);
			entity.setMessage("获取成功");
			entity.setData(gson.toJson(info));
		}else {
			entity.setStatus(0);
			entity.setMessage("获取失败");
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
