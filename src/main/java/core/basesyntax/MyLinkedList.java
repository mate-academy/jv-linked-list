package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newValue = new Node<>(last, value, null);
        if (isEmpty()) {
            last = newValue;
            first = newValue;
        } else {
            last.next = newValue;
            newValue.prev = last;
            last = newValue;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
        Node<T> newValue = new Node<>(null, value, null);
        if (isEmpty()) {
            last = newValue;
            first = newValue;
        } else if (index == 0) {
            newValue.next = first;
            first = newValue;
        } else if (index == size) {
            last.next = newValue;
            newValue.prev = last;
            last = newValue;
        } else {
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            newValue.prev = current.prev;
            current.prev.next = newValue;
            newValue.next = current;
            current.prev = newValue;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = findNodeByIndex(index);
        return current.nodeValue;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = findNodeByIndex(index);
        T removedValue = current.nodeValue;
        current.nodeValue = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = findNodeByIndex(index);
        unlink(current);
        size--;
        return current.nodeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (current.nodeValue == object || current.nodeValue != null
                    && current.nodeValue.equals(object)) {
                unlink(current);
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

    private class Node<T> {
        private T nodeValue;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T nodeValue, Node<T> next) {
            this.nodeValue = nodeValue;
            this.prev = prev;
            this.next = next;
        }
    }

    public void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
    }

    private void unlink(Node node) {
        if (node.prev == null) {
            first = first.next;
        } else if (node.next == null) {
            last = node.prev;
            node.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
}
