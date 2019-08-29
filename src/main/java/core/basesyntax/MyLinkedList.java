package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> l = tail;
        Node<T> node = new Node<T>(value, null, l);
        tail = node;
        if (l == null) {
            head = node;
        } else {
            l.next = node;
        }
        ++size;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            linkToEnd(value);
        } else {
            linkBefore(value, node(index));
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return node(index).item;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = node(index);
        node.item = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return unlink(node(index));
    }

    @Override
    public T remove(T t) {
        Node<T> node;
        T value = null;
        if (t == null) {
            for (node = head; node != null; node = node.next) {
                if (node.item == null) {
                    value = unlink(node);
                }
            }
        } else {
            for (node = head; node != null; node = node.next) {
                if (t.equals(node.item)) {
                    value = unlink(node);
                }
            }
        }
        return value;
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void linkToEnd(T t) {
        Node<T> l = tail;
        Node<T> node = new Node<T>(t, null, l);
        tail = node;
        if (l == null) {
            head = node;
        } else {
            l.next = node;
        }
        ++size;
    }

    private Node<T> node(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; ++i) {
            node = node.next;
        }
        return node;
    }

    private void linkBefore(T t, Node<T> src) {
        Node<T> pred = src.prev;
        Node<T> node = new Node<T>(t, src, pred);
        src.prev = node;
        if (pred == null) {
            head = node;
        } else {
            pred.next = node;
        }
        ++size;
    }

    private T unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        T element = node.item;
        node.item = null;
        --size;
        return element;
    }
}
