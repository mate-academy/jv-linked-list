package core.basesyntax;

import java.util.List;
import java.util.LinkedList;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexBounds(index);
        if (index == size) {
            addLast(value);
        } else {
            addBefore(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        int startIndex = 0;
        int endIndex = size;
        if (index < 0 || index >= size) throw new RuntimeException("IndexBoundException");
        if (index < size / 2) {
            endIndex = size / 2;
        } else {
            startIndex = size / 2;
        }
        for (int i = startIndex; i < endIndex; i++) {

        }
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
        return size == 0;
    }

    private void addLast(T value) {
        Node<T> t = tail;
        Node<T> newNode = new Node<>(t, value, null);
        tail = newNode;
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }
        size++;
    }

    private void addBefore(T value, int index) {

    }
    private void checkIndexBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " not found.");
        }
    }
}
