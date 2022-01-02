package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    private class Node {
        T value;
        Node prev;
        Node next;
        Node(T value) {
            this.value = value;
        }
    }
    @Override
    public void add(T value) {
        Node node = new Node(value);
        if (tail == null) {
            tail = node;
            head = tail;
            return;
        }
        tail.next = node;
        node.prev = tail;
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkOutOfBounds(index, true);
        if (head == null || index == size) {
            add(value);
            return;
        }
        Node node = new Node()
        if (index == 0) {

        }
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
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private Node getNode(int index) {

    }

    private void checkOutOfBounds(int index, boolean add) {
        int upperBound = size - (add ? 0 : 1);
        if ((index < 0) || (index >= upperBound) {
            throw new IndexOutOfBoundsException();
        }
    }
}
