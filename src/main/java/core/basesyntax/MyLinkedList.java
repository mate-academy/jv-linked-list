package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

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
            linkBefore(value, findNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> locale = findNode(index);
        T oldValue = locale.item;
        locale.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> i = head; i != null; i = i.next) {
            if (i.item == object || object != null && object.equals(i.item)) {
                unlink(i);
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

    private void linkLast(T value) {
        final Node<T> valueLast = tail;
        final Node<T> locale = new Node<>(valueLast, value, null);
        tail = locale;
        if (valueLast == null) {
            head = locale;
        } else {
            valueLast.next = locale;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> nodeValue) {
        final Node<T> previous = nodeValue.prev;
        final Node<T> locale = new Node<>(previous, value, nodeValue);
        nodeValue.prev = locale;
        if (previous == null) {
            head = locale;
        } else {
            previous.next = locale;
        }
        size++;
    }

    private void checkElementIndex(int index) {
        if (isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of bound for length " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of bound for length " + size);
        }
    }

    private boolean isPositionIndex(int index) {
        return index < 0 || index > size;
    }

    private boolean isElementIndex(int index) {
        return index < 0 || index >= size;
    }

    private Node<T> findNode(int index) {
        if (index < (size >> 1)) {
            Node<T> locale = head;
            for (int i = 0; i < index; i++) {
                locale = locale.next;
            }
            return locale;
        } else {
            Node<T> locale = tail;
            for (int i = size - 1; i > index; i--) {
                locale = locale.prev;
            }
            return locale;
        }
    }

    private T unlink(Node<T> locale) {
        final T element = locale.item;
        final Node<T> next = locale.next;
        final Node<T> prev = locale.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return element;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
