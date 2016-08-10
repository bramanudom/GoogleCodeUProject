package com.flatironschool.javacs;

import java.util.HashMap;

public class BSTIndex {
	HashMap<String, BST> index = new HashMap<String, BST>();
	public String[] topFive(String term) {
	   BST termTree = index.get(term);
	   return null;
   }
}
