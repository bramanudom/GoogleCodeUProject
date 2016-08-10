package com.flatironschool.javacs;

/** **************************************************************************
 *                     The  generic Binary Search Tree class.
 *
 * V.S.Adamchik 2010
 *****************************************************************************/

import java.util.*;

//import org.jsoup.BSTNodes.BSTNode;

class BST<BSTNode extends Comparable<BSTNode>> {
	public static void main(String[] args) {
		Integer[] a = { 1, 5, 2, 7, 4 };
		BST<Integer> bst = new BST<Integer>();
		for (Integer n : a)
			bst.insert(n, "url.com");

		bst.preOrderTraversal();
		System.out.println();

		// testing comparator
		// build a mirror BST with a rule: Left > Parent > Right
		// code for the comparator at the bottom of the file
		// bst = new BST<Integer>(new MyComp1());
		// for(Integer n : a) bst.insert(n);

		// bst.preOrderTraversal();
		// System.out.println();
		// bst.inOrderTraversal();
		// System.out.println();

		// for(Integer n : bst) System.out.print(n);
		// System.out.println();

		// System.out.println(bst);

		// testing restoring a tree from two given traversals
		// bst.restore(new Integer[] {11,8,6,4,7,10,19,43,31,29,37,49},
		// new Integer[] {4,6,7,8,10,11,19,29,31,37,43,49});
		// bst.preOrderTraversal();
		// System.out.println();
		// bst.inOrderTraversal();
		// System.out.println();

		// //testing diameter
		// System.out.println("diameter = " + bst.diameter());
		// //testing width
		// System.out.println("width = " + bst.width());
	}

	BSTNode root;
	Comparator<BSTNode> comparator;
	int size = 0;

	public BST() {
		root = null;
		comparator = null;
	}

	public BST(Comparator<BSTNode> comp) {
		root = null;
		comparator = comp;
	}

	private int compare(BSTNode x, BSTNode y) {
		Integer xcount = x.count;
		Integer ycount = y.count;
		if (comparator == null)
			return xcount.compareTo(ycount);
		else
			return comparator.compare(x, y);
	}

	/*****************************************************
	 *
	 * FIND
	 *
	 ******************************************************/

	// modified from code snippet found on stackoverflow
	public BSTNode findBSTNode(String url, BSTNode BSTNode) {
		if (BSTNode != null) {
			if (BSTNode.url.equals(url)) {
				return BSTNode;
			} else {
				BSTNode foundBSTNode = findBSTNode(url, BSTNode.left);
				if (foundBSTNode == null) {
					foundBSTNode = findBSTNode(url, BSTNode.right);
				}
				return foundBSTNode;
			}
		} else {
			return null;
		}
	}

	/*****************************************************
	 *
	 * INSERT
	 *
	 ******************************************************/

	public void insert(int count, String url) {
		root = insert(root, count, url);
		size++;
	}

	private BSTNode insert(BSTNode p, int newBSTNodeCount, String newBSTNodeUrl) {
		// System.out.println(p);
		// System.out.println(toInsert);
		if (p == null)
			return new BSTNode(newBSTNodeCount, newBSTNodeUrl);

		if (newBSTNodeCount == p.count)
			return p;

		if (newBSTNodeCount < p.count)
			p.left = insert(p.left, newBSTNodeCount, newBSTNodeUrl);
		else
			p.right = insert(p.right, newBSTNodeCount, newBSTNodeUrl);

		return p;
	}

	/*************************************************
	 *
	 * toString
	 *
	 **************************************************/

	// public String toString()
	// {
	// StringBuffer sb = new StringBuffer();
	// for(BSTNode entry : this) sb.append(entry.toString());
	//
	// return sb.toString();
	// }

	/*************************************************
	 *
	 * TRAVERSAL
	 *
	 **************************************************/

	public void preOrderTraversal() {
		preOrderHelper(root);
	}

	private void preOrderHelper(BSTNode r) {
		if (r != null) {
			System.out.print(r + " ");
			preOrderHelper(r.left);
			preOrderHelper(r.right);
		}
	}

	public void inOrderTraversal() {
		inOrderHelper(root);
	}

	private void inOrderHelper(BSTNode r) {
		if (r != null) {
			inOrderHelper(r.left);
			System.out.print(r + " ");
			inOrderHelper(r.right);
		}
	}

	public BSTNode findMax(BSTNode root) { // pass the root

		if (root.right != null && root.right.visited == false) {
			findMax(root.right);
		} else {
			root.visited = true;
			return root;
		}
		return root;
	}

	/*****************************************************
	 *
	 * TREE ITERATOR
	 *
	 ******************************************************/

	// public Iterator<BSTNode> iterator()
	// {
	// return new MyIterator();
	// }
	// pre-order
	// private class MyIterator implements Iterator<BSTNode>
	// {
	// Stack<BSTNode> stk = new Stack<BSTNode>();
	//
	// public MyIterator()
	// {
	// if(root != null) stk.push(root);
	// }
	// public boolean hasNext()
	// {
	// return !stk.isEmpty();
	// }
	//
	// public BSTNode next()
	// {
	// BSTNode cur = stk.peek();
	// if(cur.left != null)
	// {
	// stk.push(cur.left);
	// }
	// else
	// {
	// BSTNode tmp = stk.pop();
	// while( tmp.right == null )
	// {
	// if(stk.isEmpty()) return cur;
	// tmp = stk.pop();
	// }
	// stk.push(tmp.right);
	// }
	//
	// return cur;
	// }//end of next()
	//
	// public void remove()
	// {
	//
	// }
	// }//end of MyIterator

	/*****************************************************
	 *
	 * the BSTNode class
	 *
	 ******************************************************/

	class BSTNode {
		public BSTNode left, right;
		int count;
		String url;
		boolean visited = false;

		public BSTNode(String url, int count, BSTNode l, BSTNode r) {
			left = l;
			right = r;
			this.url = url;
			this.count = count;

		}

		public BSTNode(int count, BSTNode l, BSTNode r) {
			left = l;
			right = r;
			this.count = count;

		}

		public BSTNode(int count) {
			this(null, count, null, null);
		}

		public BSTNode(int count, String url) {
			this(url, count, null, null);
		}

		public String toString() {
			return ("url: " + url + " occurence: " + count);
		}
	} // end of BSTNode

}// end of BST

class MyComp1 implements Comparator<Integer> {
	public int compare(Integer x, Integer y) {
		return y - x;
	}

}