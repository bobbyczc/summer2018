package utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


public class SearchUtil {
	
	static String basepath = "C:\\Users\\superman\\Desktop\\result\\";
	
	public static List<Integer> getNewsIdByKeyword(String keyword){
		JSONTokener jsonTokener = null;
		List<Integer> list = new ArrayList<>();
		String[] types = {"国内","国际","社会","军事","港澳","台湾","华人","财经","金融","房产","汽车","能源","IT","文化","娱乐","体育","健康"};
		for(String type:types) {
			String path = basepath + type + ".json";
			System.out.println("读取"+type);
			try {
				jsonTokener = new JSONTokener(new FileReader(new File(path)));
				JSONObject object = new JSONObject(jsonTokener);
				JSONArray docArray = object.getJSONArray(type);
				for(int i = 0; i <docArray.length();i++) {
					JSONObject doc = docArray.getJSONObject(i);
					JSONArray terms = doc.getJSONArray("terms");
					for(int j = 0; j < terms.length();j++) {
						if(terms.getJSONObject(j).getString("term").equals(keyword)) {
							list.add(doc.getInt("id"));
							break;
						}
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	public static void main(String args[]) {
		List<Integer> list = getNewsIdByKeyword("高考");
		for(int i:list) {
			System.out.println(i);
		}
		System.out.println(list.size());
	}
}
