	import java.util.*;
public class BinarySearchTree
{
	wordNode root;
	
	public BinarySearchTree()
	{
		root = null;
	}
	

	public void insert(String data)
	{
		// Case: tree is empty
		if (root == null)
		{
			root = new wordNode(new Words(data));
			return;
		}

		// Case: tree is not empty
		wordNode p = root;

		// move p down as far as we can
		while(true)
		{
			if(data.compareTo(p.getWords()) == 0)
			{
				p.repeats();
				return;
			}
			else if(data.compareTo(p.getWords()) < 0 && p.getLeft() != null)
			{
				p = p.getLeft();
			}
			else if (data.compareTo(p.getWords()) > 0 && p.getRight() != null)
			{
				p = p.getRight();
			}
			else
			{
				break;
			}
		}
		wordNode w = new wordNode(new Words(data));
		if(data.compareTo(p.getWords()) < 0)
		{
			p.setLeft(w);
			
		}
		else // data.compareTo(p.getData()) > 0 
		{
			p.setRight(w);
		}
	 rebalanceInsertionPath(w);
	}

  	private void rebalanceInsertionPath(wordNode node)
	{
		// Get the path nodes from the root to the node
		ArrayList<wordNode> nodes = getAllNodesOnPath(node.getWords());

		// Reverse the list to get the path from the node to the root
		Collections.reverse(nodes);


		// Set the heights of the nodes along the path, which may have
		// changed by the insertion of the new node
		for(int i = 0; i < nodes.size(); i++)
		{
			setHeight(nodes.get(i));
		}

		// Rebalance all the nodes (subtrees) on path from inserted node
		// to the root
		//
		// To rebalance a node, we need the pointer to its parent so that
		// we can attach the rebalanced subtree to the whole tree
		//
		//	The first node that may need rebalancing is at index 2 (grandparent
		// of the inserted node), and the parent of this node is at index 3
		//
		for(int i = 3; i < nodes.size(); i++)
		{
			wordNode pp = nodes.get(i);		// pointer to parent
			wordNode pc = nodes.get(i-1);	// pointer to child, the node that may
													// actually need to be rebalanced
			if (pp.getLeft() == pc)
			{
				wordNode localRoot = rebalance(pc);
				pp.setLeft(localRoot);
				if( localRoot != pc)// some rotation has actually been made
				{
					break;
				}
			}
			else
			{
				wordNode localRoot = rebalance(pc);
				pp.setRight(localRoot);
				if( localRoot != pc)	// some rotation has actually been made
				{
					break;
				}
			}
		}

		// .. This is done outside the loop because root does not
		// .. have a parent
		root = rebalance(root);
	}

	private ArrayList <wordNode> getAllNodesOnPath(String key)
	// The tree is not empty and the key is in the tree
	{
		ArrayList <wordNode> nodes = new ArrayList<wordNode>();

		// Add root as the first node
		// Otherwise, it will not be included
		nodes.add(root);

		wordNode p = root;
		while (p != null && (p.getWords().compareTo(key) != 0))
		{
			if (key.compareTo(p.getWords()) < 0)
			{
				p = p.getLeft();
			}
			else
			{
				p = p.getRight();
			}
			nodes.add(p);
		}

		return nodes;
   }

	private void setHeight(wordNode node)
	{
		wordNode leftChild = node.getLeft();
		wordNode rightChild = node.getRight();
		int leftHeight = leftChild == null? -1: leftChild.getHeight();
		int rightHeight = rightChild == null? -1: rightChild.getHeight();

		if (leftHeight >= rightHeight)
		{
			node.setHeight(leftHeight + 1);
		}
		else
		{
			node.setHeight(rightHeight + 1);
		}
   }

   private wordNode rebalance(wordNode node)
   {
      int diff = getHeightDifference(node);

      if(diff == 2)
      {
         if(getHeightDifference(node.getLeft()) == -1)
         {
            node = leftRightRotation(node);
         }
         else
         {
            node = rightRotation(node);
         }
      }
      else if(diff == -2)
      {
         if(getHeightDifference(node.getRight()) == 1)
         {
            node = rightLeftRotation(node);
         }
         else
         {
            node = leftRotation(node);
         }
      }

      return node;
   }

   private int getHeightDifference(wordNode node)
   {
      wordNode leftChild = node.getLeft();
      wordNode rightChild = node.getRight();
      int leftHeight = leftChild == null? -1: leftChild.getHeight();
      int rightHeight = rightChild == null? -1: rightChild.getHeight();

      return leftHeight - rightHeight;
   }

   private wordNode  rightRotation(wordNode g)
   {
      // Message to let know about the rotation


      wordNode p = g.getLeft();
      wordNode x = p.getRight();   // x is right child of p
      p.setRight(g);
      g.setLeft(x);
      setHeight(g);
      setHeight(p);
      return p;
   }

   private wordNode leftRotation(wordNode g)
   {


      wordNode p = g.getRight();
      wordNode x = p.getLeft();    // x is left child of p
      p.setLeft(g);
      g.setRight(x);
      setHeight(g);
      setHeight(p);
      return p;
   }

   private wordNode rightLeftRotation(wordNode g)
   {


      wordNode p = g.getRight();
      g.setRight(rightRotation(p));
      return leftRotation(g);
   }

   private wordNode leftRightRotation(wordNode g)
   {
      wordNode p = g.getLeft();
      g.setLeft(leftRotation(p));
      return rightRotation(g);
   }


   // REMOVE NODE FROM AVL TREE
   //
   public boolean remove(String key)
   {
      // Find the node to be deleted
      wordNode deleted = root;
      wordNode parent = null;
      while (true)
      {
			if (deleted != null && key.compareTo(deleted.getWords()) == 0)
			{
				break;
			}
      	else if (key.compareTo(deleted.getWords()) < 0)
         {
            parent = deleted;
            deleted = deleted.getLeft();
         }
         else if (key.compareTo(deleted.getWords()) > 0)
         {
            parent = deleted;
            deleted = deleted.getRight();
         }
      }

      if (deleted == null) // The node is not found
      {
         return false;
      }
      else if (deleted.getLeft() == null && deleted.getRight() == null)
      {
         deleteLeafNode(deleted, parent);
         return true;
      }
      else if ( (deleted.getLeft() != null && deleted.getRight() == null)
                || (deleted.getLeft() == null && deleted.getRight() != null))
      {
         deleteNodeWithOneChild(deleted, parent);
         return true;
      }
      else
      {
         deleteNodeWith2Children(deleted, parent);
         return true;
      }
   }

   private void deleteLeafNode(wordNode deleted, wordNode parent)
   {
      if (deleted == root)
      {
         root = null;
      }
      else if (parent.getLeft() == deleted)
      {
         parent.setLeft(null);
         rebalanceDeletionPath(parent);
      }
      else
      {
         parent.setRight(null);
         rebalanceDeletionPath(parent);
      }
   }

   private void deleteNodeWithOneChild(wordNode deleted, wordNode parent)
   {
      // Get the child of the deleted node
      wordNode child = null;
      if (deleted.getLeft() != null)
      {
         child = deleted.getLeft();
      }
      else
      {
         child = deleted.getRight();
      }

      // Let root or parent of deleted node point to the child
      if (deleted == root)
      {
         root = child;
      }
      else if(deleted == parent.getLeft())
      {
         parent.setLeft(child);
         rebalanceDeletionPath(parent);
      }
      else
      {
         parent.setRight(child);
         rebalanceDeletionPath(parent);
      }
   }

   private void deleteNodeWith2Children(wordNode deleted, wordNode parent)
   {
      wordNode largest = deleted.getLeft(); // find largest in left subtree
      wordNode parentOfLargest = deleted;
      while (largest.getRight() != null)
      {
         parentOfLargest = largest;
         largest = largest.getRight();
      }

      deleted.setData(largest.getWords());    // copy largest’s data to "deleted node"

      if (largest.getLeft() == null)    // delete largest
      {
         deleteLeafNode(largest, parentOfLargest);
      }
      else
      {
         deleteNodeWithOneChild(largest, parentOfLargest);
      }
   }

   private void rebalanceDeletionPath(wordNode parent)
   {
      // Get the path nodes from the root to the parent
      ArrayList<wordNode> nodes = getAllNodesOnPath(parent.getWords());

      // Reverse the list to get the path from parent to root
      Collections.reverse(nodes);

      // Set the heighs of the nodes along the path
      for(int i = 0; i < nodes.size(); i++)
      {
         setHeight(nodes.get(i));
      }

      // Rebalance the nodes along the path
      // the first node that may need rebalancing is at index 0 (parent of the
      // deleted node), and its parent is at index 1
      //
      for(int i = 1; i < nodes.size(); i++)
      {
		 wordNode pp = nodes.get(i);
         wordNode pc = nodes.get(i-1);

         if (pp.getLeft() == pc)
         {
            pp.setLeft(rebalance(pc));
         }
         else
         {
            pp.setRight(rebalance(pc));
         }
      }

      // .. This is done outside the loop because root does not
      // .. have a parent
      root = rebalance(root);
   }
	
	public wordNode search( String key )
	{
		// case: Tree is empty
		if( root == null)
		{
			return null;
		}
		else{
		// Case: Tree is not empty
		wordNode p = root;
		while( true )
		{
			if( p == null)								// not found
			{
				return null;
			}
			else if( key.compareTo(p.getWords()) == 0 )	// found
			{
				return p;
			}
			else if( key.compareTo(p.getWords()) < 0)	// move left
			{
				p  = p.getLeft();
			}
			else										// move right
			{
				p = p.getRight();
			}
		}
		}
		
	}
	
	//Methods to save binary search tree to an ArrayList
	public void traverseInOrder(ArrayList<Words> list)
	{
		traverseInOrder(root,list);
	}

	private void traverseInOrder(wordNode node, ArrayList<Words> list)
	{
		if (node != null)
		{
			traverseInOrder( node.getLeft(),list);
			visit(node,list);
			traverseInOrder( node.getRight(), list);
		}
	}
	
	private void visit( wordNode node, ArrayList<Words> list)
	{
		list.add(node.getWordO());
	}
	
	


}