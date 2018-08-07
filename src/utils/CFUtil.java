package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dao.UserActionDao;
import model.News;
import model.UserAction;

public class CFUtil {
	
	/**
	 * 计算用户对某个新闻的评分
	 * 浏览1分，收藏3分，计算权重
	 * @param UserAction
	 * @param newsid
	 * @return
	 */
	private static int getAttentionValue(UserAction UserAction,int newsid) {
		int score = 0;
		if(UserAction.getViewList().contains(String.valueOf(newsid)))
			score += 1;
		if(UserAction.getCollectionList().contains(String.valueOf(newsid)))
			score += 3; 
		
		
		return score;
	}
	
	/**
	 * 计算用户与新闻联系的矩阵
	 * @param UserActions
	 * @param news
	 * @return
	 */
	private static int[][] getUserActionAndNewsMatrix(List<UserAction> UserActions, List<News> news){
		int row = UserActions.size()+1;
		int colum = news.size()+1;
		int[][] result = new int[row][colum];
		for(int i = 0;i < row; i++) {
			for(int j = 0; j < colum; j++) {
				if(i==0) {
					if(j==0) {
						result[i][j] = 0;
					}else {
						result[i][j] = news.get(j-1).getId();
					}			
				}else if(j == 0) {
					if(i==0) {
						result[i][j] = 0;
					}else {
						result[i][j] = UserActions.get(i-1).getUserid();
					}
				}else {
					result[i][j] = getAttentionValue(UserActions.get(i-1), news.get(j-1).getId());
				}
			}
		}
		return result;
	}
	
	/**
	 * 计算用户相似度    
	 * 两个用户浏览的相同的网页数量/根号（两个用户浏览数量的乘积）
	 * @param UserAction1
	 * @param UserAction2
	 * @return
	 */
	private static float calUserActionSimilarity(UserAction userAction1, UserAction userAction2) {
		int count = 0;
		List<String> list1 = getUserNews(userAction1);
		List<String> list2 = getUserNews(userAction2);
		for(String id1:list1) {
			for(String id2:list2) {
				if(id1.equals(id2)) {
					count++;
				}
			}
		}
		float union = (float) Math.sqrt(list1.size()*list2.size());
		return count/union;
	}
	
	/**
	 * 计算用户相似度矩阵
	 * @param UserActions
	 * @return
	 */
	private static float[][] getUserActionsMetrix(List<UserAction> userActions) {
		int size = userActions.size();
		float[][] results = new float[size+1][size+1];
		results[0][0]=0;
		for(int j = 1; j <= size; j++) {
			results[0][j] = userActions.get(j-1).getUserid();
		}
		for(int i = 1; i <= size; i++) {
			results[i][0] = userActions.get(i-1).getUserid();
		}
		for(int i = 1; i <= size; i++) {
			for(int j = 1; j <= size; j++) {
				if(i==j) {
					results[i][j]=0;
				}else {
					results[i][j] = calUserActionSimilarity(userActions.get(i-1), userActions.get(j-1));
				}	
			}
		}
		return results;
	}
	/**
	 * 获取目标用户最相似的N个用户的Id
	 * @param UserAction 目标用户
	 * @param matrix 用户相似度矩阵
	 * @param N 相似用户数量
	 * @return
	 */
	private static List<Integer> getKNNUserActionIds(UserAction UserAction, List<UserAction> actions){
		float[][] matrix = getUserActionsMetrix(actions);
		List<Integer> list = new ArrayList<>();
		List<Integer> ids = new ArrayList<>();
		int length = matrix[0].length;
		for(int i = 1; i < length; i++) {
			if(matrix[i][0]==UserAction.getUserid()) {
				ids = getKNN(matrix[i]);
			}
		}
		for(int id:ids) {
			list.add((int)matrix[0][id]);
		}
		return list;
	}
	
	/**
	 * 排序，获得最相似的几个用户的id行号
	 * @param array
	 * @param N
	 * @return
	 */
	private static List<Integer> getKNN(float[] array){
		List<Integer> result = new ArrayList<>();
		int[] index = new int[array.length-1];
		float[] realdata = new float[array.length-1];
		for(int i = 0;i< index.length;i++) {
			index[i] = i+1;
		}
		for(int i = 1; i< array.length; i++) {
			realdata[i-1] = array[i];
		}
		float temp = 0;
		int pos = 0;
		//排序
		for(int i = 0; i<realdata.length-1; i++) {
			for(int j=0;j<realdata.length-i-1;j++) {
				if(realdata[j]<realdata[j+1]) {
					temp = realdata[j];
					realdata[j] = realdata[j+1];
					realdata[j+1] = temp;
					pos = index[j];
					index[j] = index[j+1];
					index[j+1] = pos;
				}
			}
		}
		
		for(int i = 0; i<5;i++) {
			result.add(index[i]);
		}
		return result;		
	}
	
	/**
	 * 通过id在用户列表中寻找用户
	 * @param UserActions
	 * @param ids
	 * @return
	 */
	private static List<UserAction> findUserActionsbyid(List<UserAction> UserActions, List<Integer> ids){
		List<UserAction> result = new ArrayList<>();
		for(int id:ids) {
			for(UserAction u:UserActions) {
				if(id==u.getUserid()) {
					result.add(u);
				}
			}
		}
		return result;
	}
	
	private static List<String> getUserNews(UserAction action){
		List<String> result = new ArrayList<String>(action.getViewList());
		result.addAll(action.getCollectionList());
		return result;
	}
	/**
	 * 计算各种商品的推荐度
	 * @param list N个相似用户列表
	 * @param UserAction 目标用户
	 * @param newsid 计算的新闻集合
	 * @return
	 */
	private static HashMap<Integer, Float> getInterestDegree(List<UserAction> list,UserAction userAction) {
		//几个用户新闻的并集
		List<String> union = getUserNews(userAction);
		//交集
//		List<Integer> con = new ArrayList<>();
//		con.addAll(UserAction.getViewList());
		//差集
		List<String> temp = null;
		List<String> diff = new ArrayList<>();
		for(UserAction u:list ) {
			temp = new ArrayList<String>(getUserNews(u));
//			con.retainAll(temp);
			temp.removeAll(union);
			union.addAll(temp);
			
		}
		diff = new ArrayList<String>(union);
		diff.removeAll(getUserNews(userAction));
		HashMap<Integer, Float> result = new HashMap<>();
		for(String i:diff) {
			float interstDegree = 0;
			for(UserAction u:list) {
				interstDegree += calUserActionSimilarity(u, userAction)*getAttentionValue(u, Integer.parseInt(i));
			}
			result.put(Integer.parseInt(i), interstDegree);
		}
		
		return result;
	}
	
	/**
	 * 根据推荐度选出推荐度最大的几条新闻
	 * @param fs
	 * @param N
	 * @return
	 */
	private static List<Map.Entry<Integer, Float>> getTopNNews(HashMap<Integer, Float> source){
		List<Map.Entry<Integer, Float>> list = new ArrayList<Map.Entry<Integer,Float>>(source.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<Integer, Float>>() {
			public int compare(Entry<Integer, Float> o1,Entry<Integer, Float> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		
		return list;
		
	}
	
	/**
	 * 接口函数  获取用户推荐的新闻ID列表
	 * @param userid
	 * @return
	 */
	public static List<Integer> recommend(int userid){
		List<Integer> result = new ArrayList<>();
		UserActionDao actionDao = new UserActionDao();
		UserAction action = actionDao.getUserActionByUid(userid);
		List<UserAction> actions = actionDao.getAllUserAction();
		List<Integer> userids = CFUtil.getKNNUserActionIds(action, actions);
		List<UserAction> similarUsers = CFUtil.findUserActionsbyid(actions, userids);
		HashMap<Integer, Float> hashMap = CFUtil.getInterestDegree(similarUsers, action);
		for(Map.Entry<Integer, Float> entry:hashMap.entrySet()) {
			System.out.println(entry.getKey()+"   "+entry.getValue());
		}
		List<Map.Entry<Integer, Float>> recommandation = CFUtil.getTopNNews(hashMap);
		for(Map.Entry<Integer, Float> entry:recommandation) {
			result.add(entry.getKey());
		}
		return result;
		
	}
}
