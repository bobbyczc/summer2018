package utils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.NewsDao;
import dao.UserActionDao;
import model.News;
import model.UserAction;

public class CFTest {
	public static void main(String args[]) {
		NewsDao newsDao = new NewsDao();
		UserActionDao userActionDao = new UserActionDao();
		List<UserAction> actions = userActionDao.getAllUserAction();
		List<News> news = newsDao.getAllNews();
		UserAction user4 = userActionDao.getUserActionByUid(5);
//		for(int[] s:CFUtil.getUserActionAndNewsMatrix(actions, news)){
//			for(int i:s) {
//				System.out.print(i+"\t");
//			}
//			System.out.print("\n");
//		}
//		
//		for(float[] s:CFUtil.getUserActionsMetrix(actions)) {
//			for(float i:s) {
//				System.out.print(i+"\t");
//			}
//			System.out.print("\n");
//		}
		
//		List<Integer> userids = CFUtil.getKNNUserActionIds(user4, actions);
//		List<UserAction> similarUsers = CFUtil.findUserActionsbyid(actions, userids);
//		HashMap<Integer, Float> hashMap = CFUtil.getInterestDegree(similarUsers, user4);
//		for(Map.Entry<Integer, Float> entry:hashMap.entrySet()) {
//			System.out.println(entry.getKey()+"   "+entry.getValue());
//		}
//
//		List<Map.Entry<Integer, Float>> recommandation = CFUtil.getTopNNews(hashMap);
//		System.out.println(user4.getUserid()+"用户的个性推荐：");
//		for(Map.Entry<Integer, Float> entry:recommandation) {
//			System.out.println("推荐新闻id:"+entry.getKey()+",  推荐指数："+entry.getValue());
//		}
//		
		
	}
}
