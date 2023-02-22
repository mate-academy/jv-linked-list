package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> prev;
    private Node<T> next;
    private int size;

    public MyLinkedList() {
        prev = null;
        next = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (prev == null) {
            prev = next = new Node<>(null, value, null);
        } else if (index == 0) {
            prev.prev = new Node<>(null, value, prev);
            prev = prev.prev;
        } else if (index == size) {
            next.next = new Node<>(next, value, null);
            next = next.next;
        } else {
            Node<T> current = prev;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            current.prev.next = new Node<>(current.prev, value, current);
            current.prev = current.prev.next;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        isIndexOutOfBoundsException(index);
        Node<T> current = prev;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        isIndexOutOfBoundsException(index);
        Node<T> current = prev;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isIndexOutOfBoundsException(index);
        T value;
        if (prev == null) {
            throw new RuntimeException("List is empty");
        } else if (index == 0) {
            value = prev.item;
            prev = prev.next;
            if (prev != null) {
                prev.prev = null;
            } else {
                next = null;
            }
        } else if (index == size - 1) {
            value = next.item;
            next = next.prev;
            next.next = null;
        } else {
            Node<T> current = prev;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            value = current.item;
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return value;
    }

    @Override
    public boolean remove(T node) {
        Node<T> current = prev;
        while (current != null) {
            if (areEqual(node, current.item)) {
                if (current.prev == null) {
                    prev = current.next;
                } else {
                    current.prev.next = current.next;
                }
                if (current.next == null) {
                    next = current.prev;
                } else {
                    current.next.prev = current.prev;
                }
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

    private boolean areEqual(T a, T b) {
        return (a == null && b == null) || (a != null && b != null && a.hashCode() == b.hashCode());
    }

    private void isIndexOutOfBoundsException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}