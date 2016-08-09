package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

// import redis.clients.jedis.Jedis;


public class WikiCrawler {
	// keeps track of where we started
	private final String source;
	
	// the index where the results go
	//private JedisIndex index;
	
	// queue of URLs to be indexed
	private Queue<String> queue = new LinkedList<String>();
	private ArrayList<String> urlsSoFar = new ArrayList<String>();
	
	// fetcher used to get pages from Wikipedia
	final static WikiFetcher wf = new WikiFetcher();
	static File outFile = new File ("urls.txt");
	//static FileWriter fWriter;
	static PrintWriter pWriter;
	static int numUrls;

	

  

	/**
	 * Constructor.
	 * 
	 * @param source
	 * @param index
	 */
	public WikiCrawler(String source) {
		this.source = source;
		//this.index = index;
		queue.offer(source);
		numUrls = 0;
		try {
	     //fWriter = new FileWriter (outFile);
	     pWriter = new PrintWriter (outFile);
		}

		catch (IOException e){
			System.out.println(e);
		}
	}

	/**
	 * Returns the number of URLs in the queue.
	 * 
	 * @return
	 */
	public int queueSize() {
		return queue.size();	
	}

	/**
	 * Gets a URL from the queue and indexes it.
	 * @param b 
	 * 
	 * @return Number of pages indexed.
	 * @throws IOException
	 */
	public String crawl() throws IOException {
		if (queue.isEmpty()) {
            return null;
        }
        // System.out.println(queueSize());
        String url = queue.poll();
        // System.out.println(url);
        System.out.println("Crawling " + url);
 
        Elements paragraphs;

      	paragraphs = wf.fetchWikipedia(url);
      	numUrls++;
      	if (!urlsSoFar.contains(url)){
      	urlsSoFar.add(url);
        pWriter.println(url);
       	queueInternalLinks(paragraphs);
       }
       // index.indexPage(url, paragraphs);
          
        return url;
   
	}
	/**
	 * Parses paragraphs and adds internal links to the queue.
	 * 
	 * @param paragraphs
	 */
	// NOTE: absence of access level modifier means package-level
	void queueInternalLinks(Elements paragraphs) {

		// parse through all the paragraphs 
		for (Element paragraph : paragraphs){
			Iterable<Node> iter = new WikiNodeIterable(paragraph);
			for (Node node: iter) {

				Elements pUrls = paragraph.select("a[href]");
				for (Element pUrl : pUrls ){
					String url = pUrl.attr("href");
					boolean internal = url.startsWith("/wiki/");
					if (internal) {
							queue.offer(pUrl.attr("abs:href"));
						}
				}

			// if (node instanceof Element){
			// 	Element accesibleNode = (Element)node;
			// 	String tag = accesibleNode.tagName();
			// 		if(tag.equals("a")){
			// 			String url = accesibleNode.attr("abs:href");
			// 			// make sure the link is an internal link (links to another wikipedia page)
			// 			boolean internal = url.contains("wikipedia");	
			// 		}
			}
		}

	}
        



	public static void main(String[] args) throws IOException {
		//System.out.println("I'm in the main");
		
		// make a WikiCrawler
		
		String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		WikiCrawler wc = new WikiCrawler(source);
		
		while (wc.numUrls < 10){
		wc.crawl();
		}

		wc.pWriter.close();
		
		// for testing purposes, load up the queue
		// Elements paragraphs = wf.fetchWikipedia(source);
		
		// wc.queueInternalLinks(paragraphs);

		// System.out.println(wc.queue.peek());
		

		// String[] urls = wc.queue.toArray(new String[0]);

		// for (String url: urls){
		// 	System.out.println(url);
		// }






		// System.out.println(wc.queueSize());

		// //loop until we index a new page
		// String res;
		// do {
		// 	res = wc.crawl(false);

            
		// } while (res == null);
		
		// Map<String, Integer> map = index.getCounts("the");
		// for (Entry<String, Integer> entry: map.entrySet()) {
		// 	System.out.println(entry);
		// }
	}
}
