package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> next = takeElementByIndex(index);
        Node<T> prev = next.prev;
        Node<T> newNode = new Node<>(prev, value, next);
        next.prev = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
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
        return takeElementByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> element = takeElementByIndex(index);
        T valueToReturn = element.item;
        element.item = value;
        return valueToReturn;
    }

    @Override
    public T remove(int index) {
        Node<T> element = takeElementByIndex(index);
        return unLink(element).item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int index = 0; index < size; index++) {
            if (object == current.item || object != null && object.equals(current.item)) {
                unLink(current);
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
        return (size == 0);
    }

    private Node<T> takeElementByIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of size " + size);
        }
        Node<T> current;
        if (index <= (size >> 1)) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private Node<T> unLink(Node<T> node) {
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;
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
        return node;
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item) {
            this.item = item;
        }

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
