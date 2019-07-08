package stacksQueuesProject;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item>  implements Iterable<Item>  {

    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque(){

        first = null;
        last = null;
        size = 0;

    }

    private class Node{
        Item item;
        Node next;
        Node previous;
    }

    // is the deque empty?
    public boolean isEmpty(){
    return (first == null);
    }

    // return the number of items on the deque
    public int size(){
    return size;
    }

    // add the item to the front
    public void addFirst(Item item) {

            checkNullAddition(item);
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            first.previous = null;

            if (oldFirst != null)
                oldFirst.previous = first;
            else
                last = first;
            size++;

    }
    // add the item to the end
    public void addLast(Item item) {

            checkNullAddition(item);
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            last.previous = oldLast;

            if (oldLast != null)
                oldLast.next = last;
            else
                first = last;
            size++;

    }

    private void checkNullAddition(Item item){
        if (item == null) throw new IllegalArgumentException();

    }

    // remove and return the item from the front
    public Item removeFirst(){

            checkNotEmpty();
           Item item = first.item;
           first = first.next;
           if(first != null) first.previous = null;
           else last = null;
           size--;
           return item;

        }

    // remove and return the item from the end
    public Item removeLast(){

        checkNotEmpty();
        Item item = last.item;
        Node oldLast = last;
        last = oldLast.previous;
        if(last != null) last.next = null;
        else first = null;
        size--;
        return item;

    }

    private void checkNotEmpty(){
        if(this.isEmpty()) throw new NoSuchElementException("There's nothing to remove!");
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator(){
        return new dequeIterator();
    }

    private class dequeIterator implements Iterator<Item>{

        private Node current = first;

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    // unit testing (optional)
    public static void main(String[] args){

    }
}
