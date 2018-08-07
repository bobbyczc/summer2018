package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TransUtil {
	
	public  static enum UpdateMode{
		DELETE,ADD
	}
	public static List<String> strToList(String s){
		List<String> list = new ArrayList<>();
		if(s!=null) {
			if(s.contains(",")) {
				StringTokenizer tokenizer = new StringTokenizer(s, ",");
				while(tokenizer.hasMoreTokens()) {
					list.add(tokenizer.nextToken());
				}
			}else {
				
				list.add(s);
			}
		}
		return list;
	}
	
	public static String listToStr(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for(String s: list) {
			sb.append(s);
			sb.append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		return sb.toString();
	}
}
