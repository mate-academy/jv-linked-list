package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> header;
    private int size;

    public MyLinkedList() {
        header = new Node(null);
        header.next = header.prev = header;
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> entry = new Node<>(value, header.prev, header);
        entry.next.prev = entry;
        entry.prev.next = entry;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newEntry = getByIndex(index);
        newEntry = new Node<>(value, newEntry.prev, newEntry);
        newEntry.prev.next = newEntry;
        newEntry.next.prev = newEntry;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index == size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> entry = getByIndex(index);
        return entry.data;
    }

    @Override
    public T set(T value, int index) {
        if (index == size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> newEntry = getByIndex(index);
        T result = newEntry.data;
        newEntry.data = value;
        return result;
    }

    @Override
    public T remove(int index) {
        if (index == size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> entry = getByIndex(index);
        entry.prev.next = entry.next;
        entry.next.prev = entry.prev;
        size--;
        return entry.data;
    }

    @Override
    public boolean remove(T t) {
        int index = size;
        Node<T> entry = header.next;
        while (index != 0) {
            if ((t == null && entry.data == null) || (t != null && entry.data.equals(t))) {
                entry.prev.next = entry.next;
                entry.next.prev = entry.prev;
                entry = entry.next;
                size--;
                return true;
            }
            entry = entry.next;
            index--;
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

    private Node<T> getByIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> result = header;
        if (index < (size >> 1)) {
            for (int i = 0; i <= index; i++) {
                result = result.next;
            }
        } else {
            for (int i = size; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }

    private class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        public Node(T data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public Node(T data) {
            this.data = data;
        }
    }
}
