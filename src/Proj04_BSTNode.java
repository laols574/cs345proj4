/**
 * Proj04_BSTNode
 * this class represents a single node in a binary search tree
 * @author Lauren Olson, laols574
 *
 * @param <K> - key 
 * @param <V> - value 
 */

public class Proj04_BSTNode<K extends Comparable, V>{
	public  K  key;
	public  V  value;
	public int count;
	public int height;


	public Proj04_BSTNode<K,V> left;
	public Proj04_BSTNode<K,V> right;

	public Proj04_BSTNode(K key, V value){
		this.key   = key;
		this.value = value;

		this.count = 1;
		this.height = 0;
		this.left = null;
		this.right = null;
	}
}

