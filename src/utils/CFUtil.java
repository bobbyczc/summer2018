//package utils;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import model.News;
//import model.UserAction;
//
//public class CFUtil {
//	public static String basepath = "D:\\CodeProject\\eclipseProject\\NewsClassification\\sampledata\\";
//	/**
//	 * 整理所有新闻文档
//	 * @return
//	 */
//	public static List<News> getAllNews(){
//		List<News> paths = new ArrayList<>();
//		int index = 0;
//		//,"健康","教育","军事","科技","旅游","体育","文化"
//		String[] fileNames = {"财经"};
//		String path = null;
//		News news = null;
//		for(String s:fileNames) {
//			for(int i=10; i<=300;i++) {
//				path = basepath + s + "\\"+i+".txt";
//				index ++;
//				System.out.println(index+"   "+path);
//				news = new News(index,path);
//				paths.add(news);
//			}
//		}
//		return paths;
//	}
//	
//	/**
//	 * 计算用户对某个新闻的评分
//	 * 浏览1分，收藏3分，计算权重
//	 * @param UserAction
//	 * @param newsid
//	 * @return
//	 */
//	private static int getAttentionValue(UserAction UserAction,int newsid) {
//		int score = 0;
//		if(UserAction.getViewList().contains(newsid))
//			score += 1;
//		if(UserAction.getLoveList().contains(newsid))
//			score += 3; 
//		
//		
//		return score;
//	}
//	
//	/**
//	 * 计算用户与新闻联系的矩阵
//	 * @param UserActions
//	 * @param news
//	 * @return
//	 */
//	public static int[][] getUserActionAndNewsMatrix(List<UserAction> UserActions, List<News> news){
//		int row = UserActions.size()+1;
//		int colum = news.size()+1;
//		int[][] result = new int[row][colum];
//		for(int i = 0;i < row; i++) {
//			for(int j = 0; j < colum; j++) {
//				if(i==0) {
//					if(j==0) {
//						result[i][j] = 0;
//					}else {
//						result[i][j] = news.get(j-1).getId();
//					}			
//				}else if(j == 0) {
//					if(i==0) {
//						result[i][j] = 0;
//					}else {
//						result[i][j] = UserActions.get(i-1).getUserActionId();
//					}
//				}else {
//					result[i][j] = getAttentionValue(UserActions.get(i-1), news.get(j-1).getId());
//				}
//			}
//		}
//		return result;
//	}
//	
//	/**
//	 * 计算用户相似度    
//	 * 两个用户浏览的相同的网页数量/根号（两个用户浏览数量的乘积）
//	 * @param UserAction1
//	 * @param UserAction2
//	 * @return
//	 */
//	private static float calUserActionSimilarity(UserAction UserAction1, UserAction UserAction2) {
//		int count = 0;
//		for(int id1:UserAction1.getViewList()) {
//			for(int id2:UserAction2.getViewList()) {
//				if(id1==id2) {
//					count++;
//				}
//			}
//		}
//		float union = (float) Math.sqrt(UserAction1.getViewList().size()*UserAction2.getViewList().size());
//		return count/union;
//	}
//	
//	/**
//	 * 计算用户相似度矩阵
//	 * @param UserActions
//	 * @return
//	 */
//	public static float[][] getUserActionsMetrix(List<UserAction> UserActions) {
//		int size = UserActions.size();
//		float[][] results = new float[size+1][size+1];
//		results[0][0]=0;
//		for(int j = 1; j <= size; j++) {
//			results[0][j] = UserActions.get(j-1).getUserActionId();
//		}
//		for(int i = 1; i <= size; i++) {
//			results[i][0] = UserActions.get(i-1).getUserActionId();
//		}
//		for(int i = 1; i <= size; i++) {
//			for(int j = 1; j <= size; j++) {
//				if(i==j) {
//					results[i][j]=0;
//				}else {
//					results[i][j] = calUserActionSimilarity(UserActions.get(i-1), UserActions.get(j-1));
//				}	
//			}
//		}
//		return results;
//	}
//	/**
//	 * 获取目标用户最相似的N个用户的Id
//	 * @param UserAction 目标用户
//	 * @param matrix 用户相似度矩阵
//	 * @param N 相似用户数量
//	 * @return
//	 */
//	public static List<Integer> getKNNUserActionIds(UserAction UserAction, float[][] matrix, int N){
//		List<Integer> list = new ArrayList<>();
//		List<Integer> ids = new ArrayList<>();
//		int length = matrix[0].length;
//		for(int i = 1; i < length; i++) {
//			if(matrix[i][0]==UserAction.getUserActionId()) {
//				ids = getKNN(matrix[i], N);
//			}
//		}
//		for(int id:ids) {
//			list.add((int)matrix[0][id]);
//		}
//		return list;
//	}
//	
//	/**
//	 * 排序，获得最相似的几个用户的id行号
//	 * @param array
//	 * @param N
//	 * @return
//	 */
//	private static List<Integer> getKNN(float[] array, int N){
//		List<Integer> result = new ArrayList<>();
//		int length = array.length;
//		int[] index = new int[array.length-1];
//		float[] realdata = new float[array.length-1];
//		for(int i = 0;i< index.length;i++) {
//			index[i] = i+1;
//		}
//		for(int i = 1; i< array.length; i++) {
//			realdata[i-1] = array[i];
//		}
//		float temp = 0;
//		int pos = 0;
//		//排序
//		for(int i = 0; i<realdata.length-1; i++) {
//			for(int j=0;j<realdata.length-i-1;j++) {
//				if(realdata[j]<realdata[j+1]) {
//					temp = realdata[j];
//					realdata[j] = realdata[j+1];
//					realdata[j+1] = temp;
//					pos = index[j];
//					index[j] = index[j+1];
//					index[j+1] = pos;
//				}
//			}
//		}
//		
//		for(int i = 0; i<N;i++) {
//			result.add(index[i]);
//		}
//		return result;		
//	}
//	
//	/**
//	 * 通过id在用户列表中寻找用户
//	 * @param UserActions
//	 * @param ids
//	 * @return
//	 */
//	public static List<UserAction> findUserActionsbyid(List<UserAction> UserActions, List<Integer> ids){
//		List<UserAction> result = new ArrayList<>();
//		for(int id:ids) {
//			for(UserAction u:UserActions) {
//				if(id==u.getUserActionId()) {
//					result.add(u);
//				}
//			}
//		}
//		return result;
//	}
//	/**
//	 * 计算各种商品的推荐度
//	 * @param list N个相似用户列表
//	 * @param UserAction 目标用户
//	 * @param newsid 计算的新闻集合
//	 * @return
//	 */
//	public static HashMap<Integer, Float> getInterestDegree(List<UserAction> list,UserAction userAction) {
//		//几个用户新闻的并集
//		List<Integer> union = new ArrayList<>();
//		union.addAll(UserAction.getViewList());
//		//交集
////		List<Integer> con = new ArrayList<>();
////		con.addAll(UserAction.getViewList());
//		//差集
//		List<Integer> temp = null;
//		List<Integer> diff = new ArrayList<>();
//		for(UserAction u:list ) {
//			temp = new ArrayList<Integer>(u.getViewList());
////			con.retainAll(temp);
//			temp.removeAll(union);
//			union.addAll(temp);
//			
//		}
//		diff = new ArrayList<Integer>(union);
//		diff.removeAll(UserAction.getViewList());
//		HashMap<Integer, Float> result = new HashMap<>();
//		for(int i:diff) {
//			float interstDegree = 0;
//			for(UserAction u:list) {
//				interstDegree += calUserActionSimilarity(u, UserAction)*getAttentionValue(u, i);
//			}
//			result.put(i, interstDegree);
//		}
//		
//		return result;
//	}
//	
//	/**
//	 * 根据推荐度选出推荐度最大的几条新闻
//	 * @param fs
//	 * @param N
//	 * @return
//	 */
//	public static List<Map.Entry<Integer, Float>> getTopNNews(HashMap<Integer, Float> source,int N){
//		List<Map.Entry<Integer, Float>> list = new ArrayList<Map.Entry<Integer,Float>>(source.entrySet());
//		Collections.sort(list,new Comparator<Map.Entry<Integer, Float>>() {
//			public int compare(Entry<Integer, Float> o1,Entry<Integer, Float> o2) {
//				return o2.getValue().compareTo(o1.getValue());
//			}
//		});
//		int index = 0;
//		List<Map.Entry<Integer, Float>> result = new ArrayList<>();
//		for(Map.Entry<Integer, Float> item:list) {
//			index ++;
//			if(index>=N) {
//				break;
//			}
//			result.add(item);
//		}
//		return result;
//		
//	}
//}
