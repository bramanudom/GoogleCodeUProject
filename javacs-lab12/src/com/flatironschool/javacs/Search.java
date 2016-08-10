
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



	public String[] topFive(){
		long startTime = System.currentTimeMillis();
		String[] result;
		if (indexKind.equals("tree")) {
			try{
				bstIndex.add();
			}
			catch(IOException e){
				System.out.println(e);
			}
			
			result = bstIndex.topFive(searchTerm);
		}
		else {
			try{
				listIndex.add();
			}
			catch(IOException e){
				System.out.println(e);
			}
			result = listIndex.topFive(searchTerm);
		}
		System.out.println(Arrays.toString(result));
		long stopTime = System.currentTimeMillis();
		System.out.println("Elapsed time was " + (stopTime - startTime) + " miliseconds.");
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