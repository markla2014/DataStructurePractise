package trees;

/**
 * 
 *  This redblack tree is implemted based on the definition of redblack tree defined 
 *  	in: http://en.wikipedia.org/wiki/Red-black_tree 
 *  This code is mostly for the purpose of understanding howa red black tree works
 * 
 * A Red Black Tree has 5 attributes:
 * 			node value
 * 			node color (red/black (1/0))
 * 			left node
 * 			right node
 * 			parent node
 * 
 * Note: 
 * 		An empty red black tree has null root node, so there is no need 
 * 			to replace default constructor
 *  	This tree does not allow repeating values, so the value inserted value 
 *  		must be checked (not in this class) before being inserted into the tree
 *  	Every attribute in this class is referred using this.
 *  	When a new tree is created, the root must be BLACK
 *   
 *  	The tree must have at least 1 node
 * 		 	
 * 
 * @author Bao Hoang Le
 *
 */


public class RedBlackTreeNode {
	protected int value;
	protected int color;
	protected RedBlackTreeNode left, right, parent;
	
	public static final int RED = 1;
	public static int BLACK = 0;
	
	/**
	 * Constructing a red-black tree with 1 node
	 * 
	 */
	public RedBlackTreeNode (int value, int color) {
		this.value = value;
		this.color = color;
		this.left = null;
		this.right = null;
		this.parent = null;
	}
	
	/*
	 *  rotating
	 */
	private void rotateRight(RedBlackTreeNode node) {
		// x is the node, y is parent node
		RedBlackTreeNode parent = node.parent;
		RedBlackTreeNode grandparent = parent.parent;
		
		parent.left = node.right;
		if (parent.left != null)
			parent.left.parent = parent;
		node.right = parent;
		node.right.parent = node;
		node.parent = grandparent;
		
		if (grandparent != null)
			if (parent == grandparent.left)
				grandparent.left = node;
			else
				grandparent.right = node;
	}
	
	private void rotateLeft(RedBlackTreeNode node) {
		RedBlackTreeNode parent = node.parent;
		RedBlackTreeNode grandparent = parent.parent;
		
		parent.right = node.left;
		if (parent.right != null)
			parent.right.parent = parent;
		node.left = parent;
		node.left.parent = node;
		node.parent = grandparent;
		
		if (grandparent != null)
			if (parent == grandparent.left)
				grandparent.left = node;
			else
				grandparent.right = node;
	}
	
	/**
	 * insert a value into the tree, after that adjust the tree 
	 * so that the tree is still a valid red black tree 
	 * 
	 */
	public void insert(int value) {
		RedBlackTreeNode currentNode = insert(this, value);
		adjustAfterInsert(currentNode);
	}
	
	// adjust the tree after inserting, so that it s still a valid red black tree
	private void adjustAfterInsert(RedBlackTreeNode currentNode) {
		// if this is the root
		if (currentNode.parent == null) {
			currentNode.color = BLACK;
			return;
		}
		
		// if it s already been valid 
		if (color(currentNode.parent) == BLACK)
			return;
		
		RedBlackTreeNode parent = currentNode.parent;
		RedBlackTreeNode grandparent = parent.parent;
		
		// case 1a
		if (parent == grandparent.left) 
			if (color(grandparent.right) == RED) {
				parent.color = BLACK;
				grandparent.right.color = BLACK;
				grandparent.color = RED;
				adjustAfterInsert(grandparent);
				return;
			}
		
		// case 1b
		if (parent == grandparent.right)
			if (color(grandparent.left) == RED) {
				parent.color = BLACK;
				grandparent.left.color = BLACK;
				grandparent.color = RED;
				adjustAfterInsert(grandparent);
				return;
			}
		
		// case 2a
		if (parent == grandparent.left && currentNode == parent.right)
			if (color(grandparent.right) == BLACK) {
				rotateLeft(currentNode);
				adjustAfterInsert(currentNode.left);
				return;
			}
		// case 2b
		if (parent == grandparent.right && currentNode == parent.left)
			if (color(grandparent.left) == BLACK) {
				rotateRight(currentNode);
				adjustAfterInsert(currentNode.right);
				return;
			}
		
		// case 3a
		if (parent == grandparent.left && currentNode == parent.left) 
			if (color(grandparent.right) == BLACK) {
				rotateRight(parent);
				parent.color = BLACK;
				grandparent.color = RED;
				return;
			}
		// case 3b 
		if (parent == grandparent.right && currentNode == parent.right)
			if (color(grandparent.left) == BLACK) {
				rotateLeft(parent);
				parent.color = BLACK;
				grandparent.color = RED;
				return;
			}
	}
	
	// recursive insert, return the node where the value is inserted, a null leaf 
	private RedBlackTreeNode insert(RedBlackTreeNode tree, int value) {
		if (value > tree.value)
			if (tree.right == null) {
				tree.right = new RedBlackTreeNode(value, RED);
				tree.right.parent = tree;
				return tree.right;
			}
			else return(insert(tree.right, value));
		if (value < tree.value)
			if (tree.left == null) {
				tree.left = new RedBlackTreeNode(value, RED);
				tree.left.parent = tree;
				return tree.left;
			}
			else return(insert(tree.left, value));
		
		// it never reaches here
		return null;
	}
	
	/**
	 * delete a value, after that adjust the tree
	 * so that the tree is still a valid red black tree.
	 * 
	 * return true if the the value is found and deleted, false other wise
	 * 
	 */
	public boolean delete(int value) {
		RedBlackTreeNode node = find(value);
		if (node == null) 
			return false;
		
		delete(node);
		return true;
	}
	
	private void delete (RedBlackTreeNode node) {
		
		// case 1: the deleted node is a leaf (not a null leaf)  
		if (node.left == null && node.right == null) {
			if (color(node) == BLACK)
				adjustAfterDelete(node);
			if (node == node.parent.left) {
				node.parent.left = null;
			}
			else 
				node.parent.right = null;
			return ;
		}

		// case 2a 
		if (node.left == null && node.parent != null)
			if (node.parent.left == node) {
				node.parent.left = node.right;
				if (node.parent.left != null)
					node.parent.left.parent = node.parent;
				if (color(node) == BLACK) {
					adjustAfterDelete(node.right);
				}
				return ;
			}
			else {				
				node.parent.right = node.right;
				if (node.parent.right != null)
					node.parent.right.parent = node.parent;
				if (color(node) == BLACK) {
					adjustAfterDelete(node.right);
				}
				return ;
			}
		// case 2b
		if (node.right == null && node.parent != null)
			if (node.parent.left == node) {
				node.parent.left = node.left;
				if (node.parent.left != null)
					node.parent.left.parent = node.parent;
				if (color(node) == BLACK) {
					adjustAfterDelete(node.left);
				}
				return ;
			}
			else {				
				node.parent.right = node.left;
				if (node.parent.right != null)
					node.parent.right.parent = node.parent;
				if (color(node) == BLACK) {
					adjustAfterDelete(node.left);
				}
				return ;
			}
		
		// case 3
		RedBlackTreeNode replaceNode = findMin(node.right);
		node.value = replaceNode.value;
		delete(replaceNode);
		
		return ;
		
	}
	
	private void adjustAfterDelete(RedBlackTreeNode node) {
		// case 1
		if (node.parent == null || color(node) == RED) {
			node.color = BLACK;
			return;
		}
		
		RedBlackTreeNode sibling;
		RedBlackTreeNode parent = node.parent;
		
		if (node == parent.left)
			sibling = parent.right;
		else 
			sibling = parent.left;
		
		// case 2
		if (color(sibling) == BLACK && color(sibling.left) == BLACK 
			&& color(sibling.right) == BLACK) {
			sibling.color = RED;
			adjustAfterDelete(parent);
			return;
		}
		
		// case 3
		if (color(sibling) == RED) {
			if (node == parent.left) {
				rotateLeft(sibling);
				sibling.color = BLACK;
				parent.color = RED;
				adjustAfterDelete(node);
				return;
			}
			else {
				rotateRight(sibling);
				sibling.color = BLACK;
				parent.color = RED;
				adjustAfterDelete(node);
				return;
			}
		}
		
		// case 4
		if (color(sibling) == BLACK) {
			RedBlackTreeNode nearerNephew;
			RedBlackTreeNode fartherNephew;
			
			if (node == parent.left) {
				nearerNephew = sibling.left;
				fartherNephew = sibling.right;
				
				if (color(fartherNephew) != RED) {
					rotateRight(nearerNephew);
					nearerNephew.color = BLACK;
					nearerNephew = nearerNephew.left;
					fartherNephew = fartherNephew.parent;
					fartherNephew.color = RED;  
				}				
				
				//when the fartherNephew is RED
				sibling.color = parent.color;
				parent.color = BLACK;
				fartherNephew.color = BLACK;
				rotateLeft(sibling);
			}
			else {				
				nearerNephew = sibling.right;
				fartherNephew = sibling.left;
				
				if (color(fartherNephew) != RED) {
					rotateLeft(nearerNephew);
					nearerNephew.color = BLACK;
					nearerNephew = nearerNephew.right;
					fartherNephew = fartherNephew.parent;
					fartherNephew.color = RED;
				}
				
				//when the fartherNephew is RED
				sibling.color = parent.color;
				parent.color = BLACK;
				fartherNephew.color = BLACK;
				rotateRight(sibling);
			}
		}
	}
	
	/**
	 * check whether a value is in the tree
	 * 
	 */
	public boolean isAnElement(int value) {
		return find(value) != null;
	}
	
	/**
	 * 
	 */
	private RedBlackTreeNode find(int value) {
		return find(this, value);
	}
	
	//recursive find
	private RedBlackTreeNode find(RedBlackTreeNode node, int value) {
		if (node == null)
			return null;
		if (value <  node.value)
			return find(node.left, value);
		else 
			if (value > node.value)
				return find(node.right, value);
			else 
				return node;
	}
	
	/**
	 * find the minimum value in the tree
	 */
	public RedBlackTreeNode findMin(RedBlackTreeNode node) {
		if (node.left == null)
			return node;
		else 
			return findMin(node.left); 
	}
	
	
	
	
	/*
	 * display the tree for testing purpose 
	 */
	public void displayTree() {
		System.out.println("1 is RED                      O is BLACK");
		displayTree(this);
	}
	//recursive method
	public void displayTree(RedBlackTreeNode node) {
		if (node != null) {
			System.out.print("  " + node.value + " " + node.color);
			if (node.parent != null)
				System.out.print("   parent: " + node.parent.value);
			System.out.println();
			
			if (node.left != null)
				displayTree(node.left);
			if (node.right != null)
				displayTree(node.right);
		}
		System.out.println("up");
	}
	
	/**
	 * left first then current node then right
	 */
	public void inOrderTraversing(RedBlackTreeNode node) {
		if (node != null) {
			inOrderTraversing(node.left);
			System.out.print(node.value + " ");
			inOrderTraversing(node.right);
		}
	}
	
	/**
	 * 
	 */
	public static int color(RedBlackTreeNode node) {
		if (node == null)
			return BLACK;
		else 
			return node.color; 
	}
	
	
	
}
