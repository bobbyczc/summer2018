package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

public class KeyUtil {
	static String wordBase = "C:\\Users\\superman\\Desktop\\result\\wordBase.json";
	static String stopWords = "C:\\Users\\superman\\Desktop\\stopWords.txt";
	public static String getTop3KeyWrods(String content){
		HashMap<String, Double> map = calWordInfo(content);
		List<Map.Entry<String, Double>> entries = new ArrayList<>(map.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Entry<String, Double> entry1,Entry<String, Double> entry2) {
				return entry2.getValue().compareTo(entry1.getValue());
			}
		});
		int index = 0;
		String result = "";
		for(Map.Entry<String, Double> entry:entries) {
			System.out.println(entry.getKey()+" "+entry.getValue());
			index ++;
			if(index == 3) {
				result = result + entry.getKey();
				break;
			}
			result = result + entry.getKey() + ",";
		}
		return result;
	}
	
	private static HashMap<String, Double> calWordInfo(String content){
		HashMap<String, Double> result = new HashMap<>();
		WordUtil util = new WordUtil(stopWords);
		JiebaSegmenter segmenter = new JiebaSegmenter();
		List<String> list = util.filter(segmenter.process(content, SegMode.INDEX));
		JSONTokener jsonTokener;
		try {
			jsonTokener = new JSONTokener(new FileReader(wordBase));
			JSONObject object = new JSONObject(jsonTokener);
			for(String s:list) {
				if(!result.containsKey(s)) {
					int count = Collections.frequency(list, s);
					double fre = (double) count/list.size();
					try {
						JSONObject sOb = object.getJSONObject(s);
						double idf = sOb.getDouble("idfvalue");
						double tfidf = fre*idf;
						result.put(s, tfidf);
					}catch (JSONException e) {
						System.out.println("JSONObject[\""+s+"\"] not found.");
						continue;
					}
					
					
				}	
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	public static void main(String[] args) {
		String content ="　　中新网杭州4月18日电(记者 刘文彬)18日，第71届戛纳电影节宣布张震将出任金棕榈主竞赛单元评审，这也是本届评审团中唯一一位华人评审。　　继姜文之后，张震成为第二位出现在主竞赛单元评审名单中的华语男演员。记者了解到，作为戛纳的常客，张震曾先后有9部影片入围，其中《最好的时光》、《呼吸》等5部入围主竞赛单元。　　从带着作品参赛的演员，到主竞赛单元的评审，时隔三年，张震再次来到戛纳。面对记者采访，他坦言：“心情很不一样，可以看到更多来自世界不同地方的好电影，很期待这一次的戛纳之旅。”　　张震此次出任戛纳电影节主竞赛单元的评审，是他第二次在世界五大电影节上担任评委一职。而对于好电影的标准，张震直言好的电影是需要温度的，“这个温度是可以延续并且存在心底很久的，是好电影的必备条件。”至于偏爱的影片类型，张震透露，不同的年龄段，他喜欢的电影类型会不一样，“随着人生阅历的增加，看到电影的深度也会不同。”　　张震表示，此前来到戛纳，更多的任务是来宣传优秀的华语电影，“这一次‘使命’发生了变化，心态也会不一样，是一次特别的体验。”对于心态上的变化，张震补充道：“这一次更加享受电影节的氛围，可以看到更多来自世界不同地方的好作品，期待这一次的戛纳之旅。”　　虽然张震是首次作为戛纳电影节的评审，但二十余年的演员经历让他对表演有着自己的理解与认知，“我是演员出身，所以表演的地方，我会看得比较仔细，会有比较多的想法和意见。”另一方面，张震认为从事评审工作是一个学习的过程，“电影不只有演员，它是由很多不同专业的人员配合而成的，做评审可以学到很多不同专业上的知识。”　　作为华语电影的中流砥柱，张震被影迷赞誉为“神坛上的男演员”。对于能够获得观众和众多专业电影奖项的认可，张震显得十分谦虚，他表示：“奖项对于演员来说是很大的肯定，不仅来自观众，还来自于业内同行的认可。但更多的其实是鞭策，无论是电影、电视剧还是舞台剧，表演是永无止境的。”";
		System.out.println(getTop3KeyWrods(content));
	}
}
