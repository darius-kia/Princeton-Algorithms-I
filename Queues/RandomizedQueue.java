/* *****************************************************************************
 *  Name: Darius Kianersi
 *  Date: 4/20/2020
 *  Description: Algorithms 1
 **************************************************************************** */
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (n==0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == items.length) resize(items.length*2);
        items[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int ind = StdRandom.uniform(n); // generate a random index to remove
        Item ret = items[ind]; // save the item to return
        items[ind] = items[--n];
        items[n] = null;
        if (n > 0 && n == items.length/4) resize(items.length/2);
        return ret;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return items[StdRandom.uniform(n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        int current;
        Item[] r;
        public RandomizedIterator() {
            int current = 0;
            r = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                r[i] = items[i];
            }
            StdRandom.shuffle(r);
        }

        public boolean hasNext() {
            return current < n;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return r[current++];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> d = new RandomizedQueue<>();
        d.enqueue(3);
        d.enqueue(4);
        d.enqueue(5);
        System.out.println("dequeued: " + d.dequeue());
        System.out.println("dequeued: " + d.dequeue());
        d.enqueue(6);
        for (int i : d) {
            System.out.println(i);
        }
        System.out.println("SAMPLES");
        System.out.println(d.sample());
        System.out.println(d.sample());
        System.out.println(d.sample());
        System.out.println("Empty? " + d.isEmpty());
        System.out.println("Size: " + d.size());
    }

}