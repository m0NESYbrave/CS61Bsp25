import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private class Node {
        T item;
        Node prev;
        Node next;

        Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        size++;
        sentinel.next = new Node(x, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
    }

    @Override
    public void addLast(T x) {
        size++;
        sentinel.prev = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
    }

    @Override
    public List<T> toList() {
	List<T> returnList = new ArrayList<>();
        Node temp = sentinel;
        while (temp.next != sentinel) {
            temp = temp.next;
            returnList.add(temp.item);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
	return size == 0;
    }

    @Override
    public int size() {
	return size;
    }

    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        size--;
        T item = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
	return item;
    }

    @Override
    public T removeLast() {
        if (sentinel.next == sentinel) {
            return null;
        }
        size--;
        T item = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
	return item;
    }

    @Override
    public T get(int index) {
	if (index < 0 || index >= size) {
            return null;
        }

        Node temp = sentinel.next;
        int cnt = 0;
        while (temp != sentinel) {
            if (cnt == index) {
                return temp.item;
            }
            temp = temp.next;
            cnt++;
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
	return getRecursiveHelper(index, sentinel.next); // Node is recursive.
    }

    private T getRecursiveHelper(int index, Node temp) { // Have handled special cases.
        if (index == 0) {
            return temp.item;
        } else {
            return getRecursiveHelper(index - 1, temp.next);
        }
    }
}