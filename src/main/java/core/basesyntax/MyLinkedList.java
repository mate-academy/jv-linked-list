package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, last, null); // new signature
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);

        if (index == size()) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(value, last, null); // new signature
        Node<T> current = getNode(index);

        if (index == 0) {
            first = newNode;
        } else {
            Node<T> previous = current.prev;
            previous.next = newNode;
            newNode.prev = previous;
        }
        newNode.next = current;
        current.prev = newNode;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size()) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index + ", Size: " + size());
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
        return getNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.data;
        node.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        unlink(nodeToRemove);
        return nodeToRemove.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (current.data == null ? object == null : current.data.equals(object)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return getSize();
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size()) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void unlink(Node<T> node) {
        Node<T> previous = node.prev;
        Node<T> next = node.next;

        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
        }

        if (next == null) {
            last = previous;
        } else {
            next.prev = previous;
        }
        size--;
    }

    private static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
}
