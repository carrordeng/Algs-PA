import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int size;

    // construct an empty deque
    public Deque() {
        this.first = new Node<>(null);
        this.last = new Node<>(null);
        first.next = last;
        last.pre = first;
        this.size = 0;
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("Illegal Argument");
        Node<Item> node = new Node<>(item);
        Node<Item> second = first.next;
        first.next = node;
        node.pre = first;
        node.next = second;
        second.pre = node;
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("Illegal Argument");
        Node<Item> node = new Node<>(item);
        Node<Item> second = last.pre;
        second.next = node;
        node.pre = second;
        node.next = last;
        last.pre = node;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0)
            throw new java.util.NoSuchElementException("No Such Element");
        Node<Item> second = first.next;
        Node<Item> third = first.next.next;
        first.next = third;
        third.pre = first;
        size--;
        return second.item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (size == 0)
            throw new java.util.NoSuchElementException("No Such Element");
        Node<Item> second = last.pre;
        Node<Item> third = last.pre.pre;
        last.pre = third;
        third.next = last;
        size--;
        return second.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class Node<Item> {
        private Item item;
        private Node<Item> pre;
        private Node<Item> next;

        private Node(Item item) {
            this.item = item;
            pre = null;
            next = null;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = first.next;

        @Override
        public boolean hasNext() {
            return current != last;
        }

        @Override
        public Item next() {
            if (last == current)
                throw new java.util.NoSuchElementException("No Such Element");
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException("Unsupported Operation");
        }
    }
}