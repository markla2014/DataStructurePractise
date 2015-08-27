package trees;

class AvlTree {
	private node root;

	public AvlTree() {

		root = null;
	}

	protected class node {
		protected int height;
		protected int key;
		protected node left;
		protected node right;

		protected node(int k) {
			key = k;
			height = 0;
			left = null;
			right = null;
		}

		protected int getH() {
			return height;

		}
	}

	public void insert(int n) {
		if (root == null) {
			root = new node(n);
		} else
			root = insert(root, n);
		check();
	}

	public node insert(node current, int t) {

		if (current.key < t) {
			if (current.right == null) {
				current.right = new node(t);
			} else
				current.right = insert(current.right, t);
		}
		if (current.key > t) {
			if (current.left == null) {
				current.left = new node(t);

			} else
				current.left = insert(current.left, t);
		}

		// current.height=height(current.right)-height(current.left);
		// if(current.height==2||current.height==-2){
		// node temp=rotation(current);
		// if(current==root){root=temp;}
		// current=temp;
		// }

		return current;
	}

	public int height(node t) {

		int p = 0;
		int p1 = 0;
		int p2 = 0;
		if (t == null) {
			return -1;
		} else {
			int t1 = t.key;
			p1 = height(t.left);
			p2 = height(t.right);
			p = Math.max(p1, p2) + 1;
			return p;
		}
	}

	public void delete(int k) {
		if (root == null) {
			System.out.println("that's empty");
		} else {
			root = delete(root, k);
			check();
		}
	}

	public void check() {
		root = check(root);

	}

	public node check(node current) {
		if (current != null) {
			current.left = check(current.left);
			current.right = check(current.right);
			int t = current.height;
			current.height = height(current.right) - height(current.left);

			if (current.height == 2 || current.height == -2) {
				node temp = rotation(current);
				if (current == root) {
					root = temp;
				}
				current = temp;
			}

		}
		return current;
	}

	public node delete(node t, int n) {

		if (t != null) {
			int test = t.key;
			if (t.key > n) {
				t.left = delete(t.left, n);
			} else if (t.key < n) {
				t.right = delete(t.right, n);
			} else {

				if (t.left == null && t.right == null) {
					t = null;

				} else if (t.left != null && t.right == null) {
					t = t.left;

				} else if (t.right != null && t.left == null) {
					t = t.right;

				} else {
					node p = t.right;
					while (p.left != null)
						p = p.left;
					node q = p.left;
					p.left = q.right;
					q.right = t.right;
					p = q;
					t = p;

				}

			}

		}

		return t;

	}

	// ////////////////////////////////////rotation
	// part////////////////////////////////////////
	public node rotation(node x) {
		node temp;
		node temp2;
		int test = x.key;
		if (x.height == -2) {
			temp = x.left;
			if (temp.height == -1) {
				x.left = temp.right;
				temp.right = x;
				temp.height = 0;
				x.height = 0;
				return temp;
			} else if (temp.height == 1) {
				temp2 = temp.right;
				temp.right = temp2.left;
				temp2.left = temp;
				x.left = temp2.right;
				temp2.right = x;
				temp.height = 0;
				temp2.height = 0;
				x.height = 0;
				return temp2;
			}
		} else if (x.height == 2) {
			temp = x.right;
			if (temp.height == 1) {
				x.right = temp.left;
				temp.left = x;
				temp.height = 0;
				x.height = 0;
				return temp;
			} else if (temp.height == -1) {

				temp2 = temp.left;
				temp.left = temp2.right;
				temp2.right = temp;
				x.right = temp2.left;
				temp2.left = x;
				temp.height = 0;
				temp2.height = 0;
				x.height = 0;
				return temp2;
			}
		}
		// x.height=height(x.right)-height(x.left);
		return x;

	}

	// ////////////////////////////////dkd//////////////////////////////////
	public void print() {

		print(root);

	}

	public void print(node current) {
		if (current != null) {
			System.out.println(current.key + " " + current.height);
			print(current.left);
			print(current.right);
		}
	}

	public static void main(String argv[]) {

		AvlTree a = new AvlTree();
		System.out.println("/////////////////////////////////////////");
		a.insert(10);
		a.insert(6);
		// a.insert(7);
		a.insert(4);
		// a.insert(13);
		// a.insert(12);
		//
		// a.insert(12);
		// a.insert(9);
		// a.insert(14);
		// a.insert(6);
		// a.insert(11);
		// a.insert(20);
		// a.insert(4);
		// a.insert(7);
		// a.insert(10);
		// a.insert(8);
		// a.delete(4);
		/*
		 * test
		 */

		// a.insert(15);
		// a.insert(23);
		// a.insert(45);
		// a.insert(67);
		// a.insert(89);
		// a.insert(68);
		// a.insert(74);

		a.print();

	}

}
