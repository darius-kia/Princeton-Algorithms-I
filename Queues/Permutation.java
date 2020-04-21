/* *****************************************************************************
 *  Name: Darius Kianersi
 *  Date: 4/21/2020
 *  Description: Algorithms 1
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> r = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String term = StdIn.readString();
            r.enqueue(term);
        }
        int i = 0;
        for (String s : r) {
            if (i < k) {
                System.out.println(s);
                i++;
            } else break;
        }
    }
}
