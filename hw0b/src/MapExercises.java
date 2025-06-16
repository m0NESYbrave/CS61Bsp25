import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        // TODO: Fill in this function.
        Map<Character, Integer> treeMap = new TreeMap<>();
        int index = 1;
        for (char i = 'a'; i <= 'z'; i++) {
            treeMap.put(i, index++);
        }
        return treeMap;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        // TODO: Fill in this function.
        Map<Integer, Integer> treeMap = new TreeMap<>();
        for (int i : nums) {
            treeMap.put(i, i * i);
        }
        return treeMap;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        // TODO: Fill in this function.
        Map<String, Integer> treeMap = new TreeMap<>();
        int cnt = 0;
        for (String i : words) {
            for (String j : words) {
                if (i.equals(j)) {
                    cnt++;
                }
            }
            /* A map cannot contain duplicate keys.
               If we try to add a key already in the map, the value is overwritten.
            */
            treeMap.put(i, cnt);
            cnt = 0;
        }
        return treeMap;
    }
}
