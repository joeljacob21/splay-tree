package SPLT_A4;

public class SPLT implements SPLT_Interface{
	private BST_Node root;
	private int size;

	public SPLT() {
		this.size = 0;
	} 

	public BST_Node getRoot() { //please keep this in here! I need your root node to test your tree!
		return root;
	}

	@Override
	public void insert(String s) {
		if (empty()) {
			root = new BST_Node(s);
			size += 1;
			return;
		} else if (contains(s)) {
		} else {
			size += 1;
			root.insertNode(s);
			BST_Node newRoot = findNode(s);
			newRoot.splay();
			root = newRoot;
		}
	}

	@Override
	public void remove(String s) {
		if (!contains(s)) {
			return;
		} else {
			if (root.data.equals(s) && size == 1) {
				root = null;
				size -= 1;
			} else {
				size -= 1;
				root.removeNode(s);
			}
		}
		
	}

	@Override
	public String findMin() {
		if (empty()) {
			return null;
		} else {
			root = root.findMin();
			root.splay();
			return root.data;
		}
	}

	@Override
	public String findMax() {
		if (empty()) {
			return null;
		} else {
			root = root.findMax();
			root.splay();
			return root.data;
		}
	}

	@Override
	public boolean empty() {
		return size == 0;
	}

	@Override
	public boolean contains(String s) {
		if(empty()) {
			return false;
		} if(!root.containsNode(s)) {
			return false;
		}
		root = findNode(s);
		root.splay();
		return true;
	}
	
	private BST_Node findNode(String s) {
		return root.findYoMama(s);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int height() {
		if (empty()) {
			return -1;
		} else {
			return root.getHeight();
		}
	}  

}