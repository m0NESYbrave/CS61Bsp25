package deque;
import java.util.Comparator;

public class Maximizer61B {
    /**
     * Returns the maximum element from the given iterable of comparables.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @return          the maximum element
     */
    public static <T extends Comparable<T>> T max(Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }
        T maxItem = null;
        int cnt = 0;
        for (T i : iterable) {
            if (cnt == 0) {
                maxItem = i; // maxItem is the first item, it can't be null.
            }
            if (i.compareTo(maxItem) > 0) { // T has this method
                maxItem = i;
            }
            cnt++;
        }
        return maxItem;
    }

    /**
     * Returns the maximum element from the given iterable according to the specified comparator.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @param comp      the Comparator to compare elements
     * @return          the maximum element according to the comparator
     */
    public static <T> T max(Iterable<T> iterable, Comparator<T> comp) {
        if (iterable == null) {
            return null;
        }

        T maxItem = null;
        int cnt = 0;
        for (T i : iterable) {
            if (cnt == 0) {
                maxItem = i;
            }
            if (comp.compare(i, maxItem) > 0) { // T does not have a compareTo() method
                maxItem = i;
            }
            cnt++;
        }
        return maxItem;
    }
}
