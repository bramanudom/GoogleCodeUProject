
package com.flatironschool.javacs;
import java.io.IOException;
import java.util. *;

public class Search{
	//String filename = "urls.txt";
	ListIndex listIndex = new ListIndex("urls.txt");
	BSTIndex bstIndex = new BSTIndex("urls.txt");
	String searchTerm, indexKind;
	String[] top = new String[5];


	public Search(String searchTerm, String indexKind){
		this.searchTerm = searchTerm;
		this.indexKind = indexKind;
	}

	public void indexPages() {
		long startTime = System.nanoTime();

		if (indexKind.equals("tree")) {
			try{
				bstIndex.add();
			}
			catch(IOException e){
				System.out.println(e);
			}
					}
		else {
			try{
				listIndex.add();
			}
			catch(IOException e){
				System.out.println(e);
			}
		}
		long stopTime = System.nanoTime();
		System.out.println("Indexing Elapsed time was " + (stopTime - startTime)/1000 + " miliseconds.");
	}

	public String[] topFive(){
		long startTime = System.nanoTime();
		String[] result;
		
		indexPages();
		if (indexKind.equals("tree")) {
			result = bstIndex.topFive(searchTerm);
		}
		else {
			result = listIndex.topFive(searchTerm);
		}
		System.out.println(Arrays.toString(result));
		long stopTime = System.nanoTime();
		System.out.println("Total Elapsed time was " + (stopTime - startTime)/1000 + " miliseconds.");
		return result;
	}

		//TODO: return the topfive URLS with the highestm relevance scores
		

		public static void main(String[] args){
		Search treeSearch = new Search("the", "tree");
		treeSearch.topFive();
		Search listSearch = new Search("the", "list");
		listSearch.topFive();

	}




}