package trees;


public class RedBlackTree {
	
	RedBlackTreeNode root;
	
	/**
	 * 
	 */
	public RedBlackTree() {
		root = null;
	}
	
	/**
	 *   
	 */
	public void insert(int value) {
		if (root == null)
			root = new RedBlackTreeNode(value, RedBlackTreeNode.BLACK);
		else
			if (!root.isAnElement(value))
				root.insert(value);
		
		while (root.parent != null)
			root = root.parent;
		root.color = RedBlackTreeNode.BLACK;
	}
	
	/**
	 * this method doesnt delete the last node of the tree
	 */
	public boolean delete(int value) {
		boolean result = root.delete(value);
		while (root.parent != null)
			root = root.parent;

		root.color = RedBlackTreeNode.BLACK;
		return result;
	}
	
	/**
	 * 
	 */
	public void displayTree() {
		root.displayTree();
	}
	
	/**
	 * left first then current node then right
	 *
	 */
	public void inOrderTraversing() {
		root.inOrderTraversing(root);
		System.out.println();
	}
	
	
	
	
	
	
	
	
	
	
	// ********************TESTING********************
	private static void test1() {
		//10 7 18 3 8 11 22 26 15
		RedBlackTreeNode tree = new RedBlackTreeNode(10, RedBlackTreeNode.BLACK);
		tree.insert(7);
		tree.insert(18);
		//tree.displayTree();
		//System.out.println("********************");
		
		tree.insert(3);
		tree.insert(8);
		tree.insert(11);
		tree.insert(22);
		//tree.displayTree();
		//System.out.println("********************");
		
		tree.insert(26);
		tree.insert(15);
		tree.insert(2);
//		tree.displayTree();
//		System.out.println("********************");
		
		tree.delete(8);
//		tree.displayTree();
//		System.out.println("********************");
		tree.delete(10);
		tree.displayTree();
		System.out.println("********************");
//		tree.delete(8);
//		tree.displayTree();
//		System.out.println("********************");
	}
	
	private static void test2() {
		//260, 240, 140, 320, 250, 170, 60, 100, 20, 40, 290, 280, 270, 30, 265, 275, 277, 278
		RedBlackTree tree = new RedBlackTree();
		tree.insert(260);
		tree.insert(240);
		//tree.displayTree();
		//System.out.println("********************");
		
		tree.insert(140);
		tree.insert(320);
		tree.insert(250);
		tree.insert(170);
//		tree.displayTree();
//		System.out.println("**********6*********");
		
		tree.insert(60);
		tree.insert(100);
		tree.insert(20);
		tree.insert(40);
		
//		tree.displayTree();
//		System.out.println("*********10*********");
		
		tree.insert(290);
		tree.insert(280);
		
//		tree.displayTree();
//		System.out.println("*********12*********");
		
		tree.insert(270);
		tree.insert(30);
		tree.insert(265);
		tree.insert(275);
//		tree.displayTree();
//		System.out.println("********************");
		tree.insert(277);
		tree.insert(278);
		
		tree.inOrderTraversing();
		tree.delete(260);
		tree.displayTree();
		System.out.println("********************");
		
		tree.delete(8);
		
//		tree.displayTree();
//		System.out.println("********************");
		
		tree.delete(10);
		
		tree.displayTree();
		System.out.println("********************");
		
//		tree.delete(8);
		tree.delete(100);
		
		tree.displayTree();
		System.out.println("********************");
		
		tree.delete(275);
		tree.delete(60);
		tree.delete(30);
		tree.delete(240);
		tree.delete(40);
		tree.delete(290);
		tree.displayTree();
		System.out.println("********************");
		
		tree.delete(260);
		tree.delete(140);
		tree.delete(20);
		tree.delete(170);
		tree.delete(250);
		tree.delete(277);
		tree.delete(270);
		tree.delete(265);
		tree.delete(280);
		tree.delete(278);
		tree.displayTree();
		System.out.println("********************");
	}
	
	//testing
	public static void main(String[] args) {
		test2();
	}
	

}
