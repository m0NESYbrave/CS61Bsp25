import deque.Maximizer61B;
import deque.ArrayDeque61B;

import org.junit.jupiter.api.*;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class Maximizer61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    private static class SizeComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    }

    @Test
    /** We use a cnt to avoid maxItem be null. */
    public void basicTest() {
        ArrayDeque61B<String> ad = new ArrayDeque61B<>();

        /* max_empty */
        assertThat(Maximizer61B.max(ad)).isEqualTo(null);
        assertThat(Maximizer61B.max(ad, new StringLengthComparator())).isEqualTo(null);

        /* max_nonempty max_default max_different_comp */
        ad.addFirst("");
        ad.addFirst("2");
        ad.addFirst("fury road");
        assertThat(Maximizer61B.max(ad, new StringLengthComparator())).isEqualTo("fury road");
        assertThat(Maximizer61B.max(ad)).isEqualTo("fury road");

        ArrayDeque61B<Integer> ad1 = new ArrayDeque61B<>();

        ad1.addLast(1);
        ad1.addLast(2);
        ad1.addFirst(3);

        assertThat(Maximizer61B.max(ad1)).isEqualTo(3);
        assertThat(Maximizer61B.max(ad1, new SizeComparator())).isEqualTo(3);
    }
}
