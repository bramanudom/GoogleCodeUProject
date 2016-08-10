
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
		System.out.println("Indexing Elapsed time was " + (stopTime - startTime)/1000 + " microseconds.");
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
		System.out.println("Total Elapsed time was " + (stopTime - startTime)/1000 + " microseconds.");
		return result;
	}

		//TODO: return the topfive URLS with the highestm relevance scores
		

		public static void main(String[] args){
//			System.out.println("Search terms: ");
			String searchTerm;
			String indexType;
			if (args.length > 0) {
			    try {
			        searchTerm = args[0];
			        indexType = args[1];
			        
			        Search treeSearch = new Search(searchTerm, indexType);
					treeSearch.topFive();
			    } catch (NumberFormatException e) {
			        System.err.println("Please provide a relevant search term and choose either list or tree");
			        System.exit(1);
			    }
			}
			    
//			Search treeSearch = new Search(searchTerm, indexType);
//			treeSearch.topFive();
//			Search listSearch = new Search(searchTerm, "list");
//			listSearch.topFive();

	}




}