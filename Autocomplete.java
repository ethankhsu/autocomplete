import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Autocomplete {
    // array holding terms
    private Term[] sorted;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null)
            throw new IllegalArgumentException("Argument cannot be null");
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null)
                throw new IllegalArgumentException("Null entry");
        }
        sorted = new Term[terms.length];
        // copying the terms array then sorting it
        for (int i = 0; i < terms.length; i++) {
            sorted[i] = terms[i];
        }
        Arrays.sort(sorted);
    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("prefix is null");
        }
        // use search to find first and last
        // Comparator<Term> comparator = Term.byReverseWeightOrder();
        Comparator<Term> comparator = Term.byPrefixOrder(prefix.length());
        Term key = new Term(prefix, 0);

        int first = BinarySearchDeluxe.firstIndexOf(sorted, key, comparator);
        int last = BinarySearchDeluxe.lastIndexOf(sorted, key, comparator);

        // for no match case
        if (first == -1 || last == -1) {
            Term[] output = new Term[0];
            return output;
        }

        Term[] output = new Term[last - first + 1];

        for (int i = 0; i < output.length; i++) {
            output[i] = sorted[first + i];
        }

        Comparator<Term> comparator1 = Term.byReverseWeightOrder();
        Arrays.sort(output, comparator1);

        return output;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("prefix is null");
        Comparator<Term> preCompare = Term.byPrefixOrder(prefix.length());
        Term k = new Term(prefix, 0);

        // int variable to find the first index of the prefix
        int first = BinarySearchDeluxe.firstIndexOf(sorted, k, preCompare);
        // int variable to find the last index of the prefix
        int end = BinarySearchDeluxe.lastIndexOf(sorted, k, preCompare);

        // for no match case
        if (first == -1 || end == -1)
            return 0;

        return end - first + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term a = new Term("hi", 1);
        Term b = new Term("hii", 2);
        Term c = new Term("hiii", 3);

        Term[] bob = { a, b, c };
        Term[] desc;

        Autocomplete test = new Autocomplete(bob);
        desc = test.allMatches("hi");

        StdOut.println(desc.length);
        StdOut.println();

        for (int i = 0; i < desc.length; i++) {
            StdOut.println(desc[i]);
        }
        StdOut.println();

        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);

            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }
}
