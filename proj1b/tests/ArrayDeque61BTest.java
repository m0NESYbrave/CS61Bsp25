import deque.ArrayDeque61B;

import deque.Deque61B;
import jh61b.utils.Reflection;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }

    // TODO: My remove_first(last)_trigger_resize test's array size is less than 8
    // TODO: Enhancement, Maximizer and GH2 after lec11
    @Test
    /** This test tests addFirst with fixed size. */
    public void addFirstBasicTest() {
	Deque61B<Integer> ad1 = new ArrayDeque61B<>();

	ad1.addFirst(1); // add_first_from_empty
	ad1.addFirst(2); // add_first_nonempty
	ad1.addFirst(3);
	ad1.addFirst(4);

	assertThat(ad1.toList()).containsExactly(4, 3, 2, 1).inOrder();
    }

    @Test
    /** This test tests addLast with fixed size */
    public void addLastBasicTest() {
	Deque61B<Integer> ad1 = new ArrayDeque61B<>();

	ad1.addLast(1); // add_last_from_empty
	ad1.addLast(2); // add_last_nonempty
	ad1.addLast(3);
	ad1.addLast(4);

	assertThat(ad1.toList()).containsExactly(1, 2, 3, 4).inOrder();
    }

    @Test
    /** This test performs integrated addFirst and addLast with fixed size. */
    public void addFirstAndAddLastBasicTest() {
	Deque61B<Integer> ad1 = new ArrayDeque61B<>();

	ad1.addLast(1);
	ad1.addLast(2);
	ad1.addFirst(3);
	ad1.addLast(4);
	ad1.addFirst(5);
	ad1.addFirst(6);
	ad1.addLast(7);

	assertThat(ad1.toList()).containsExactly(6, 5, 3, 1, 2, 4, 7).inOrder();

	Deque61B<Integer> ad2 = new ArrayDeque61B<>();

	ad2.addLast(1);
	ad2.addLast(2);
	ad2.addFirst(3);
	ad2.addLast(4);

	assertThat(ad2.toList()).containsExactly(3, 1, 2, 4).inOrder();
    }

    @Test
    /** This test tests get with two special cases and some normal cases. */
    public void getTest() {
	Deque61B<Integer> ad1 = new ArrayDeque61B<>();

	assertThat(ad1.get(0)).isEqualTo(null);   // get_oob_large
	assertThat(ad1.get(100)).isEqualTo(null); // get_oob_large
	assertThat(ad1.get(-1)).isEqualTo(null);  // get_oob_neg

	ad1.addLast(1);
	ad1.addLast(2);
	ad1.addFirst(3);
	ad1.addLast(4);
	ad1.addFirst(5);
	ad1.addFirst(6);
	ad1.addLast(7);

	assertThat(ad1.get(0)).isEqualTo(6);
	assertThat(ad1.get(5)).isEqualTo(4);
	assertThat(ad1.get(3)).isEqualTo(1);
	assertThat(ad1.get(4)).isEqualTo(2);
	assertThat(ad1.get(8)).isEqualTo(null);
    }

    @Test
    /** This test tests isEmpty and size with empty and non-empty arrays. */
    public void isEmptyAndSizeTest() {
	Deque61B<Integer> ad1 = new ArrayDeque61B<>();

	assertThat(ad1.isEmpty()).isTrue(); // is_empty_true
	assertThat(ad1.size()).isEqualTo(0);

	ad1.addLast(1);
	ad1.addLast(2);
	ad1.addFirst(3);
	ad1.addLast(4);
	ad1.addFirst(5);
	ad1.addFirst(6);
	ad1.addLast(7);

	assertThat(ad1.isEmpty()).isFalse(); // is_empty_false
	assertThat(ad1.size()).isEqualTo(7); // size
    }

    @Test
    /** This test tests toList with more general case. I find error when removing. */
    public void toListTest() {
	Deque61B<Integer> ad1 = new ArrayDeque61B<>();

	assertThat(ad1.toList()).isEqualTo(null); // to_list_empty

	ad1.addFirst(1);
	ad1.addFirst(2);
	ad1.addFirst(3);
	ad1.addLast(4);
	ad1.removeFirst();
	ad1.removeFirst();
	ad1.removeFirst();

	assertThat(ad1.toList()).containsExactly(4).inOrder(); // to_list_nonempty

	Deque61B<Integer> ad2 = new ArrayDeque61B<>();

	ad2.addLast(1);
	ad2.addLast(2);

	assertThat(ad2.toList()).containsExactly(1, 2).inOrder();

	Deque61B<Integer> ad3 = new ArrayDeque61B<>();

	ad3.addFirst(1);
	ad3.addFirst(2);

	assertThat(ad3.toList()).containsExactly(2, 1).inOrder();
    }

    @Test
    /** This test tests removeFirst with empty and non-empty arrays with fixed size. */
    public void removeFirstTest() {
	Deque61B<Integer> ad1 = new ArrayDeque61B<>();

	assertThat(ad1.removeFirst()).isEqualTo(null);
	assertThat(ad1.size()).isEqualTo(0); // size_after_remove_from_empty

	ad1.addFirst(1);
	ad1.addFirst(2);
	ad1.addFirst(3);
	ad1.addLast(4);

	assertThat(ad1.toList()).containsExactly(3, 2, 1, 4).inOrder();
	assertThat(ad1.removeFirst()).isEqualTo(3); // remove_first
	assertThat(ad1.removeFirst()).isEqualTo(2);
	assertThat(ad1.removeFirst()).isEqualTo(1); // remove_first_to_one
	assertThat(ad1.toList()).containsExactly(4).inOrder();
	assertThat(ad1.removeFirst()).isEqualTo(4); // remove_first_to_empty
	assertThat(ad1.size()).isEqualTo(0); // size_after_remove_to_empty

	Deque61B<Integer> ad2 = new ArrayDeque61B<>();

	ad2.addLast(1);

	assertThat(ad2.removeFirst()).isEqualTo(1);
	assertThat(ad2.isEmpty()).isTrue();
	assertThat(ad2.size()).isEqualTo(0);

	Deque61B<Integer> ad3 = new ArrayDeque61B<>();

	ad3.addLast(1);
	ad3.addLast(2);
	ad3.addFirst(3);
	ad3.addLast(4);
	ad3.addFirst(5);
	ad3.addFirst(6);
	ad3.addLast(7);

	assertThat(ad3.removeFirst()).isEqualTo(6);
	assertThat(ad3.removeFirst()).isEqualTo(5);
	assertThat(ad3.removeFirst()).isEqualTo(3);
	assertThat(ad3.removeFirst()).isEqualTo(1);
	assertThat(ad3.removeFirst()).isEqualTo(2);
	assertThat(ad3.removeFirst()).isEqualTo(4);
	assertThat(ad3.toList()).containsExactly(7).inOrder();
    }

    @Test
    /** This test tests removeLast with empty and non-empty arrays with fixed size. */
    public void removeLastTest() {
	Deque61B<Integer> ad1 = new ArrayDeque61B<>();

	assertThat(ad1.removeLast()).isEqualTo(null);

	ad1.addLast(1);
	ad1.addLast(2);
	ad1.addLast(3);
	ad1.addFirst(4);

	assertThat(ad1.toList()).containsExactly(4, 1, 2, 3).inOrder();
	assertThat(ad1.removeLast()).isEqualTo(3); // remove_last
	assertThat(ad1.removeLast()).isEqualTo(2);
	assertThat(ad1.removeLast()).isEqualTo(1); // remove_last_to_one
	assertThat(ad1.toList()).containsExactly(4).inOrder();
	assertThat(ad1.removeLast()).isEqualTo(4); // remove_last_to_empty
	assertThat(ad1.toList()).isEqualTo(null);

	Deque61B<Integer> ad2 = new ArrayDeque61B<>();

	ad2.addFirst(1);

	assertThat(ad2.removeLast()).isEqualTo(1);
	assertThat(ad2.isEmpty()).isTrue();
	assertThat(ad2.size()).isEqualTo(0);

	Deque61B<Integer> ad3 = new ArrayDeque61B<>();

	ad3.addLast(1);
	ad3.addLast(2);
	ad3.addFirst(3);
	ad3.addLast(4);
	ad3.addFirst(5);
	ad3.addFirst(6);
	ad3.addLast(7);

	assertThat(ad3.removeLast()).isEqualTo(7);
	assertThat(ad3.removeLast()).isEqualTo(4);
	assertThat(ad3.removeLast()).isEqualTo(2);
	assertThat(ad3.removeLast()).isEqualTo(1);
	assertThat(ad3.removeLast()).isEqualTo(3);
	assertThat(ad3.removeLast()).isEqualTo(5);
	assertThat(ad3.toList()).containsExactly(6).inOrder();
    }

    @Test
    /** This test tests integrated removeFirst and removeLast with fixed size. */
    public void removeFirstAndRemoveLastTest() {
	Deque61B<Integer> ad1 = new ArrayDeque61B<>();

	ad1.addLast(1);
	ad1.addLast(2);
	ad1.addFirst(3);
	ad1.addLast(4);
	ad1.addFirst(5);
	ad1.addFirst(6);
	ad1.addLast(7);

	assertThat(ad1.removeLast()).isEqualTo(7);
	assertThat(ad1.toList()).containsExactly(6, 5, 3, 1, 2, 4);
	assertThat(ad1.removeFirst()).isEqualTo(6);
	assertThat(ad1.toList()).containsExactly(5, 3, 1, 2, 4);
    }

    @Test
    /** Comprehensive test. I find error in addFirst and addLast's special case. */
    public void allTest() {
	Deque61B<Integer> ad1 = new ArrayDeque61B<>();

	ad1.addLast(1);
	ad1.addLast(2);
	ad1.addLast(3);
	ad1.addLast(4);
	ad1.addLast(5);
	ad1.removeFirst();
	ad1.addFirst(0);
	ad1.addLast(10);

	assertThat(ad1.get(5)).isEqualTo(10);
	assertThat(ad1.get(6)).isEqualTo(null);

	Deque61B<Integer> ad2 = new ArrayDeque61B<>();

	ad2.addFirst(1);
	ad2.addFirst(2);
	ad2.addFirst(3);
	ad2.addFirst(4);
	ad2.addFirst(5);
	ad2.removeLast();
	ad2.addLast(10);
	ad2.addFirst(66);

	assertThat(ad2.toList()).containsExactly(66, 5, 4, 3, 2, 10);
	assertThat(ad2.get(4)).isEqualTo(2);
	assertThat(ad2.get(5)).isEqualTo(10);
    }

    @Test
    /** This test tests add operation when resized. */
    public void addFirstAndAddLastResizeTest() {
	Deque61B<Integer> ad1 = new ArrayDeque61B<>(); // all addFirst

	ad1.addFirst(0);
	ad1.addFirst(1);
	ad1.addFirst(2);
	ad1.addFirst(3);
	ad1.addFirst(4);
	ad1.addFirst(5);
	ad1.addFirst(6);
	ad1.addFirst(7); // resizeUp, add_first_trigger_resize

	assertThat(ad1.size()).isEqualTo(8);
	assertThat(ad1.toList()).containsExactly(7, 6, 5, 4, 3, 2, 1, 0).inOrder();

	Deque61B<Integer> ad2 = new ArrayDeque61B<>(); // all addLast

	ad2.addLast(0);
	ad2.addLast(1);
	ad2.addLast(2);
	ad2.addLast(3);
	ad2.addLast(4);
	ad2.addLast(5);
	ad2.addLast(6);
	ad2.addLast(7); // resizeUp, add_last_trigger_resize

	assertThat(ad2.size()).isEqualTo(8);
	assertThat(ad2.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7).inOrder();

	Deque61B<Integer> ad3 = new ArrayDeque61B<>(); // integrated

	ad3.addLast(1);
	ad3.addLast(2);
	ad3.addFirst(3);
	ad3.addLast(4);
	ad3.addFirst(5);
	ad3.addFirst(6);
	ad3.addLast(7);
	ad3.addFirst(8); // resizeUp

	assertThat(ad3.size()).isEqualTo(8);
	assertThat(ad3.toList()).containsExactly(8, 6, 5, 3, 1, 2, 4, 7).inOrder();

	ad3.addFirst(10);
	ad3.addFirst(11);
	ad3.addLast(12);
	ad3.addFirst(13);
	ad3.addLast(14);
	ad3.addLast(15);
	ad3.addFirst(16);
	ad3.addLast(17); // resizeUp
	ad3.addLast(18);

	assertThat(ad3.size()).isEqualTo(17);
	assertThat(ad3.toList()).containsExactly(16, 13, 11, 10,
						8, 6, 5, 3, 1, 2, 4, 7,
						12, 14, 15, 17, 18);
    }

    @Test
    /** This test tests removeFirst when resized */
    public void removeFirstResizeTest() {
	Deque61B<Integer> ad1 = new ArrayDeque61B<>(); // begin with 0 item then remove

	ad1.removeFirst(); // items.length should 8 -> 4
	ad1.addFirst(0);
	ad1.addFirst(1);
	ad1.addLast(2);
	ad1.addLast(3); // resizeUp

	assertThat(ad1.toList()).containsExactly(1, 0, 2, 3);

	Deque61B<Integer> ad2 = new ArrayDeque61B<>(); // begin with 1 item then remove

	ad2.addFirst(0);
	ad2.removeFirst(); // items.length 8 -> 4
	ad2.addFirst(0);
	ad2.removeFirst(); // 4 since 0.25 > R = 0.125
	ad2.addLast(0);
	ad2.addFirst(1);
	ad2.addFirst(2);
	ad2.addLast(3);

	assertThat(ad2.toList()).containsExactly(2, 1, 0, 3);

	Deque61B<Integer> ad3 = new ArrayDeque61B<>(); // begin with 7 items then remove

	ad3.addLast(1);
	ad3.addLast(2);
	ad3.addFirst(3);
	ad3.addLast(4);
	ad3.addFirst(5);
	ad3.addFirst(6);
	ad3.addLast(7);
	ad3.removeFirst();
	ad3.removeFirst();
	ad3.removeFirst();
	ad3.removeFirst();
	ad3.removeFirst();
	ad3.removeFirst(); // 1 item now

	assertThat(ad3.size()).isEqualTo(1);
	assertThat(ad3.toList()).containsExactly(7);

	ad3.removeLast(); // begin with 1 item

	assertThat(ad3.size()).isEqualTo(0);

	/** ad4 triggers a resizeUp and then a resizeDown: resize_up_and_resize_down */
	Deque61B<Integer> ad4 = new ArrayDeque61B<>(); // begin with 8 items then remove

	ad4.addLast(1);
	ad4.addLast(2);
	ad4.addFirst(3);
	ad4.addLast(4);
	ad4.addFirst(5);
	ad4.addFirst(6);
	ad4.addLast(7);
	ad4.addFirst(8); // resizeUp
	ad4.removeFirst();
	ad4.removeFirst();
	ad4.removeFirst();
	ad4.removeFirst(); // 4 items now

	assertThat(ad4.size()).isEqualTo(4);
	assertThat(ad4.toList()).containsExactly(1, 2, 4, 7).inOrder();

	ad4.removeFirst(); // begin with 4 items and R = 0.25, resizeDown

	assertThat(ad4.toList()).containsExactly(2, 4, 7).inOrder();
    }

    @Test
    /** This test tests removeLast when resized */
    public void removeLastResizeTest() {
	Deque61B<Integer> ad1 = new ArrayDeque61B<>(); // begin with 0 item then remove

	ad1.removeLast(); // items.length 8 -> 4
	ad1.addFirst(0);
	ad1.addFirst(1);
	ad1.addLast(2);
	ad1.addLast(3); // resizeUp

	assertThat(ad1.toList()).containsExactly(1, 0, 2, 3);

	Deque61B<Integer> ad2 = new ArrayDeque61B<>(); // begin with 1 item then remove

	ad2.addLast(0);
	ad2.removeLast(); // items.length 8 -> 4
	ad2.addLast(0);
	ad2.removeLast(); // 4
	ad2.addLast(0);
	ad2.addFirst(1);
	ad2.addFirst(2);
	ad2.addLast(3);

	assertThat(ad2.toList()).containsExactly(2, 1, 0, 3);

	Deque61B<Integer> ad3 = new ArrayDeque61B<>(); // begin with 7 item then remove

	ad3.addLast(1);
	ad3.addLast(2);
	ad3.addFirst(3);
	ad3.addLast(4);
	ad3.addFirst(5);
	ad3.addFirst(6);
	ad3.addLast(7);
	ad3.removeLast();
	ad3.removeLast();
	ad3.removeLast();
	ad3.removeLast();
	ad3.removeLast();
	ad3.removeLast(); // 1 item now

	assertThat(ad3.size()).isEqualTo(1);
	assertThat(ad3.toList()).containsExactly(6);

	ad3.removeLast(); // begin with 1 item, resizeDown

	assertThat(ad3.size()).isEqualTo(0);

	Deque61B<Integer> ad4 = new ArrayDeque61B<>(); // begin with 8 items then remove

	ad4.addLast(1);
	ad4.addLast(2);
	ad4.addFirst(3);
	ad4.addLast(4);
	ad4.addFirst(5);
	ad4.addFirst(6);
	ad4.addLast(7);
	ad4.addFirst(8); // resizeUp
	ad4.removeLast();
	ad4.removeLast();
	ad4.removeLast();
	ad4.removeLast(); // 4 items now

	assertThat(ad4.size()).isEqualTo(4);
	assertThat(ad4.toList()).containsExactly(8, 6, 5, 3).inOrder();

	ad4.removeLast(); // begin with 4 items and R = 0.25, resizeDown

	assertThat(ad4.toList()).containsExactly(8, 6, 5).inOrder();
    }

    @Test
    /** This test tests integrated remove operation when resized. */
    public void removeFirstAndRemoveLastResizeTest() {
	Deque61B<Integer> ad1 = new ArrayDeque61B<>();

	ad1.addLast(0);
	ad1.addFirst(1);
	ad1.addLast(2);
	ad1.addFirst(3);
	ad1.addFirst(4);
	ad1.removeLast();
	ad1.removeLast();
	ad1.removeFirst();
	ad1.removeLast(); // 1 item now

	assertThat(ad1.size()).isEqualTo(1);
	assertThat(ad1.toList()).containsExactly(3).inOrder();

	ad1.removeFirst(); // begin with 1 item, resizeDown

	assertThat(ad1.size()).isEqualTo(0);
    }
}
