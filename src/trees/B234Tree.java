package trees;

/**
 * 
 * @author mark lang
 *
 */
public class B234Tree {
	B234TreeNode root;
	
	/**
	 * constructor, creating an empty tree
	 */
	public B234Tree() {
		root = null;
	}
	
	
	/**
	 * add a new element to the tree, the element must not be in the tree before
	 */
	public void insert(int value) {
		if (root == null)
			root = new B234TreeNode(value);
		else
			root.insert(value);
		
		while (root.parent != null)
			root = root.parent;
	}
	
	/**
	 * delete a number from the tree 
	 */
	public void delete(int value) {
		root.delete(value) ;
	}
	
	/**
	 * print all elements in the tree out 
	 */
	public void printTheTree() {
		inOrderTraverse(root);
		System.out.println();
		System.out.println();
	}
	
	private void inOrderTraverse(B234TreeNode node) {
		if (node != null) {
			inOrderTraverse(node.children[0]);
			for (int i = 0; i < node.noKeys; i++) {
				System.out.print(node.keys[i] + " ");
				inOrderTraverse(node.children[i+1]);
			}
		}
	}
	
	
	
	///**************************TESTING*************
	
	public static void main(String args []) {
		B234Tree tree = new B234Tree();
		tree.insert(50);
		tree.insert(60);
		tree.insert(70);
		tree.insert(80);
		tree.insert(40);
		tree.insert(35);
		tree.insert(46);
		tree.insert(55);
		tree.insert(63);
		tree.insert(66);
		tree.insert(88);
		tree.insert(56);
		tree.insert(57);
		tree.insert(58);
		tree.insert(59);
		

		tree.printTheTree();
		
		tree.delete(56);
		B234TreeNode.display(tree.root);
		System.out.println("********************");
		
//		tree.delete(70);
//		tree.delete(80);
//		
//		tree.delete(46);
//		B234TreeNode.display(tree.root);
//		System.out.println("********************");
//		tree.delete(63);
//		B234TreeNode.display(tree.root);
//		System.out.println("********************");
//		tree.delete(55);
//		B234TreeNode.display(tree.root);
//		System.out.println("********************");		
//		tree.delete(35);
//		tree.delete(56);
//		B234TreeNode.display(tree.root);
//		System.out.println("********************");		
	}
	
}
