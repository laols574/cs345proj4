
public class  Proj04_RedBlack_student<K extends Comparable<K>, V>
implements Proj04_Dictionary<K,V>{
	
	private String treeType;
	public Proj04_RedBlack_student(String treeType) {
		this.treeType = treeType;
	}
	
	private Proj04_234Node root;
	public Proj04_RedBlack_student(String treeType, Proj04_234Node root) {
		this.treeType = treeType;
		this.root = root;
	}
	@Override
	public void set(K key, V value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void remove(K key) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
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
	private int inOrderHelper(Proj04_234Node<K,V> root, int index, K[] keysOut, V[] valuesOut, int[] countsOut) {
		if(root == null) {
			return index;
		}
		index = inOrderHelper(root.left, index, keysOut, valuesOut, countsOut);
		if(keysOut != null)
			keysOut[index] = root.key;
		if(valuesOut != null)
			valuesOut[index] = root.value;
		if(countsOut != null)
			countsOut[index] = root.count;
		index++;
		index = inOrderHelper(root.right, index, keysOut, valuesOut, countsOut);
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
	private int postOrderHelper(Proj04_234Node<K,V> root, int index, K[] keysOut, V[] valuesOut, int[] countsOut) {
		if(root == null) {
			return index;
		}
		index = postOrderHelper(root.left, index, keysOut, valuesOut, countsOut);
		index = postOrderHelper(root.right, index, keysOut, valuesOut, countsOut);
		if(keysOut != null)
			keysOut[index] = root.key;
		if(valuesOut != null)
			valuesOut[index] = root.value;
		if(countsOut != null)
			countsOut[index] = root.count;
		index++;
		return index;
	}
	@Override
	public void genDebugDot() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		return null;
	}
}
