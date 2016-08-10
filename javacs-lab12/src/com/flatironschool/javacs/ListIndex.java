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
	String term;
	
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

	public String[] topFive (String term){
		String [] results = new String[5]
		ArrayList<Entry> entries = getEntries(term))
		for(int i = 0; i < 5; i++){
			results[i] = entries.get(i);
		}

		return results;
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

	/* returns the array of entry objects given a term */

	public ArrayList<Entry> getEntries(String term){
		this.term = term;
		index.get(term);
	}

	public List<Entry> sort() {

		// get all entries from the search map and populate a LinkedList first
		// write a comparator that compares entries (<K,V>)
		// call sort on the list of entries, return the list 

		List<Entry> entryList = new LinkedList<Entry>();
		

		for (Entry entry: this.getEntries(term)){
			entryList.add(entry);
		}


		Comparator<Entry> comparator = new Comparator<Entry>(){
			@Override
			public int compare(Entry thisEntry, Entry thatEntry) {
				Integer thisV = thisEntry.getCount);
				Integer thatV = thatEntry.getCount();
				if (thisV > thatV ){
					return 1;
				} 
				else if (thisV < thatV){
					return -1;
			
				} else{
					return 0;
				}
			}
		};

		Collections.sort(entryList, comparator);
		return entryList;
	}

	@Override
	public ArrayList<Entry> getURLs(String term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Entry> getCounts(String term) {
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


	public

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
	
	public boolean incrementTermCount(String term, String url) {
		if (index.containsKey(term)) {
			ArrayList<Entry> entryList = index.get(term);
			for (Entry entry : entryList) {
				if (entry.getUrl().equals(url)) {
					entry.addOne();
					return true;
				}
			}
			entryList.add(new Entry(url, 1));
            return true;
        } else {
        	ArrayList<Entry> newEntryList = new ArrayList<Entry>();
        	newEntryList.add(new Entry(url, 1));
        	index.put(term, newEntryList);
        	return true;
        }
	}

	@Override
	public void printIndex() {
		for (String term: index.keySet()){
			System.out.println("Term: " + term);
			ArrayList<Entry> list = index.get(term);
			for(Entry entry: list){
				System.out.println("url: " + entry.getUrl() + "count: " + entry.getCount());
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException{		
		ListIndex listIndex = new ListIndex("urls.txt");
		listIndex.add();

		
		ArrayList<Entry> list = listIndex.index.get("the");
//		System.out.println("count: " +  list.get(0).count);
	}

}

class Entry {
	static String url;
	static int count;
	
	public Entry(String url, int count) {
		this.url = url;
		this.count = count;
	}
	
	public void addOne() {
		count += 1;
	}

	public int getCount(){
		return this.count;
	}

	public String getUrl(){
		return this.url;
	}



}