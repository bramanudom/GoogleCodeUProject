package com.flatironschool.javacs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
		String [] results = new String[5];
		ArrayList<Entry> entries = getEntries(term);
		int i = 0;
		while(i<5 && i<entries.size()) {
			results[i] = entries.get(i).getUrl();
			i++;
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
//			System.out.println(url);
			Elements paragraphs = wf.fetchWikipedia(url);
			indexPage(url, paragraphs);
			//scanny.nextLine();
		}
	}

	/* returns the array of entry objects given a term */

	public ArrayList<Entry> getEntries(String term){
		this.term = term;
		return index.get(term);
	}

	public List<Entry> sort() {

		// get all entries from the search map and populate a LinkedList first
		// write a comparator that compares entries (<K,V>)
		// call sort on the list of entries, return the list 

		List<Entry> entryList = new LinkedList<Entry>();
		

		for (Entry entry: getEntries(term)){
			entryList.add(entry);
		}


		Comparator<Entry> comparator = new Comparator<Entry>(){
			@Override
			public int compare(Entry thisEntry, Entry thatEntry) {
				Integer thisV = thisEntry.getCount();
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


	public ArrayList<Entry> getURLs(String term) {
		// TODO Auto-generated method stub
		return null;
	}


	public ArrayList<Entry> getCounts(String term) {
		// TODO Auto-generated method stub
		return null;
	}


	public Map<String, Integer> getCountsFaster(String term) {
		// TODO Auto-generated method stub
		return null;
	}


	public Integer getCount(String url, String term) {
		// TODO Auto-generated method stub
		return null;
	}




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
//			if (term.equals("the")) {
//				System.out.println(url);
//			}
			ArrayList<Entry> entryList = index.get(term);
//			if (term.equals("the")) {
//				System.out.println(entryList.size());
//				System.out.println(url);
//			}
			for (Entry entry : entryList) {
				if (entry.getUrl().equals(url)) {
					entry.addOne();
					return true;
				}
			}
			
//			System.out.println(index.size());
			entryList.add(new Entry(url, 1));
//			System.out.println(index.get(term).size());
            return true;
        } else {
        	ArrayList<Entry> newEntryList = new ArrayList<Entry>();
        	newEntryList.add(new Entry(url, 1));
        	index.put(term, newEntryList);
        	return true;
        }
	}


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
		try{
			listIndex.add();
		}
		catch(IOException e){
			System.out.print(e);
		}

		
		System.out.println("There are " + listIndex.index.size() + " words");		
		ArrayList<Entry> list = listIndex.index.get("the");
		System.out.println("There are " + list.size() +" urls that have the word the in them");
		System.out.println("count: " +  list.get(0).count);
		System.out.println(list);
		System.out.println(Arrays.toString(listIndex.topFive("the")));
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

	public int getCount(){
		return this.count;
	}

	public String getUrl(){
		return this.url;
	}

	public String toString() {
		return this.url;
	}

}