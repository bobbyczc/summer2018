package utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

import model.News;

public class WordUtil {
	
	private List<String> stopWords;
	public WordUtil(String path) {
		this.stopWords = new ArrayList<>();
		File file = new File(path);
		String line;
		try {
			BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			while((line=bReader.readLine())!=null) {
				this.stopWords.add(line.trim());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public List<String> getStopWords() {
		return stopWords;
	}
	public void setStopWords(List<String> stopWords) {
		this.stopWords = stopWords;
	}
	
	public boolean isWhiteSpace(String s) {
		if(s.equals("\n")||s.equals("\t")||s.equals("\r")) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 去除停用词
	 * @param list
	 * @return
	 */
	public List<String> filter(List<SegToken> list){
		List<String> resultList = new ArrayList<>();
		for(SegToken s:list) {
			if(!this.stopWords.contains(s.word)) {
				resultList.add(s.word);
			}
		}
		return resultList;
	}
	
//	/**
//	 * 除去一个单词列表中的重复项，返回一个元素不重复的列表
//	 * @param wordList
//	 * @return
//	 */
//	public List<String> distinct(List<String> wordList){
//		List<String> list= new ArrayList<>();
//		for(String word:wordList) {
//			if(!list.contains(word)) {
//				list.add(word);
//			}
//		}
//		return list;
//	}
	/**
	 * 统计一个单词列表中出现的词的频率
	 * @param wordList
	 * @return
	 */
	public HashMap<String, Integer> countWordFry(List<News> list){
		List<String> wordList = getWordByNews(list);
		HashMap<String, Integer> map = new HashMap<>();
		for(String s:wordList) {
			if(!map.containsKey(s))
				map.put(s, Collections.frequency(wordList, s));
		}
		List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> entry1,Entry<String, Integer> entry2) {
				return entry2.getValue().compareTo(entry1.getValue());
			}
			
		});
		HashMap<String, Integer> result = new HashMap<>();
		int index = 0;
		for(Map.Entry<String, Integer> mapping:entries) {
			index++;
			result.put(mapping.getKey(), mapping.getValue());
			if(index==50) {
				break;
			}
		}
		return result;
	}
	
	private List<String> getWordByNews(List<News> list){
		JiebaSegmenter segmenter = new JiebaSegmenter();
		List<String> result = new ArrayList<>();
		for(News n:list) {
			List<String> newsWord = filter(segmenter.process(n.getContent().replaceAll("\\s*", ""), SegMode.INDEX));
			for(String s:newsWord) {
				result.add(s);
			}
		}
		return result;
	}
}
