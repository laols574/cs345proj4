import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class  Proj04_AVL_student<K extends Comparable<K>, V>
implements Proj04_Dictionary<K,V> {
	
	private int count = 1;
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
		root = set_helper(root, key, value);
	}
	
	private Proj04_BSTNode<K, V> set_helper(Proj04_BSTNode<K, V> node, K key, V value){
		if(node == null) {
			return new Proj04_BSTNode<K, V>(key, value);
		}
		if (node.key.compareTo(key) ==  0) {
			node.value = value;
		}
		if(node.key.compareTo(key) < 0) {
			node.right = set_helper(node.right, key, value);
		}
		else if(node.key.compareTo(key) >  0 ) {
			node.left = set_helper(node.left, key, value);

		}
		AVLTreeUpdateHeight(node);
		AVLTreeUpdateCount(node);
		return rebalanceAVLTree(node, key);
	}

	
	private Proj04_BSTNode<K,V> rebalanceAVLTree(Proj04_BSTNode<K,V> node, K key) {
		int balance = AVLTreeGetBalance(node);
		//left left
		if( balance < -1 &&  key.compareTo(node.left.key) < 0) {
			return rotateRight(node);
		}
		//right right 
		else if(balance > 1 && key.compareTo(node.right.key) > 0){
			return rotateLeft(node);

		}
		//left right 
		else if(balance < -1 &&  key.compareTo(node.left.key) > 0){
			Proj04_BSTNode<K, V> temp = rotateRight(node);
			return rotateLeft(temp);
		}
		//right left  
		else if(balance > 1 && key.compareTo(node.right.key) < 0){
			Proj04_BSTNode<K, V>  temp = rotateLeft(node);
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

		return rightHeight - leftHeight;
	}
	/** rotateRight
	 * 
	 */
	private Proj04_BSTNode<K,V> rotateRight(Proj04_BSTNode<K,V> r) {
		Proj04_BSTNode<K,V> rNode = r.left;
		Proj04_BSTNode<K,V> rSubTree = rNode.right;
		
		r.left = rSubTree;
		rNode.right = r;
		
		AVLTreeUpdateHeight(r);
		AVLTreeUpdateCount(r);
		AVLTreeUpdateCount(rNode);
		AVLTreeUpdateHeight(rNode);

		return rNode;
	}
	/**
	 * 
	 * @param r
	 */
	private Proj04_BSTNode<K,V> rotateLeft(Proj04_BSTNode<K,V> r) {
		Proj04_BSTNode<K,V> lNode = r.right;
		Proj04_BSTNode<K,V> lSubTree = lNode.left;
		
		r.right = lSubTree;
		lNode.left = r;
		
		AVLTreeUpdateHeight(r);
		AVLTreeUpdateCount(r);
		AVLTreeUpdateHeight(lNode);
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
	
	private Proj04_BSTNode<K, V> remove_helper(Proj04_BSTNode<K, V> node, K key) { 
		//normal delete recursion 
		if(node == null)
			return null;
		if(node.key.compareTo(key) > 0) {
			node.left = remove_helper(node.left, key);
		}
		if (node.key.compareTo(key) < 0) {
			node.right = remove_helper(node.right, key);
		}
		//if the key equals 
		else if(node.key.compareTo(key) == 0){
			//case 1: node has one child or no children 
			if(node.left == null || node.right == null) {
				//selects the node that is not null if there is one 
				Proj04_BSTNode<K, V>  temp = null;
				if(node.left == null)
					temp = node.right;
				if(node.right ==  null)
					temp = node.left;
				//no children 
				if(temp == null) {
					temp = node;
					node = null;
				}
				//one child 
				else {
					node = temp;
				}
			}
			//case 2: two children 
			else {
				Proj04_BSTNode<K, V> predecessor = minValue(node.left);
				K tempKey = node.key;
				V tempVal = node.value;
				node.key = predecessor.key;
				node.value = predecessor.value;
				predecessor.key = tempKey;
				predecessor.value = tempVal;
				node.left = remove_helper(node.left, tempKey);
			}

		}
		
		//update the height of the current node
		if(node != null) {
			AVLTreeUpdateHeight(node);
			AVLTreeUpdateCount(node);
			return rebalanceAVLTree(node, key);
		}
		return node;
		//rebalance
		
	}
	/**
	 * finds the max value value of a 
	 * tree 
	 * @param root - the root node 
	 * @return - return the max value of a tree 
	 */
	private Proj04_BSTNode<K,V>  minValue(Proj04_BSTNode<K,V> root) {
        while (root.right != null) 
        { 
        	root = root.right; 
        } 
        return root; 
		
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
			String aux = "h=" + root.height;
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
			String aux = "h=" + root.height;
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
		
		File file = new File(treeType + "_" + count++ + ".dot");
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
