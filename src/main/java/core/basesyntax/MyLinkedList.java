package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "This index is out of bound ";
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE + index);
        }
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE + index);
        }
    }

    Node<T> getNode(int index) {
        if (index < (size >> 1)) {
            Node<T> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    void linkBefore(T e, Node<T> succ) {
        Node<T> pred = succ.prev;
        Node<T> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null) {
            head = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    void linkLast(T e) {
        final Node<T> l = tail;
        final Node<T> newNode = new Node<>(l, e, null);
        tail = newNode;
        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    T unlink(Node<T> x) {
        Node<T> next = x.next;
        Node<T> prev = x.prev;
        final T element = x.item;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        return element;
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T object : list) {
            linkLast(object);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> x = getNode(index);
        T oldVal = x.item;
        x.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = head; x != null; x = x.next) {
                if (object.equals(x.item)) {
                    unlink(x);
                    return true;
                }
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
