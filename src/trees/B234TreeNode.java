package trees;

/**
 * 
 * @author mark lang
 *
 */




public class B234TreeNode {
	int [] keys;
	B234TreeNode [] children;
	B234TreeNode parent;
	int noKeys;
	
	final int MAX_KEYS = 3;
	
	
	/**
	 * constructor
	 */
	public B234TreeNode() {
		keys = new int [MAX_KEYS];
		children = new B234TreeNode [MAX_KEYS + 1];
		noKeys = 0;
		parent = null;
	}
	
	/**
	 * constructor, a new node with only 1 value and 2 children
	 */
	public B234TreeNode(int value, B234TreeNode child1, B234TreeNode child2, B234TreeNode parent) {
		keys = new int [MAX_KEYS];
		keys[0] = value;
		
		children = new B234TreeNode [MAX_KEYS + 1];
		children[0] = child1;
		if (children[0] != null)
			children[0].parent = this;
		children[1] = child2;
		if (children[1] != null)
			children[1].parent = this;
		
		noKeys = 1;
		this.parent = parent;
	}
	
	/**
	 * constructor, a new node with only 1 value 
	 */
	public B234TreeNode(int value) {
		keys = new int [MAX_KEYS];
		keys[0] = value;
		
		children = new B234TreeNode [MAX_KEYS + 1];
		
		noKeys = 1;
		parent = null;
	}
	
	/**
	 * insert a value into the tree
	 */
	public void insert(int value) {
		insert(this, value);
	}
	
	public void insert(B234TreeNode node, int value) {
		if (node.noKeys == MAX_KEYS) {
			node.split();
			insert(node.parent, value);
			return;
		}
		if (value < node.keys[0]) {
			if (node.children[0] == null)
				node.addKey(value, 0);
			else 
				insert(node.children[0], value);
			return;
		}
		
		for (int i = 0; i < node.noKeys -1; i++)
			if (value > node.keys[i] && value < node.keys[i+1]) {
				if (node.children[i+1] == null)
					node.addKey(value, i+1);
				else 
					insert(node.children[i+1], value);
				return;
			}
		
		if (value > node.keys[node.noKeys -1]) {
			if (node.children[node.noKeys] == null)
				node.addKey(value, node.noKeys);
			else 
				node.insert(node.children[node.noKeys], value);
			return;
		}
	}
	
	/**
	 * delete a key, it must be in the tree
	 */
	public void delete(int value) {
		B234TreeNode node = B234TreeNode.find(this, value);
		
		for (int i = 0; i < node.noKeys; i++)
			if (node.keys[i] == value) {
				if (node.children[0] != null) {
					B234TreeNode replaceNode = B234TreeNode.findMin(node.children[i+1]); 
					node.keys[i] = replaceNode.keys[0];
					delete(replaceNode, 0);
				}
				else
					delete(node, i);
			}
			
	}
	
	// delete a node's key at position pos
	public void delete(B234TreeNode node, int pos) {
		if (node.noKeys == 1) {
			node.fixAfterDelete(node);
			return;
		}
		
		for (int i = pos; i < node.noKeys - 1; i++) {
			node.keys[i] = node.keys[i+1];
		}
		node.noKeys --;
	}
	
	// adjust the tree after delete
	public void fixAfterDelete(B234TreeNode node) {
		B234TreeNode leftSib = null;
		B234TreeNode rightSib = null;
		B234TreeNode parent = node.parent;
		
		int pos = 0;
		for (int i = 0; i <= parent.noKeys; i++)
			if (parent.children[i] == node) {
				pos = i;
				if (i > 0) 
					leftSib = parent.children[i-1];
				if (i < parent.noKeys)
					rightSib = parent.children[i+1];
			}
		
		// fusion
		if ((leftSib == null || leftSib.noKeys == 1) && (rightSib == null || rightSib.noKeys == 1)) 
			if (leftSib != null) {
				leftSib.keys[1] = parent.keys[pos - 1];
				leftSib.children[2] = node.children[1];
				if (leftSib.children[2] != null)
					leftSib.children[2].parent = leftSib;
				leftSib.noKeys = 2;
				                                    
				parent.keys[pos - 1] = parent.keys[pos];
				parent.children[pos] = parent.children[pos + 1];
				parent.noKeys --;
				
				if (parent.noKeys == 0 && parent.parent != null)
					fixAfterDelete(parent);
				
				//parent is the root
				if (parent.noKeys == 0)
					if (parent.parent == null) {
						parent.noKeys = leftSib.noKeys;
						for (int i = 0; i <= parent.noKeys; i++) {
							if (i < leftSib.noKeys)
								parent.keys[i] = leftSib.keys[i];
							parent.children[i] = leftSib.children[i];
						}
						return;
					}
				
				return;
			}
			else {
				node.keys[0] = parent.keys[pos];
				node.keys[1] = rightSib.keys[0];
				node.children[1] = rightSib.children[0];
				if (node.children[1] != null)
					node.children[1].parent = node;
				node.children[2] = rightSib.children[1];
				if (node.children[2] != null)
					node.children[2].parent = node;
				node.noKeys = 2;
				
				parent.keys[pos] = parent.keys[pos+1];
				parent.children[pos+1] = parent.children[pos+2];
				
				parent.noKeys--;
				
				if (parent.noKeys == 0 && parent.parent != null)
					fixAfterDelete(parent);
				
				// parent is the root having no nodes
				if (parent.noKeys == 0)
					if (parent.parent == null) {
						parent.noKeys = node.noKeys;
						for (int i = 0; i <= node.noKeys; i++) {
							if (i < node.noKeys)
								parent.keys[i] = node.keys[i];
							parent.children[i] = node.children[i];
							if (parent.children[i] != null)
								parent.children[i].parent = parent;
						}
						
				return;
			}
		}
		
		// transfer
		if (leftSib != null && leftSib.noKeys > 1) {
			node.keys[0] = parent.keys[pos -1];
			node.noKeys = 1;
			for (int i = 3; i>=1; i--)
				node.children[i] = node.children[i-1];
			node.children[0] = leftSib.children[leftSib.noKeys];
			if (node.children[0] != null)
				node.children[0].parent = node;
			
			parent.keys[pos - 1] = leftSib.keys[leftSib.noKeys - 1];
			
			leftSib.children[leftSib.noKeys] = null;
			leftSib.noKeys --;
			
			return;
		}
		
		if (rightSib != null && rightSib.noKeys > 1) {
			node.keys[0] = parent.keys[pos];
			node.noKeys = 1;
			node.children[1] = rightSib.children[0];
			node.children[1].parent = node;
			
			parent.keys[pos] = rightSib.keys[0];
			
			rightSib.keys[0] = rightSib.keys[1];
			rightSib.children[0] = rightSib.children[1];
			rightSib.keys[1] = rightSib.keys[2];
			rightSib.children[1] = rightSib.children[2];
			rightSib.keys[2] = rightSib.keys[3];
			rightSib.children[2] = rightSib.children[3];
			rightSib.noKeys --;
			
			return;
		}
	}
	
	/**
	 * find a node which has a certain value  
	 */
	public B234TreeNode find (int value) {
		return B234TreeNode.find(this, value); 
	}
	
	//recursive version
	public static B234TreeNode find(B234TreeNode node, int value) {
		for (int i = 0; i < node.noKeys; i++) {
			if (node.keys[i] == value)
				return node;
		}
		
		if (value < node.keys[0])
			return find(node.children[0], value);
		
		
		for (int i = 0; i < node.noKeys - 1; i++)
			if (value > node.keys[i] && value < node.keys[i+1])
				return find(node.children[i+1], value);
		
		if (value > node.keys[node.noKeys - 1]) 
			return find(node.children[node.noKeys], value);
			
		return null;
	}
	
	
	/**
	 * find the minimum value node in a tree
	 * 
	 */
	public static B234TreeNode findMin(B234TreeNode node) {
		if (node.children[0] == null)
			return node;
		else 
			return findMin(node.children[0]);
	}
	
	
	
	/**
	 * add a key with 2 new children, for splitting
	 */
	private void addKey(int value, B234TreeNode child1, B234TreeNode child2) {
		if (value < keys[0]) {
			addKey(value,0);
			children[2] = children[1];
			children[0] = child1;
			children[1] = child2;
			return;
		}
		
		if (noKeys == 2 && value > keys[0] && value < keys[1]) {
			addKey(value, 1);
			children[3] = children[2];
			children[1] = child1;
			children[2] = child2;
			return;
		}
		if (value > keys[noKeys-1]) {
			addKey(value, noKeys);
			children[noKeys] = child2;
			children[noKeys - 1] = child1;
			return;
		}
	}
	
	
	/**
	 * add a key to a certain position in the array of keys
	 */
	private void addKey(int value, int pos) {
		noKeys ++;
		
		for (int i = noKeys - 1; i > pos; i--) {
			keys[i] = keys[i-1];
			children[i+1] = children[i]; 
		}
		
		keys[pos] = value;
		//children[pos] = null;
	}
	
	/**
	 * split current node, the node must have 3 keys
	 */
	private void split() {
		int keyUp = keys[1];
		B234TreeNode parent;
		B234TreeNode newSibling;
		
		newSibling = new B234TreeNode(keys[2], children[2], children[3], null);
		noKeys = 1;
		children[2] = null;
		children[3] = null;
		
		if (this.parent == null) {
			parent = new B234TreeNode(keyUp, this, newSibling, null);
		}
		else {
			parent = this.parent;
			parent.addKey(keyUp, this, newSibling);
		}
		
		this.parent = parent;
		newSibling.parent = parent;
	}
	
	/**
	 * display the tree for testing purpose
	 */
	public static void display(B234TreeNode node) {
		if (node == null)
			return;
			
		for (int i = 0; i < node.noKeys; i++)
			System.out.print(node.keys[i] + " ");
		System.out.println();
		
		for (int i = 0; i < node.noKeys + 1; i++)
			display(node.children[i]);
		
		System.out.println("   up");
	}
	
}
