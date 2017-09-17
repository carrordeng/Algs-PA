import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] data;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.size = 0;
        data = (Item[]) new Object[1];
    }

    // unit testing (optional)
    public static void main(String[] args) {
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("Illegal Argument");
        if (size == data.length) {
            Item[] temp = (Item[]) new Object[data.length * 2];
            for (int i = 0; i < size; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }
        data[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0)
            throw new java.util.NoSuchElementException("No Such Element");
        int i = StdRandom.uniform(size);
        Item item = data[i];
        if (i != size - 1)
            data[i] = data[size - 1];
        data[size - 1] = null;
        size--;
//        if(size<=data.length/4){
//            Item[] temp=(Item[]) new Object[data.length/2];
//            for(int j=0;j<size;j++){
//                temp[j]=data[j];
//            }
//            data=temp;
//        }
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (size == 0)
            throw new java.util.NoSuchElementException("No Such Element");
        int i = StdRandom.uniform(size);
        return data[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current;
        private int[] sequence;

        public RandomizedQueueIterator() {
            current = 0;
            sequence = new int[size];
            for (int i = 0; i < size; i++) {
                sequence[i] = i;
            }
            StdRandom.shuffle(sequence);
        }

        @Override
        public boolean hasNext() {
            return current < size - 1;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                current++;
                return data[sequence[current]];
            }
            throw new java.util.NoSuchElementException("No Such Element");
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException("Unsupported Operation");
        }
    }
}