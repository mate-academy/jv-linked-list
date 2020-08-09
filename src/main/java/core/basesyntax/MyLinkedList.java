package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    int size;
    Node<T> first;
    Node<T> last;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public boolean add(T value) {
        linkLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, nodeAt(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        Object[] arr = list.toArray();
        if (arr.length == 0) {
            return false;
        }
        for (T i:list) {
            linkLast(i);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return nodeAt(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = nodeAt(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = nodeAt(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T t) {
        for (Node<T> x = first; x != null; x = x.next) {
            if (x.item == t || x.item != null && x.item.equals(t)) {
                unlink(x);
                return true;
            }
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

    public void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void linkLast(T value) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    public void linkBefore(T e, Node<T> x) {
        final Node<T> prev = x.prev;
        final Node<T> newNode = new Node<>(prev, e, x);
        x.prev = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    public void unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
        size--;
    }

    Node<T> nodeAt(int index) {
        indexCheck(index);
        if (index < (size >> 1)) {
            Node<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }
}
