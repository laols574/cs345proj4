import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class  Proj04_AVL_student<K extends Comparable<K>, V>
implements Proj04_Dictionary<K,V> {
	
	private int count = 0;
	private String treeType;
	private Proj04_BSTNode<K,V> root;
	/**
	 * constructor 
	 * Proj04_AVL_student
	 * this constructor creates a AVL tree
	 * @param treeType
	 */
	public Proj04_AVL_student(String treeType) {
		this.treeType = treeType;
		this.root = null;
	}
	/**
	 * set - operates as an insertion method 
	 * @param K key 
	 * @param V value 
	 */
	@Override
	public void set(K key, V value) {
		if(root == null) {
			root.key = key;
			root.value = value;
		}
		Proj04_BSTNode<K,V> curr = root;
		while(curr != null) {
			if(curr.key.compareTo(key)  > 0) {
				if(curr.left == null) {
					curr.left = new Proj04_BSTNode<K, V>(key, value);
				}
				else {
					curr = curr.left;
				}
			}
			else if(curr.key.compareTo(key) < 0) {
				if(curr.right == null) {
					curr.right = new Proj04_BSTNode<K, V>(key, value);
				}
				else {
					curr = curr.right;
				}
			}
		}
		
		AVLTreeUpdateHeight(root);
		AVLTreeUpdateCount(root);
		rebalanceAVLTree(root);
		//rebalance and do counts/heights
	}
	


	
	private Proj04_BSTNode<K,V> rebalanceAVLTree(Proj04_BSTNode<K,V> node) {
		int balance = AVLTreeGetBalance(node);
		//left left
		if(balance > 1 && node.key.compareTo(node.left.key) < 0) {
			return rotateRight(node);
		}
		//right right 
		else if(balance < -1 && node.key.compareTo(node.right.key) > 0){
			return rotateLeft(node);

		}
		//left right 
		else if(balance > 1 && node.key.compareTo(node.left.key) > 0){
			Proj04_BSTNode temp = rotateRight(node);
			return rotateLeft(temp);
		}
		//left right 
		else if(balance < -1 && node.key.compareTo(node.right.key) < 0){
			Proj04_BSTNode temp = rotateLeft(node);
			return rotateRight(temp);
		}
		return node;
		
	}
	/** AVLTreeGetBalance
	 * 
	 * 
	 * @param node
	 */
	private int AVLTreeGetBalance(Proj04_BSTNode<K,V> node) {
		int leftHeight = -1;
		int rightHeight = -1;
		if(node.left != null)
			leftHeight = node.left.height;
		if(node.right != null)
			rightHeight = node.right.height;
		return leftHeight - rightHeight;
	}
	/** rotateRight
	 * 
	 */
	private Proj04_BSTNode<K,V> rotateRight(Proj04_BSTNode<K,V> r) {
		Proj04_BSTNode<K,V> rNode = r.left;
		Proj04_BSTNode<K,V> rSubTree = rNode.right;
		
		rNode.right = r;
		r.left = rSubTree;
		
		AVLTreeUpdateHeight(rNode);
		AVLTreeUpdateHeight(r);
		AVLTreeUpdateCount(r);
		AVLTreeUpdateCount(rNode);

		return rNode;
	}
	/**
	 * 
	 * @param r
	 */
	private Proj04_BSTNode<K,V> rotateLeft(Proj04_BSTNode<K,V> r) {
		Proj04_BSTNode<K,V> lNode = r.right;
		Proj04_BSTNode<K,V> lSubTree = lNode.left;
		
		lNode.left = r;
		r.right = lSubTree;
		
		AVLTreeUpdateHeight(lNode);
		AVLTreeUpdateHeight(r);
		AVLTreeUpdateCount(r);
		AVLTreeUpdateCount(lNode);

		return lNode;
	}
	private void AVLTreeUpdateCount(Proj04_BSTNode<K,V> node) {
		node.count = (1 + getCount(node.left) + getCount(node.right));
	}
	

	/** AVLTreeUpdateHeight
	 * this function updates the height value of the given node
	 */
	private void AVLTreeUpdateHeight(Proj04_BSTNode<K,V> node) {
		int leftHeight = -1;
		int rightHeight = -1;
		if(node.left != null) {
			leftHeight = node.left.height;
		}
		if(node.right != null) {
			rightHeight = node.right.height;
		}
		node.height = max(rightHeight, leftHeight) + 1;
	}
	/**
	 * max
	 * returns the maximum value of the two inputed integers
	 * @param one - first integer
	 * @param two - second integer 
	 * @return the larger value
	 */
	private int max(int one, int two) {
		if(one > two){ return one;}
		else {return two;}
	} 
	
	@Override
	public V get(K key) {
		Proj04_BSTNode<K, V> node = search(root, key);
		if(node == null)
			return null;
		return node.value;
	}
	
	private Proj04_BSTNode<K, V> search(Proj04_BSTNode<K,V> root, K key){
		if(root == null || root.key.equals(key)) {
			return root;
		}
		if(root.key.compareTo(key) < 0){
			 return search(root.right, key);
		}
		else{
			 return search(root.left, key);
		}
	}
	@Override
	public void remove(K key) {
		root = remove_helper(root, key);
	}
	
	private Proj04_BSTNode<K, V> remove_helper(Proj04_BSTNode<K, V> root, K key) {
		//normal delete recursion 
		if(root == null)
			return root;
		if(root.key.compareTo(key) < 0) {
			root.left = remove_helper(root.left, key);
		}
		else if (root.key.compareTo(key) > 0) {
			root.right = remove_helper(root.right, key);
		}
		//if the key equals 
		else if(root.key.compareTo(key) == 0){
			//case 1: node has one child or no children 
			if(root.left == null || root.right == null) {
				//selects the node that is not null if there is one 
				Proj04_BSTNode temp = null;
				if(temp == root.left)
					temp = root.right;
				if(temp == root.right)
					temp = root.left;
				//no children 
				if(temp == null) {
					temp = root;
					root = null;
				}
				//one child 
				else {
					root = temp;
				}
			}
		}
		if(root == null)
			return null;
		
		//update the height of the current node
		AVLTreeUpdateHeight(root);
		AVLTreeUpdateCount(root);
		rebalanceAVLTree(root);
		return root;
		//rebalance
		
	}
	/** int getSize()
	 *
	 * @return Returns the number of keys stored in the tree.  This *MUST* run in
	 * O(1) time.
	 */
	public int getSize()
	{
		return getCount(root);
	}
	private int getCount(Proj04_BSTNode<K,V> node)
	{
		if (node == null)
			return 0;
		return node.count;
	}
	/**
	 * the x = change(x)
	 */
	@Override
	public void inOrder(K[] keysOut, V[] valuesOut, String[] auxOut) {
		inOrderHelper(root, 0, keysOut, valuesOut, auxOut);
	}
	/**
	 * inOrderHelper
	 * this function implements a in order traversal and stores the values in 
	 * arrays provided 
	 * @param root - root node 
	 * @param index - index counter 
	 * @param keysOut - array for keys 
	 * @param valuesOut - array for values 
	 * @param countsOut - array for counts 
	 * @return
	 */
	private int inOrderHelper(Proj04_BSTNode<K,V> root, int index, K[] keysOut, V[] valuesOut, String[] auxOut) {
		if(root == null) {
			return index;
		}
		index = inOrderHelper(root.left, index, keysOut, valuesOut, auxOut);
		if(keysOut != null)
			keysOut[index] = root.key;
		if(valuesOut != null)
			valuesOut[index] = root.value;
		if(auxOut != null) {
			String aux = "height " + root.height + " count " + root.count;
			auxOut[index] = aux;
		}
		index++;
		index = inOrderHelper(root.right, index, keysOut, valuesOut, auxOut);
		return index;

	}
	/**
	 * the x = change(x)
	 */
	@Override
	public void postOrder(K[] keysOut, V[] valuesOut, String[] auxOut) {
		postOrderHelper(root, 0, keysOut, valuesOut, auxOut);
		
	}
	/**
	 * postOrderHelper
	 * this function implements a post order traversal and stores the values in 
	 * arrays provided 
	 * @param root - root node 
	 * @param index - index counter 
	 * @param keysOut - array for keys 
	 * @param valuesOut - array for values 
	 * @param countsOut - array for counts 
	 * @return
	 */
	private int postOrderHelper(Proj04_BSTNode<K,V> root, int index, K[] keysOut, V[] valuesOut, String[] auxOut) {
		if(root == null) {
			return index;
		}
		index = postOrderHelper(root.left, index, keysOut, valuesOut, auxOut);
		index = postOrderHelper(root.right, index, keysOut, valuesOut, auxOut);
		if(keysOut != null)
			keysOut[index] = root.key;
		if(valuesOut != null)
			valuesOut[index] = root.value;
		if(auxOut != null) {
			String aux = "height " + root.height + " count " + root.count;
			auxOut[index] = aux;
		}
		index++;
		return index;
	}
	
	/* void genDebugDot()
	 *
	 * Generates a \texttt{.dot} file which represents the tree; if this
	 * is called multiple times, then they must all have different
	 * filenames.
	 *
	 * The generated files must be placed in the *current* directory, and
	 * must not include any whitespace in the name.
	 */
	public void genDebugDot()
	{
		
		File file = new File(treeType + count++ + ".dot");
		try {
			FileWriter writer = new FileWriter(file);
			writer.write("digraph{\n");
			//all nodes 
			debugAllNodes(root, writer);
			//all node attachments 
			debugAttachments(root, writer);

			writer.write("}\n");
			writer.close();
		} catch (IOException e) {
			System.err.println("IOException");
			e.printStackTrace();
		}
		
	}
	/**
	 * debugAttachments
	 * this function returns all the connections between nodes  
	 * @param root - root node 
	 * @param writer - the file writer to be written to 
	 */
	private void debugAttachments(Proj04_BSTNode<K,V> root, FileWriter writer) {
		try {
		if(root == null) {
			return;
		}
		if(root.left != null) {
			writer.write("\t" + root.key + "->" + root.left.key + ";\n");
			debugAttachments(root.left, writer);
		}
		if(root.right != null) {
			writer.write("\t" + root.key + "->" + root.right.key + ";\n");
			debugAttachments(root.right, writer);
		}
		else {
			return;
		}
		}
		catch(IOException io) {
			System.err.println("IOException");
			io.printStackTrace();
		}

	}
	/**
	 * debugAllNodes
	 * this function returns all the nodes 
	 * @param root - root node 
	 * @param writer - the file writer to be written to 
	 */
	private void debugAllNodes(Proj04_BSTNode<K,V> root, FileWriter writer)  {
		if(root == null) {
			return;
		}
		try {
		writer.write("\t" + root.key + ";\n");
		}		
		catch(IOException io) {
			System.err.println("IOException");
			io.printStackTrace();
		}
		debugAllNodes(root.left, writer);
		debugAllNodes(root.right, writer);

	}
}
