package com.flatironschool.javacs;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import redis.clients.jedis.Jedis;


/**
 * Represents the results of a search query.
 *
 */
public class WikiSearch{
	
	// map from URLs that contain the term(s) to relevance score
	private Map<String, Integer> map;

	/**
	 * Constructor.
	 * 
	 * @param map
	 */
	public WikiSearch(Map<String, Integer> map) {
		this.map = map;
	}
	
	/** 
	 * NEW METHOD: Looks up the Inverse Document Frequency (IDF) aka 
	 * how important a term is
	 *
	 * @param url
	 * @return the IDF score of the current search
	 **/

	public Integer getIDF(String url){
		//TODO: fill this out by first figuring out the skeleton of our search engine

		/* we know that the IDF is calculated according to tfidf.com as
		 * log_e(Total # of documents/ number of documents with the term in it)
		 * given a search, we can find the total number of documents with the term 
		 * in it by simply figuring out the number of keys/ <K,V> sets as each K 
		 * represents a url/webpage/document that contains the search term 

		 * we now have to figure out how to compute the number of pages we have 
		 * in total -- we'll have to look at our crawler to see how we'd like to
		 * combine the two

		 */


		return 0;

	}


	/**
	 * Looks up the relevance of a given URL.
	 * 
	 * @param url
	 * @return
	 */
	public Integer getRelevance(String url) {
		Integer relevance = map.get(url);
		return relevance==null ? 0: relevance;
	}
	
	/**
	 * Prints the contents in order of term frequency.
	 * 
	 * @param map
	 */
	private  void print() {
		List<Entry<String, Integer>> entries = sort();
		for (Entry<String, Integer> entry: entries) {
			System.out.println(entry);
		}
	}
	
	/**
	 * Computes the union of two search results.
	 * 
	 * @param that
	 * @return New WikiSearch object.
	 */
	public WikiSearch or(WikiSearch that) {
		// instantiated a newMap that will have the combined (or) search results
       Map<String, Integer> returnMap = new HashMap<String, Integer> ();
        
        /*  iterate through one of the maps in the WikiSearch, checks for keys that are present 
			in both WikiSearches and combines their values; adds new values and keys to returnMap
			*/
        for (String key: this.map.keySet()){
        	if (that.map.containsKey(key)){
        		Integer newValue = this.map.get(key) + that.getRelevance(key);
        		//System.out.println("new combined value = " + newValue);
        		returnMap.put(key, newValue);

        	}

        /* if the key is specific to one of the maps, add the entire entry to the returnMap
        */

        	else {
        		returnMap.put(key,this.getRelevance(key));
        	}
        }

        /* do the same for the other map */

        for (String key: that.map.keySet()){
        	if (!returnMap.containsKey(key)){
        		returnMap.put(key, that.getRelevance(key));
        	}
        }

   		//System.out.println(returnMap.entrySet());

        return (new WikiSearch(returnMap));


	}
	
	/**
	 * Computes the intersection of two search results.
	 * 
	 * @param that
	 * @return New WikiSearch object.
	 */
	public WikiSearch and(WikiSearch that) {
        Map<String, Integer> returnMap = new HashMap<String, Integer> ();
        
        for (String key: this.map.keySet()){
        	if (that.map.containsKey(key)){
        		Integer newValue = this.getRelevance(key) + that.getRelevance(key);
        		returnMap.put(key, newValue);

        	}
		}

		return (new WikiSearch(returnMap));
	}
	
	/**
	 * Computes the intersection of two search results.
	 * 
	 * @param that
	 * @return New WikiSearch object.
	 */
	public WikiSearch minus(WikiSearch that) {
       Map<String, Integer> returnMap = new HashMap<String, Integer> ();
        
        for (String key: this.map.keySet()){
        	if (!that.map.containsKey(key)){
        		returnMap.put(key, this.getRelevance(key));
        	}
		}

		return (new WikiSearch(returnMap));
	}
	
	/**
	 * Computes the relevance of a search with multiple terms.
	 * 
	 * @param rel1: relevance score for the first search
	 * @param rel2: relevance score for the second search
	 * @return
	 */
	protected int totalRelevance(Integer rel1, Integer rel2) {
		// simple starting place: relevance is the sum of the term frequencies.
		return rel1 + rel2;
	}

	/**
	 * Sort the results by relevance.
	 * 
	 * @return List of entries with URL and relevance.
	 */
	public List<Entry<String, Integer>> sort() {

		// get all entries from the search map and populate a LinkedList first
		// write a comparator that compares entries (<K,V>)
		// call sort on the list of entries, return the list 

		List<Entry<String, Integer>> entryList = new LinkedList<Entry<String, Integer>>();
		

		for (Entry<String, Integer> entry: this.map.entrySet()){
			entryList.add(entry);
		}


		Comparator<Entry<String,Integer>> comparator = new Comparator<Entry<String,Integer>>(){
			@Override
			public int compare(Entry<String,Integer> thisEntry, Entry<String,Integer> thatEntry) {
				Integer thisV = thisEntry.getValue();
				Integer thatV = thatEntry.getValue();
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

	/**
	 * Performs a search and makes a WikiSearch object.
	 * 
	 * @param term
	 * @param index
	 * @return
	 */
	public static WikiSearch search(String term, JedisIndex index) {
		Map<String, Integer> map = index.getCounts(term);
		return new WikiSearch(map);
	}

	public static void main(String[] args) throws IOException {
		
		// make a JedisIndex
		// Jedis jedis = JedisMaker.make();
		// JedisIndex index = new JedisIndex(jedis); 
		
		// // search for the first term
		// String term1 = "java";
		// System.out.println("Query: " + term1);
		// WikiSearch search1 = search(term1, index);
		// search1.print();
		
		// // search for the second term
		// String term2 = "programming";
		// System.out.println("Query: " + term2);
		// WikiSearch search2 = search(term2, index);
		// search2.print();

		HashMap<String, Integer> hi = new HashMap<String, Integer> ();
		hi.put("a", 2);
		hi.put("b", 1);

		WikiSearch test = new WikiSearch(hi);

		System.out.println(test.sort());



			
		// compute the intersection of the searches
		// System.out.println("Query: " + term1 + " AND " + term2);
		// WikiSearch intersection = search1.and(search2);
		// intersection.print();
	}
}
