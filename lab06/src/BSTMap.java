import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
	K key;
	V value;
	BSTNode left;
	BSTNode right;

	BSTNode(K key, V value) {
	    this.key = key;
	    this.value = value;
	    left = null;
	    right = null;
	}
    }

    private BSTNode root;
    private int size;

    public BSTMap() {
	root = null;
	size = 0;
    }

    @Override
    public void put(K key, V value) {
	/* We reassign every link along the path. */
	root = put(key, value, root);
    }

    private BSTNode put(K key, V value, BSTNode node) {
	if (node == null) {
	    node = new BSTNode(key, value);
	    size++;
	    return node;
	}
	if (key.compareTo(node.key) > 0) {
	    node.right = put(key, value, node.right);
	} else if (key.compareTo(node.key) < 0) {
	    node.left = put(key, value, node.left);
	} else {
	    node.value = value;
	}
	return node;
    }

    @Override
    public V get(K key) {
//	BSTNode temp = root;
//	while (temp != null) {
//	    if (key.compareTo(temp.key) > 0) {
//		temp = temp.right;
//	    } else if (key.compareTo(temp.key) < 0) {
//		temp = temp.left;
//	    } else {
//		return temp.value;
//	    }
//	}
//	return null;
	return get(key, root);
    }

    private V get(K key, BSTNode node) {
	if (node == null) {
	    return null;
	}
	if (key.compareTo(node.key) > 0) {
	    /*  If not return, it will go to return node.value.
	    * 	This could update our desired result.
	    * */
	    return get(key, node.right);
	} else if (key.compareTo(node.key) < 0) {
	    return get(key, node.left);
	}
	return node.value; // find the key
    }

    @Override
    public boolean containsKey(K key) {
	/* Iterative way */
	BSTNode temp = root;
	while (temp != null) {
	    if (key.compareTo(temp.key) > 0) {
		temp = temp.right;
	    } else if (key.compareTo(temp.key) < 0) {
		temp = temp.left;
	    } else {
		return true;
	    }
	}
	return false; // temp is null, no key found
    }

    @Override
    public int size() {
	return size;
    }

    @Override
    public void clear() {
	root = null;
	size = 0;
    }

    @Override
    public Set<K> keySet() {
	Set<K> set = new TreeSet<>();
	// Obviously need to iterate.
	for (K i : this) {
	    set.add(i);
	}
	return set;
    }

    @Override
    public V remove(K key) {
	if (!containsKey(key)) {
	    return null;
	}
	V deletedValue = get(key);
	root = remove(key, root);
	size--;
	return deletedValue;
    }

    private BSTNode remove(K key, BSTNode currNode) {
	if (currNode == null) {
	    return null;
	}
	if (key.compareTo(currNode.key) > 0) {
	    currNode.right = remove(key, currNode.right);
	} else if (key.compareTo(currNode.key) < 0) {
	    currNode.left = remove(key, currNode.left);
	} else { // find the key
//	    if (currNode.left == null && currNode.right == null) { // no children
//		currNode = null;
//		return currNode;
//	    } else if (currNode.left == null && currNode.right != null) { // one right child
//		return currNode.right;
//	    } else if (currNode.left != null && currNode.right == null) { // one left child
//		return currNode.left;
//	    }
	    /* Can be simplified. */
	    if (currNode.left == null) {
		return currNode.right;
	    } else if (currNode.right == null) {
		return currNode.left;
	    } else { // two children
		BSTNode prec = predecessor(currNode.left); // go to the left subtree to get predecessor
		prec.right = currNode.right;
		currNode = prec;
	    }
	}
	return currNode;
    }

    /* The biggest in the left subtree. */
    private BSTNode predecessor(BSTNode prec) {
	if (prec.right != null) {
	    prec = predecessor(prec.right);
	}
	return prec;
    }

    @Override
    public Iterator<K> iterator() {
	return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
	/*  Have no idea, others use stack.
	*   LIFO so the latest node is on the top.
	* */
	Stack<BSTNode> BSTStack;

	BSTMapIterator() {
	    BSTStack = new Stack<>();
	    pushLeft(root);
	}

	/* Go to the node with the min key.
	*  And push all the nodes along the way.
	* */
	void pushLeft(BSTNode node) {
	    while (node != null) {
		BSTStack.push(node);
		node = node.left;
	    }
	}

	@Override
	public boolean hasNext() {
	    return !BSTStack.isEmpty();
	}

	@Override
	public K next() {
	    BSTNode currNode = BSTStack.pop();
	    pushLeft(currNode.right);
	    return currNode.key;
	}
    }

    public void printInOrder() {
	for (K i : this) {
	    System.out.println("Key: " + i + " Value: " + this.get(i));
	}
    }

    public static void main(String[] args) {
	BSTMap<String, Integer> map1 = new BSTMap<>();
	map1.put("idk", 9);
	map1.put("g", 7);
	map1.put("f", 6);
	map1.put("h", 8);
	map1.put("e", 5);
	map1.put("c", 3);
	map1.put("d", 4);
	map1.put("a", 1);
	Set<String> set = map1.keySet();
	map1.printInOrder();

	BSTMap<String, Integer> map2 = new BSTMap<>();
	map2.put("D", 4);
	map2.put("B", 2);
	map2.put("A", 1);
	map2.put("C", 3);
	map2.put("F", 6);
	map2.put("E1", 5);
	map2.put("E2", 5);
	map2.put("G", 7);
	map2.remove("A");
    }
}
