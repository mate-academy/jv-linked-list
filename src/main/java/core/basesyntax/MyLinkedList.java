package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    Node node ;
    Node head;
    Node tail;

    @Override
    public void add(T value) {
        if(size == 0) {
            node = new Node(value);
            node.prev = null;
            node.next = null;
            head = node;
            size++;
        } else if (size == 1){
            node = new Node(value);
            node.next = null;
            node.prev = head;
            node = tail;
            size++;
        } else {
            node = new Node(value);
            node.next = null;
            node.prev = tail;
            tail = node;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {

    }

    @Override
    public void addAll(List<T> list) {

    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) throws NoSuchElementException {
        throw new NoSuchElementException("Element " + object + " was not found");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public void check(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }
}

class Node<T> {
    private T item;
    Node prev;
    Node next;

    public Node(T item) {
        this.item = item;
    }
}
