package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    Node<T> head;

    private class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public MyLinkedList() {
        this.size = 0;
        head = new Node<T>(null, (T) new Object(), null);
    }

    @Override
    public boolean add(T value) {
        Node<T> current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = new Node<>(current, value, null);
        size++;

        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of range, size of array is  "
                    + size);
        }
        Node<T> toAdd = new Node<T>(null, value, null);
        Node<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.prev.next = toAdd;
        current.next.prev = toAdd;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        Node<T> current = head;
        while (current.next != null) {
            current = current.next;
        }

        for (T t : list) {
            add(t);
        }

        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = head.next;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = head.next;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.item = value;

        return (T) current;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = head;

        for (int i = 0; i <= index; i++) {
            current = current.next;
        }
        current.prev.next = current.next;
        size--;

        return current.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> current = head;
        while (current != null) {
            if (current.item == t) {
                current.prev.next = current.next;
                size--;
                return true;
            }
            current = current.next;
        }

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
            throw new IndexOutOfBoundsException("Index is out of range, size of array is  "
                    + size);
        }
    }

//    private T passingArray(int index) {
//        checkIndex(index);
//
//        Node<T> current = head;
//
//        if (index < size / 2) {
//            for (int i = 0; i <= index; i++) {
//                current = current.next;
//            }
//        } else {
//            for (int i = size; i > 0; i--) {
//                current = current.next;
//            }
//        }
//        return (T) current;
//    }
}
