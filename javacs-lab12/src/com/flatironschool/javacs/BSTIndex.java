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

import com.flatironschool.javacs.BST.BSTNode;

public class BSTIndex {
	HashMap<String, BST> index = new HashMap<String, BST>();
	Scanner scanny;
	String term;
	String fileName;

	public BSTIndex(String filename){
		this.fileName = filename;

	}

	public static void main(String[] args){
		BSTIndex bstIndex = new BSTIndex("urls.txt");
		try{
			bstIndex.add();
		} catch (IOException e){
			System.out.println(e);
		}

		System.out.println("There are " + bstIndex.index.size() + " words in the index");
		System.out.println(Arrays.toString(bstIndex.topFive("the")));
	}

	public String[] topFive(String term) {
	   BST termTree = index.get(term);
	   String[] result = new String[5];


	   for(int i=0; i < 5; i++){
	   	BSTNode max = termTree.findMax(termTree.root);
	   	result[i] = max.url;
	   }

	   return result; 
   }


   public void incrementTermCount(String term, String url) {
		if (index.containsKey(term)) {
			BST entryBST = index.get(term);
			BSTNode foundNode = entryBST.findBSTNode(url, entryBST.root);

			if ( foundNode != null){
				foundNode.count++;
			} else {
				entryBST.insert(1, url);
			}
		}

		else{
			BST newTermTree = new BST();
			newTermTree.insert(1, url);
			index.put(term, newTermTree);
		}
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



}
