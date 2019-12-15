package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    transient int size = 0;
    transient Node first;
    transient Node last;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public void error(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node node = new Node(null, value, null);
            first = node;
            last = node;
        } else if (size > 0) {
            Node node = new Node(last, value, null);
            last.next = node;
            last = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        error(index);
        if (index == size) {
            add(value);
        } else {
            Node node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            Node newNode = new Node(node.prev, value, node);
            node.prev = newNode;
            newNode.prev.next = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return (T) node.item;
    }

    @Override
    public void set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        node.item = value;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        if (size == 1) {
            first = last = null;
            size--;
            return (T) node;
        }
        T removed = (T) get(index);
        if (node == last) {
            node.prev.next = null;
            last = node.prev;
        } else if (node == first) {
            node.next.prev = null;
            first = node.next;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return (T) removed;
    }

    @Override
    public T remove(T t) {
        Node node = first;
        for (int i = 0; i < size; i++) {
            if (t == node.item || t.equals(node.item)) {
                return remove(i);
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
