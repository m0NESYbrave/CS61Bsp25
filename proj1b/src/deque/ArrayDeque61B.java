package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int first, last; // tell where the next item should be put
    private final double R1 = 0.25; // items.length >= 16
    private final double R2 = 0.125; // items.length < 16

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        first = 0;
        last = 0;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            a[i] = get(i); // copy items in order into the new array.
        }
        last = size;
        first = capacity - 1;
        items = a;
    }

    @Override
    public void addFirst(T x) {
        items[first] = x;
        size++;
        first--;
        first = Math.floorMod(first + items.length, items.length); // first can be minus
        if (first == items.length - 1 && size == 1) { // addFirst happens first, must size == 1
            last = 1;
        }

        if (size == items.length) {
            resize(size * 2);
        }
    }

    @Override
    public void addLast(T x) {
        items[last] = x;
        size++;
        last++; // don't need to use floorMod() here, since when full will resize
        if (last == 1 && size == 1) { // addLast happens first
            first = items.length - 1;
        }

        if (size == items.length) {
            resize(size * 2);
        }
    }

    @Override
    public List<T> toList() {
        if (size == 0) { // see ad1
            return null;
        }
        List<T> returnList = new ArrayList<>();

        /** The commented code is complex, since at first get()'s implementation is wrong.
         *  I just simply return items[index] where 0 <= index < items.length.
         *  get() should be in sequence, from first to last. */

//        int temp = first;
//        temp++;
//        temp = Math.floorMod(temp, items.length); // first can be exactly items.length - 1, see ad2
//
//        // Temp is the position of the first item, so during the iteration every item is not null.
//        while (temp != last) { // iterate the array once
//            returnList.add(items[temp]);
//            temp++;
//            temp = Math.floorMod(temp, items.length); // temp can >= item.length, see ad3
//        }

        for (int i = 0; i < size; i++) {
            returnList.add(get(i));
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
        if (items.length >= 16 && size <= items.length * R1) {
            resize(items.length / 2);
        } else if (items.length < 16 && size <= items.length * R2) {
            resize(items.length / 2);
        }

        if (size == 0) {
            return null;
        }
        size--;
        first++;
        first = Math.floorMod(first, items.length); // first can >= items.length
        T returnItem = items[first];
        items[first] = null;
	return returnItem;
    }

    @Override
    public T removeLast() {
        if (items.length >= 16 && size <= items.length * R1) {
            resize(items.length / 2);
        } else if (items.length < 16 && size <= items.length * R2) {
            resize(items.length / 2);
        }

        if (size == 0) {
            return null;
        }
        size--;
        last--;
        last = Math.floorMod(last + items.length, items.length); // last decrements, can be minus
        T returnItem = items[last];
        items[last] = null;
	return returnItem;
    }

    @Override
    public T get(int index) { // in sequence!
        if (index < 0 || index >= size) { // 0 <= index < size
            return null;
        }
	index += first;
        index++;
        index = Math.floorMod(index, items.length);
        return items[index];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    /** An Iterable must have an Iterator. */
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        int wizPos;

        ArrayDequeIterator() {
            // wizPos is the position of the first item except empty array.
            wizPos = Math.floorMod(first + 1, items.length);
        }

        @Override
        public boolean hasNext() {
            return items[wizPos] != null;
        }

        @Override
        public T next() {
            T returnItem = items[wizPos];
            wizPos++;
            wizPos = Math.floorMod(wizPos, items.length);
            return returnItem;
        }
    }

    @Override
    /** Object method which checks the equality of contents. */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof ArrayDeque61B<?> otherDeque) {
            if (size() != otherDeque.size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                if (!get(i).equals(otherDeque.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return toList().toString(); // List itself does not have this method.
    }
}
