import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key,
                                         Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        int location = -1;
        int lo = 0;
        int hi = a.length - 1;

        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;

            int compare = comparator.compare(key, a[mid]);
            if (compare < 0) {
                hi = mid - 1;
            }
            else if (compare > 0) {
                lo = mid + 1;
            }
            else {
                location = mid;
                hi = mid - 1;
            }
        }
        return location;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key,
                                        Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null)
            throw new IllegalArgumentException("Argument cannot be null");

        int index = -1;
        int low = 0;
        int high = a.length - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int compare = comparator.compare(key, a[mid]);
            if (compare > 0)
                low = mid + 1;
            else if (compare < 0)
                high = mid - 1;
            else {
                index = mid;
                low = mid + 1;
            }
        }
        return index;
    }

    // unit testing (required)
    public static void main(String[] args) {
        String[] a = { "A", "A", "C", "G", "G", "T" };
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        int index = BinarySearchDeluxe.firstIndexOf(a, "G", comparator);
        int index2 = BinarySearchDeluxe.lastIndexOf(a, "G", comparator);

        StdOut.println(index);
        StdOut.println(index2);
    }
}
