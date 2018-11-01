import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/*
 *there are two cases: 
 *red red occurs at a leaf and there can be a simple rotation 
 *then do rebalance and have getbalance sent appropriate values
 *if not, you need to recolor the parent 
 *if a child's node is red and the grandchild is red
 *and then turn the node red and the child black 
 *if the node is the root turn it black
 *red red occurs internally and it needs to be pushed up 
 */
public class  Proj04_RedBlack_student<K extends Comparable<K>, V>
implements Proj04_Dictionary<K,V>{
	
	private Proj04_RedBlackNode<K,V> root;
	private String treeType;
	private int count = 0;
	/**
	 * constructor 1 
	 * @param treeType
	 */
	public Proj04_RedBlack_student(String treeType) {
		this.treeType = treeType;
		this.root = null;
	}
	/**
	 * constructor 2
	 * @param treeType
	 * @param root
	 */
	public Proj04_RedBlack_student(String treeType, Proj04_234Node<K,V> root) {
		this.treeType = treeType;
		this.root = buildFrom234(root);
		
	}
	
	private Proj04_RedBlackNode<K,V> buildFrom234(Proj04_234Node<K,V> node) {
		if(node == null) {
			return null;
		}else {System.out.print(node.key1);}
		Proj04_RedBlackNode<K,V> child1 = buildFrom234(node.child1);
		Proj04_RedBlackNode<K,V> child2 = buildFrom234(node.child2);
		Proj04_RedBlackNode<K,V> child3 = buildFrom234(node.child3);
		Proj04_RedBlackNode<K,V> child4 = buildFrom234(node.child4);
		
		if(node.numKeys == 1) {
			Proj04_RedBlackNode<K,V> newNode = new Proj04_RedBlackNode<K,V>(node.key1, node.val1);
			newNode.color = "black";
			newNode.left = child1;
			newNode.right = child2;
			RedBlackTreeUpdateHeight(newNode);
			RedBlackTreeUpdateCount(newNode);
			return newNode;
			
		}
		else if(node.numKeys == 2) {
			Proj04_RedBlackNode<K,V> node1 = new Proj04_RedBlackNode<K,V>(node.key1, node.val1);
			Proj04_RedBlackNode<K,V> node2 = new Proj04_RedBlackNode<K,V>(node.key2, node.val2);
			node1.color = "black";
			node2.color = "red";

			node1.left = node2;
			
			node2.left = child1;
			node2.right = child2;
			
			node1.right = child3;
			 
			RedBlackTreeUpdateHeight(node2);
			RedBlackTreeUpdateCount(node2);
			
			RedBlackTreeUpdateCount(node1);
			RedBlackTreeUpdateHeight(node1);

			return node1;

		}
		Proj04_RedBlackNode<K,V> node1 = new Proj04_RedBlackNode<K,V>(node.key2, node.val2);
		Proj04_RedBlackNode<K,V> node2 = new Proj04_RedBlackNode<K,V>(node.key1, node.val1);
		Proj04_RedBlackNode<K,V> node3 = new Proj04_RedBlackNode<K,V>(node.key3, node.val3);
		node1.color = "black";
		node2.color = "red";
		node3.color = "red";

		node1.left = node2;
		node1.right = node3;
			
		node2.left = child1;
		node2.right = child2;
			
		node3.left = child3;
		node3.right = child4;

		RedBlackTreeUpdateCount(node2);
		RedBlackTreeUpdateHeight(node2);

		RedBlackTreeUpdateCount(node3);
		RedBlackTreeUpdateHeight(node3);
			
		RedBlackTreeUpdateCount(node1);
		RedBlackTreeUpdateHeight(node1);
			
		return node1;

			
	}
	/**
	 * insertion function
	 * 
	 * @param K key
	 * @param V value 
	 * 
	 */
	@Override
	public void set(K key, V value) {
		root = set_helperRB(root, key, value);
		root.color = "black";
	}
	
	
	private Proj04_RedBlackNode<K,V> set_helperRB(Proj04_RedBlackNode<K,V> node, K key, V value) {
		if(node == null) {
			Proj04_RedBlackNode<K,V> newNode =  new Proj04_RedBlackNode<K,V>(key, value);
			newNode.color = "red";
			return newNode;
		}
		if(node.key.compareTo(key) == 0) {
			node.value = value;
			return node;
		}
		if(node.color == "black" && (node.left != null && node.right != null) && node.left.color == "red" && node.right.color == "red"){
			node.color = "red";
			node.left.color = "black";
			node.right.color = "black";
		}
		if(node.key.compareTo(key) < 0) {
			node.right = set_helperRB(node.right, key, value);
		}
		else if(node.key.compareTo(key) >  0) {
			node.left = set_helperRB(node.left, key, value);
		}
		RedBlackTreeUpdateCount(node);
		RedBlackTreeUpdateHeight(node);
		return rebalanceRedBlackTree(node, key);
				
	}
	private void RedBlackTreeUpdateCount(Proj04_RedBlackNode<K,V> node) {
		node.count = (1 + getCount(node.left) + getCount(node.right));
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
	private int getCount(Proj04_RedBlackNode<K,V> node)
	{
		if (node == null)
			return 0;
		return node.count;
	}
	private Proj04_RedBlackNode<K,V> rebalanceRedBlackTree(Proj04_RedBlackNode<K,V> node, K key) {
		//if it's a leaf
		if(node.color == "red")
			return node;
		if((node.left != null && node.right != null) && node.left.color == "black" && node.right.color == "black")
			return node;
		if((node.left != null && node.right != null) && node.left.color == "red" && node.right.color == "red")
			return node;
		//left left
		if(node.left != null && node.left.color == "red") {
			if(node.left.left != null && node.left.left.color == "red") {
				node.left.color = "black";
				node.color  = "red";
				return rotateRight(node);
			}
		}
		//right right 
		else if(node.right != null &&  node.right.color == "red"){
			if(node.right.right != null && node.right.right.color == "red") {
				node.right.color = "black";
				node.color  = "red";
				return rotateLeft(node);
			}
		}
		//left right 
		else if(node.left != null && node.left.color == "red"){
			if(node.left.right.color != null && node.left.right.color == "red") {
				node.left.right.color = "black";
				node.color  = "red";
				Proj04_RedBlackNode<K, V> temp = rotateRight(node);
				return rotateLeft(temp);
			}
		}
		//right left  
		else if(node.right != null && node.right.color == "red"){
			if(node.right.left != null &&  node.right.left.color == "red") {
				node.right.left.color = "black";
				node.color  = "red";
				Proj04_RedBlackNode<K, V>  temp = rotateLeft(node);
				return rotateRight(temp);
			}
		}
		return node;
		
	}

	/** rotateRight
	 * 
	 */
	private Proj04_RedBlackNode<K,V> rotateRight(Proj04_RedBlackNode<K,V> r) {
		Proj04_RedBlackNode<K,V> rNode = r.left;
		Proj04_RedBlackNode<K,V> rSubTree = rNode.right;
		
		rNode.right = r;
		r.left = rSubTree;
		
		RedBlackTreeUpdateHeight(r);
		RedBlackTreeUpdateCount(r);
		RedBlackTreeUpdateHeight(rNode);
		RedBlackTreeUpdateCount(rNode);
	
		return rNode;
	}
	/**
	 * 
	 * @param r
	 */
	private Proj04_RedBlackNode<K,V> rotateLeft(Proj04_RedBlackNode<K,V> r) {
		Proj04_RedBlackNode<K,V> lNode = r.right;
		Proj04_RedBlackNode<K,V> lSubTree = lNode.left;
		
		lNode.left = r;
		r.right = lSubTree;
		
		RedBlackTreeUpdateHeight(r);
		RedBlackTreeUpdateCount(r);
		RedBlackTreeUpdateHeight(lNode);
		RedBlackTreeUpdateCount(lNode);
	
		return lNode;
	}

	/** RedBlackTreeUpdateHeight
	 * this function updates the height value of the given node
	 */
	private void RedBlackTreeUpdateHeight(Proj04_RedBlackNode<K,V> node) {
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
		Proj04_RedBlackNode<K, V> node = RedBlackSearch(root, key);
		if(node == null)
			return null;
		return node.value;
	}
	
	private Proj04_RedBlackNode<K, V> RedBlackSearch(Proj04_RedBlackNode<K,V> root, K key){
		if(root == null || root.key.equals(key)) {
			return root;
		}
		if(root.key.compareTo(key) < 0){
			 return RedBlackSearch(root.right, key);
		}
		else{
			 return RedBlackSearch(root.left, key);
		}
	}
	
	@Override
	public void remove(K key) {
		
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
	private int inOrderHelper(Proj04_RedBlackNode<K,V> root, int index, K[] keysOut, V[] valuesOut, String[] auxOut) {
		if(root == null) {
			return index;
		}
		index = inOrderHelper(root.left, index, keysOut, valuesOut, auxOut);
		if(keysOut != null)
			keysOut[index] = root.key;
		if(valuesOut != null)
			valuesOut[index] = root.value;
		if(auxOut != null) {
			String aux =  root.color.substring(0, 1);
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
	private int postOrderHelper(Proj04_RedBlackNode<K,V> root, int index, K[] keysOut, V[] valuesOut, String[] auxOut) {
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
			String aux = root.color.substring(0, 1);
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
	private void debugAttachments(Proj04_RedBlackNode<K,V> root, FileWriter writer) {
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
	private void debugAllNodes(Proj04_RedBlackNode<K,V> root, FileWriter writer)  {
		if(root == null) {
			return;
		}
		try {
		writer.write("\t" + root.key + "[fillcolor = " + root.color + "]" + ";\n");
		}		
		catch(IOException io) {
			System.err.println("IOException");
			io.printStackTrace();
		}
		debugAllNodes(root.left, writer);
		debugAllNodes(root.right, writer);

	}
}