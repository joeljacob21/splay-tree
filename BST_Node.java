package SPLT_A4;

public class BST_Node {
	String data;
	BST_Node left;
	BST_Node right;
	BST_Node par; //parent...not necessarily required, but can be useful in splay tree
	boolean justMade; //could be helpful if you change some of the return types on your BST_Node insert.
	//I personally use it to indicate to my SPLT insert whether or not we increment size.

	BST_Node(String data){ 
		this.data=data;
		this.justMade=true;
	}

	BST_Node(String data, BST_Node left,BST_Node right,BST_Node par){ //feel free to modify this constructor to suit your needs
		this.data=data;
		this.left=left;
		this.right=right;
		this.par=par;
		this.justMade=true;
	}

	// --- used for testing  ----------------------------------------------
	//
	// leave these 3 methods in, as is (meaning also make sure they do in fact return data,left,right respectively)

	public String getData(){ return data; }
	public BST_Node getLeft(){ return left; }
	public BST_Node getRight(){ return right; }

	// --- end used for testing -------------------------------------------


	// --- Some example methods that could be helpful ------------------------------------------
	//
	// add the meat of correct implementation logic to them if you wish

	// you MAY change the signatures if you wish...names too (we will not grade on delegation for this assignment)
	// make them take more or different parameters
	// have them return different types
	//
	// you may use recursive or iterative implementations

	public boolean containsNode(String s){ 
		if(data.equals(s))return true;
		if(data.compareTo(s)>0){//s lexiconically less than data
			if(left==null)return false;
			return left.containsNode(s);
		}
		if(data.compareTo(s)<0){
			if(right==null)return false;
			return right.containsNode(s);
		}
		return false; //shouldn't hit
	} //note: I personally find it easiest to make this return a Node,(that being the node splayed to root), you are however free to do what you wish.
	public boolean insertNode(String s){ 
		if(data.compareTo(s)>0){
			if(left==null){
				left=new BST_Node(s);
				left.par = this;
				return true;
			}
			return left.insertNode(s);
		}
		if(data.compareTo(s)<0){
			if(right==null){
				right=new BST_Node(s);
				right.par = this;
				return true;
			}
			return right.insertNode(s);
		}
		return false;
	} //Really same logic as above note
	
	public boolean removeNode(String s){ 
		if(data==null)return false;
		if(data.equals(s)){
			if(left!=null){
				data=left.findMax().data;
				left.removeNode(data);
				if(left.data==null)left=null;
			}
			else if(right!=null){
				data=right.findMin().data;
				right.removeNode(data);
				if(right.data==null)right=null;
			}
			else data=null;
			return true;
		}
		else if(data.compareTo(s)>0){
			if(left==null)return false;
			if(!left.removeNode(s))return false;
			if(left.data==null)left=null;
			return true;
		}
		else if(data.compareTo(s)<0){
			if(right==null)return false;
			if(!right.removeNode(s))return false;
			if(right.data==null)right=null;
			return true;
		}
		return false;
	} //I personal do not use the removeNode internal method in my impl since it is rather easily done in SPLT, feel free to try to delegate this out, however we do not "remove" like we do in BST
	
	public BST_Node findMin(){
		if(left!=null)return left.findMin();
		return this;
	}
	public BST_Node findMax(){
		if(right!=null)return right.findMax();
		return this;
	}
	public int getHeight(){
		int l=0;
		int r=0;
		if(left!=null)l+=left.getHeight()+1;
		if(right!=null)r+=right.getHeight()+1;
		return Integer.max(l, r);
	}

	public void splay() {
		if(this.par == null) {
			return;
		} else if(this.par.par == null) {
			if(this.par.right != null && this.par.right.getData().equals(this.getData())) {
				BST_Node temp = this.left; //storing b
				this.left = this.par; //moving parent to left child of x
				this.par.right = temp; //setting b as previous parent right
				this.left.par = this; //setting pointer
				if(this.left.right!=null)this.left.right.par = this.left;
				this.par = null;
				this.splay();
			} else {
				BST_Node temp = this.right; //storing b
				this.right = this.par; //moving parent to left child of x
				this.par.left = temp; //setting b as previous parent right
				this.right.par = this; //setting pointer
				if(this.right.left!=null)this.right.left.par = this.right;
				this.par = null;
				this.splay();;
			}
		} else {
			if(this.par.par.right != null && this.par.par.right.right != null && 
					this.par.par.right.right.getData().equals(this.getData())) {
				BST_Node parent = this.par.par.par;
				BST_Node temp = this.left; // storing b
				this.left = this.par;
				this.left.right = temp;
				temp = this.left.left;
				this.left.left = this.left.par;
				this.left.left.right = temp;
				this.left.par = this;
				this.left.left.par = this.left;
				this.par = parent;
				if(this.par != null)this.par.right = this;
				if(this.left.right != null)this.left.right.par = this.left;
				if(this.left.left.right != null)this.left.left.right.par = this.left.left;
				this.splay();
			} else if(this.par.par.left != null && this.par.par.left.left != null && 
					this.par.par.left.left.getData().equals(this.getData())) {
				BST_Node parent = this.par.par.par;
				BST_Node temp = this.right; // storing b
				this.right = this.par;
				this.right.left = temp;
				temp = this.right.right;
				this.right.right = this.right.par;
				this.right.right.left = temp;
				this.right.par = this;
				this.right.right.par = this.right;
				this.par = parent;
				if(this.par != null)this.par.left = this;
				if(this.right.left!= null)this.right.left.par = this.right;
				if(this.right.right.left != null)this.right.right.left.par = this.right.right;
				this.splay();
			} else if(this.par.par.right != null && this.par.par.right.left != null && 
					this.par.par.right.left.getData().equals(this.getData())) {
				BST_Node parent = this.par.par.par;
				BST_Node tempRight = this.right;
				BST_Node tempLeft = this.left;
				this.right = this.par;
				this.right.left = tempRight;
				this.left = this.right.par;
				this.left.right= tempLeft;
				this.par = parent;
				if(this.right.left != null)this.right.left.par = this.right;
				if(this.left.right != null)this.left.right.par = this.left;
				this.right.par = this;
				this.left.par = this;
				this.splay();
			} else {
				BST_Node parent = this.par.par.par;
				BST_Node tempLeft = this.left;
				BST_Node tempRight = this.right;
				this.left = this.par;
				this.left.right = tempLeft;
				this.right = this.left.par;
				this.right.left = tempRight;
				this.par = parent;
				if(this.left.right != null)this.left.right.par = this.left;
				if(this.right.left != null)this.right.left.par = this.right;
				this.left.par = this;
				this.right.par = this;
				this.splay();
			}
		}
		
		
	}
	protected BST_Node findYoMama(String s) {
		if(data.equals(s))return this;
		if(data.compareTo(s)>0){//s lexiconically less than data
			if(left==null)return null;
			return left.findYoMama(s);
		}
		if(data.compareTo(s)<0){
			if(right==null)return null;
			return right.findYoMama(s);
		}
		return null;
	}
	//you could have this return or take in whatever you want..so long as it will do the job internally. As a caller of SPLT functions, I should really have no idea if you are "splaying or not"
	//I of course, will be checking with tests and by eye to make sure you are indeed splaying
	//Pro tip: Making individual methods for rotateLeft and rotateRight might be a good idea!

}