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
import utils.WordUtil;

/**
 * Servlet implementation class WordFrenquencyServlet
 */
@WebServlet("/WordFrenquencyServlet")
public class WordFrenquencyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WordFrenquencyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Linux下路径
		String path=request.getSession().getServletContext().getRealPath("")+"WEB-INF/classes/data/stopWords.txt";
		
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String keyword = request.getParameter("keyword");
		NewsDao dao = new NewsDao();
		List<News> list = dao.getTopNewsByKeyword(keyword, 100);
		ResponseEntity entity = new ResponseEntity();
		if(!list.isEmpty()) {
			Gson gson = new Gson();
			WordUtil util = new WordUtil(path);
			HashMap<String, Integer> wordFre = util.countWordFry(list);
			entity.setStatus(1);
			entity.setMessage("success");
			entity.setData(gson.toJson(wordFre));
		}else {
			entity.setStatus(0);
			entity.setMessage("fail");
			entity.setData("no data");
		}
		response.getWriter().write(entity.toString());;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
