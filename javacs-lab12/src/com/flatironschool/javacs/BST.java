package com.flatironschool.javacs;



/** **************************************************************************
 *                     The  generic Binary Search Tree class.
 *
 * V.S.Adamchik 2010
 *****************************************************************************/

import java.util.*;

class BST <Node extends Comparable<Node>>
{
   public static void main(String[] args)
   {
      Integer[] a = {1,5,2,7,4};
      BST<Integer> bst = new BST<Integer>();
      for(Integer n : a) bst.insert(n, "url.com");

      bst.preOrderTraversal();
      System.out.println();

      //testing comparator
      //build a mirror BST with a rule:  Left > Parent > Right
      //code for the comparator at the bottom of the file
      // bst = new BST<Integer>(new MyComp1());
      // for(Integer n : a) bst.insert(n);

      // bst.preOrderTraversal();
      // System.out.println();
      // bst.inOrderTraversal();
      // System.out.println();


      // for(Integer n : bst) System.out.print(n);
      // System.out.println();

      // System.out.println(bst);

      //testing restoring a tree from two given traversals
      // bst.restore(new Integer[] {11,8,6,4,7,10,19,43,31,29,37,49},
      //                 new Integer[] {4,6,7,8,10,11,19,29,31,37,43,49});
      // bst.preOrderTraversal();
      // System.out.println();
      // bst.inOrderTraversal();
      // System.out.println();

      // //testing diameter
      // System.out.println("diameter = " + bst.diameter());
      // //testing width
      // System.out.println("width = " + bst.width());
   }


   private Node root;
   private Comparator<Node> comparator;
   int size = 0;

   public BST()
   {
      root = null;
      comparator = null;
   }

   public BST(Comparator<Node> comp)
   {
      root = null;
      comparator = comp;
   }

   private int compare(Node x, Node y)
   {
	  Integer xcount = x.count;
	  Integer ycount = y.count;
      if(comparator == null) return xcount.compareTo(ycount);
      else
      return comparator.compare(x,y);
   }
/*****************************************************
*
*            FIND
*
******************************************************/

// modified from code snippet found on stackoverflow
public Node findNode(String url, Node node){
	  if(node != null){
        if(node.name().equals(name)){
           return node;
        } else {
            Node foundNode = search(name, node.left);
            if(foundNode == null) {
                foundNode = search(name, node.right);
            }
            return foundNode;
         }
    } else {
        return null;
    }
}


/*****************************************************
*
*            INSERT
*
******************************************************/

   public void insert(int count, String url)
   {
      root = insert(root, count, url);
      size++;
   }

   private Node insert(Node p, int newNodeCount, String newNodeUrl)
   {
      // System.out.println(p);
      // System.out.println(toInsert);
      if (p == null)
         return new Node( newNodeCount, newNodeUrl);

      if (newNodeCount == p.count)
      	return p;

      if (newNodeCount < p.count)
         p.left = insert(p.left, newNodeCount, newNodeUrl);
      else
         p.right = insert(p.right, newNodeCount, newNodeUrl);

      return p;
   }

/*************************************************
 *
 *            toString
 *
 **************************************************/

//   public String toString()
//   {
//      StringBuffer sb = new StringBuffer();
//      for(Node entry : this) sb.append(entry.toString());
//
//      return sb.toString();
//   }

/*************************************************
 *
 *            TRAVERSAL
 *
 **************************************************/

   public void preOrderTraversal()
   {
      preOrderHelper(root);
   }
   private void preOrderHelper(Node r)
   {
      if (r != null)
      {
         System.out.print(r+" ");
         preOrderHelper(r.left);
         preOrderHelper(r.right);
      }
   }

   public void inOrderTraversal()
   {
      inOrderHelper(root);
   }
   private void inOrderHelper(Node r)
   {
      if (r != null)
      {
         inOrderHelper(r.left);
         System.out.print(r+" ");
         inOrderHelper(r.right);
      }
   }



/*****************************************************
*
*            TREE ITERATOR
*
******************************************************/

//   public Iterator<Node> iterator()
//   {
//      return new MyIterator();
//   }
   //pre-order
//   private class MyIterator implements Iterator<Node>
//   {
//      Stack<Node> stk = new Stack<Node>();
//
//      public MyIterator()
//      {
//         if(root != null) stk.push(root);
//      }
//      public boolean hasNext()
//      {
//         return !stk.isEmpty();
//      }
//
//      public Node next()
//      {
//         Node cur = stk.peek();
//         if(cur.left != null)
//         {
//            stk.push(cur.left);
//         }
//         else
//         {
//            Node tmp = stk.pop();
//            while( tmp.right == null )
//            {
//               if(stk.isEmpty()) return cur;
//               tmp = stk.pop();
//            }
//            stk.push(tmp.right);
//         }
//
//         return cur;
//      }//end of next()
//
//      public void remove()
//      {
//
//      }
//   }//end of MyIterator

/*****************************************************
*
*            the Node class
*
******************************************************/

   class Node
   {
      public Node left, right;
      int count;
      String url;
      boolean visited = fal

      public Node(String url, int count, Node l, Node r)
      {
         left = l; right = r;
         this.url = url;
         this.count = count;

      }

      public Node(int count, Node l, Node r)
      {
         left = l; right = r;
         this.count = count;

      }

        public Node(int count)
      {
         this(null, count, null, null);
      }

      public Node(int count, String url)
      {
         this(url, count, null, null);
      }

      public String toString()
      {
         return ("url: " + url + " occurence: " + count);
      }
   } //end of Node


}//end of BST



class MyComp1 implements Comparator<Integer>{
public int compare(Integer x, Integer y)
   {
        return y-x;
   }

}