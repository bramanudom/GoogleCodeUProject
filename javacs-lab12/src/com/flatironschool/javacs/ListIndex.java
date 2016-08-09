package com.flatironschool.javacs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;
import java.io.File;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
public class ListIndex implements Index{
	
	String fileName;
	HashMap<String, ArrayList<Entry>> index = new HashMap<String, ArrayList<Entry>>();
	Scanner scanny;
	
	public ListIndex() {
		
	}
	
	public ListIndex(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public boolean isIndexed(String url) {
		// TODO Auto-generated method stub
		return false;
	}

	public void add() throws IOException{ //CHANGE METHOD SIGNCATURE INTERFACE	
		// TODO Auto-generated method stub
		WikiFetcher wf = new WikiFetcher();
		URL filePath = getClass().getResource(fileName);
		scanny = new Scanner(new File(filePath.getPath()));
		while(scanny.hasNext()){
			String url = scanny.nextLine();
			Elements paragraphs = wf.fetchWikipedia(url);
			indexPage(url, paragraphs);
		}
	}

	@Override
	public Set<String> getURLs(String term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getCounts(String term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getCountsFaster(String term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCount(String url, String term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void indexPage(String url, Elements paragraphs) {
		// TODO Auto-generated method stub
		for (Node node: paragraphs) {
			processTree(node, url);
		}
	}
	
	public void processTree(Node root, String url) {
		for (Node node: new WikiNodeIterable(root)) {
			if (node instanceof TextNode) {
				processText(((TextNode) node).text(), url);
			}
		}
	}
	
	public void processText(String text, String url) {
		// replace punctuation with spaces, convert to lower case, and split on whitespace
		String[] array = text.replaceAll("\\pP", " ").toLowerCase().split("\\s+");
		
		for (int i=0; i<array.length; i++) {
			String term = array[i];
			incrementTermCount(term, url);
		}
	}
	
	public void incrementTermCount(String term, String url) {
		if (index.containsKey(term)) {
			ArrayList<Entry> entryList = index.get(term);
			for (Entry entry : entryList) {
				if (entry.url.equals(url)) {
					entry.addOne();
				}
			}
		}
	}

	@Override
	public void printIndex() {
		for (String term: index.keySet()){
			System.out.println("Term: " + term);
			ArrayList<Entry> list = index.get(term);
			for(Entry entry: list){
				System.out.println("url: " + entry.url + "count: " + entry.count);
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException{		
		ListIndex listIndex = new ListIndex("urls.txt");
		listIndex.add();
		// listIndex.index = new HashMap<String, ArrayList<Entry>>();
		// ArrayList<Entry> entries = new ArrayList<Entry>();
		// entries.add(new Entry("abc", 1));
		// listIndex.index.put("a", entries);
		//listIndex.incrementTermCount("a", "abc");
		
		ArrayList<Entry> list = listIndex.index.get("the");
//		System.out.println("count: " +  list.get(0).count);
	}

}

class Entry {
	String url;
	int count;
	
	public Entry(String url, int count) {
		this.url = url;
		this.count = count;
	}
	
	public void addOne() {
		count += 1;
	}
}