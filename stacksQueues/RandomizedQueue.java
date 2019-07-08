package stacksQueuesProject;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] a;

    // construct an empty randomized queue
    public RandomizedQueue(){

        size = 0;
        a = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty(){

        return (size == 0);

    }

    // return the number of items on the randomized queue
    public int size(){
        return size;

    }

    // add the item
    public void enqueue(Item item){

        if (item == null) throw new IllegalArgumentException();

        if (size == a.length) resize(2*a.length);    // double size of array if necessary
        a[size++] = item;                            // add item
    }

    // resize the underlying array holding the elements
    private void resize(int capacity){

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            if(a[i] != null) temp[i] = a[i];
        }
        a = temp;

    }

    // remove and return a random item
    public Item dequeue(){

        checkNotEmpty();

        int randomIndex = StdRandom.uniform(size);
        Item removed = a[randomIndex];
        a[randomIndex] = a[size-1];
        a[size-1] = null;
        size--;
        if (size > 0 && size == a.length/4) resize(a.length/2);
        return removed;

    }

    // return a random item (but do not remove it)
    public Item sample(){

        checkNotEmpty();

        return a[StdRandom.uniform(size)];
    }

    private void checkNotEmpty(){
        if(this.isEmpty()) throw new NoSuchElementException("There's nothing to remove!");
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomIterator();

    }

    private class RandomIterator implements Iterator<Item>{

        private int current = size;
        private Item[] copy;

        public RandomIterator(){

            copy = (Item[]) new Object[size];

            for(int i = 0; i < size; i++){
                copy[i] = a[i];
            }

        }

        public boolean hasNext(){
            return(current > 0);

        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next(){

            if (!hasNext()) throw new NoSuchElementException();

            int randomNum = StdRandom.uniform(0, current);
            Item chosen = copy[randomNum];
            copy[randomNum] = copy[--current];
            copy[current] = chosen;

            return chosen;

        }



    }

    public static void main(String[] args){

        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();

        for(int i = 0; i < 5 ; i++){
            randomizedQueue.enqueue(i);
        }

        for(int i: randomizedQueue) {
            StdOut.print(i + " ");


                      }

        }

    }   // unit testing (optional)


