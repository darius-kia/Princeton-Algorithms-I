
/* *****************************************************************************
 *  Name: Darius Kianersi
 *  Date: 4/20/2020
 *  Description: Algorithms 1
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;   // pointer to first node
    private Node last;    // pointer to last node
    private int n;          // size

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
        public Node(Node previous, Item item, Node next) {
            this.item = item;
            this.next = next;
            this.prev = previous;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (n == 0);
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == 0) {
            first = new Node(null, item, null);
            last = new Node(null, item, null);
        } else if (n == 1) {
            first = new Node(null, item, last);
            last.prev = first;
        } else if (n == 2) {
            Node oldFirst = first;
            first = new Node(null, item, oldFirst);
            last.prev = oldFirst;
        } else {
            Node newFirst = new Node(null, item, first);
            first.prev = newFirst;
            first = newFirst;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == 0) {
            first = new Node(null, item, null);
            last = new Node(null, item, null);
        } else if (n == 1) {
            last = new Node(first, item, null);
            first.next = last;
        } else if (n == 2) {
            Node oldLast = last;
            last = new Node(oldLast, item, null);
            oldLast.next = last;
        } else {
            Node newLast = new Node(last, item, null);
            last.next = newLast;
            last = newLast;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item i = first.item;
        if (n == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        n--;
        return i;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item i = last.item;
        if (n == 1) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        n--;
        return i;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addLast(4);
        d.addLast(5);
        d.addFirst(6);
        d.addFirst(1);
        d.addLast(3);
        d.addFirst(2);
        d.addLast(10);

        int[] a = new int[10];
        a[0] = d.removeFirst();
        a[1] = d.removeLast();
        a[2] = d.removeFirst();
        a[3] = d.removeLast();
        a[4] = d.removeFirst();
        for (int i : d) {
            System.out.println(i);
        }
        System.out.println("Size: " + d.size());
        System.out.println(d.isEmpty());
        for (int i : a) {
            System.out.println("Removed: " + i);
        }


    }
}

