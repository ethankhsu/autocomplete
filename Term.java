import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Term implements Comparable<Term> {

    private String query; // query of term
    private long weight; // weight of term

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null || weight < 0)
            throw new IllegalArgumentException("Invalid Inputs");

        this.query = query;
        this.weight = weight;
    }

    private static class CompareDesc implements Comparator<Term> {
        // descending order by weight
        public int compare(Term a, Term b) {
            return Double.compare(b.weight, a.weight);
        }
    }

    private static class CompareR implements Comparator<Term> {
        // first r in lexicographical order
        private int prefix;

        // initialize value 'r' passed in to instance variable 'prefix'
        public CompareR(int r) {
            prefix = r;
        }

        public int compare(Term a, Term b) {
            int lenOne = a.query.length();
            int lenTwo = b.query.length();

            String query1 = a.query;
            String query2 = b.query;

            int i = 0;
            while (i < lenOne && i < lenTwo && i < prefix) {
                if (query1.charAt(i) < query2.charAt(i)) {
                    return -1;
                }
                else if (query1.charAt(i) > query2.charAt(i)) {
                    return 1;
                }
                i++;
            }

            if (i == prefix) {
                return 0;
            }

            if (lenOne == lenTwo) {
                return 0;
            }
            else if (lenTwo < lenOne) {
                return 1;
            }
            return -1;
        }
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new CompareDesc();
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0)
            throw new IllegalArgumentException("r is negative");
        return new CompareR(r);
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return (weight + "\t" + query);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term test1 = new Term("dog", 7);
        Term test2 = new Term("doggy", 9);
        Term test3 = new Term("l", 2);
        Term test4 = new Term("c", 1);

        StdOut.println(test1.compareTo(test3));

        Comparator<Term> comparator1 = byPrefixOrder(5);

        Comparator<Term> comparator2 = byReverseWeightOrder();

        StdOut.println(comparator1.compare(test2, test1));

        StdOut.println(comparator2.compare(test2, test1));

        StdOut.println(test3.compareTo(test4));

        /* strings diff lengths, but first r characters same */
        Term p = new Term("CAAACAAT", 6);
        Term q = new Term("CAAACTC", 9);
        Comparator<Term> comparator = byPrefixOrder(5);
        StdOut.println(comparator.compare(p, q));
    }

}
