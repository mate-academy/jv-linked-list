package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T data, Node<T> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    public MyLinkedList() { }

    @Override
    public void add(T value) {
        Node<T> current = tail;
        Node<T> newNode = new Node<>(current, value, null);
        tail = newNode;

        if (current == null) {
            head = newNode;
        } else {
            current.next = newNode;

        }
        size++;
    }

    @Override
    public void add(T value, int index) {
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            this.add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index == 0) {
            return head.data;
        }

        checkIndex(index);
        Node<T> result = head;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }

        return result.data;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
