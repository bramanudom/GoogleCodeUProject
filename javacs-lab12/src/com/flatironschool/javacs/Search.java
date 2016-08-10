
package com.flatironschool.javacs;
import java.io.IOException;
import java.util. *;

public class Search{
	//String filename = "urls.txt";
	ListIndex index = new ListIndex("urls.txt");
	// BSTIndex index;
	String searchTerm;
	String[] top = new String[5];


	public Search(String searchTerm){
		this.searchTerm = searchTerm;
		try{
			index.add();
		}
		catch(IOException e){
			System.out.println(e);
		}
	}



	public String[] topFive(){
		return index.topFive(searchTerm);

		//TODO: return the topfive URLS with the highestm relevance scores
		
	}

		public static void main(String[] args){
		Search newSearch = new Search("the");
		System.out.print(newSearch.topFive());
	}




}