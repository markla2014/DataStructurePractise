package trees;

public class splaying {
  private node root;
  public splaying(){
	  
  }
  private class node{
	  protected  node left;
	   protected node right;
	   protected int key;
	   protected node parent;
	   public node(int k,node l,node r,node p){
		   left=l;
		   right=r;
		   key=k;
		   parent=p;
	   }
	  
  }
  public void insert(int k){
	 
	 root=insert(root,k);
	 
  }
  public node insert(node current,int k){
	  if(current==null){
		  current=new node(k,null,null,null);
	  }else{
	  if(current.key>k){
		  if(current.left!=null){
		      int test=current.left.key;
		 return insert(current.left,k);}
		  else {
			  current.left=new node(k,null,null,current);
			
			  return  splayingAT(current.left);
		  }
	  }else if(current.key<k){
		  if(current.right!=null){
		      int test=current.right.key;
		  return insert(current.right,k);
           }
		  else {
			current.right=new node(k,null,null,current);
		   return  splayingAT(current.right);
		  }
	  }
	 }
	 return current;
  }
  public boolean find(int k){
	  if(find(root,k)==null){
		  return false;
	  }else 
		  return true;
	  
  }
  public node find(node current,int k){
	  if(current.key>k){
		  return find(current.left,k);
	  }else if(current.key<k)
	     return find(current.right,k);
	     else if (current.key==k)
	    	 return current;
	     else 
	    	 return null;
  }
  public node splayingAT(node current){
	  if(current.parent.key>current.key){
		  current.parent.left=current.right;
		  current.right=current.parent;
		  current.right.parent=current.parent;
		  current.parent=current.parent.parent;
		  current.parent.parent=current;
		  return current;
	  }else{
		  current.parent.right=current.left;
		  current.left=current.parent;
		  current.left.parent=current.parent;
		  current.parent=current.parent.parent;
		  current.parent.parent=current;
		  return current;
	  }
  }
  public void print() {

		print(root);

	}

	public void print(node current) {
		if (current != null) {
			System.out.println(current.key + " ");
			print(current.left);
			//System.out.println( "up ");
			print(current.right);
		}
	}
	public static void main(String[] args){
		splaying tree=new splaying();
		tree.insert(4);
		tree.insert(5);
		tree.insert(6);
		tree.insert(12);
		tree.insert(34);
		tree.insert(78);
		tree.insert(45);
		tree.print();
	}
  }
