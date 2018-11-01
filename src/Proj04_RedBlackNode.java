
public class Proj04_RedBlackNode<K extends Comparable, V>{
	public  K  key;
	public  V  value;
	public int count;
	public int height;
	public String color;


	public Proj04_RedBlackNode<K,V> left;
	public Proj04_RedBlackNode<K,V> right;

	public Proj04_RedBlackNode(K key, V value){
		this.key   = key;
		this.value = value;

		this.count = 1;
		this.height = 0;
		this.color = "black";
		this.left = null;
		this.right = null;
	}
}
