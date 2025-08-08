package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            last = new Node<>(null, value, null);
            first = last;
        } else {
            last.next = new Node<>(last, value, null);
            last = last.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index passed is invalid, index: "
                    + index + ", list size: " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> successor = getNode(index);
        Node<T> predecessor = successor.prev;
        Node<T> newNode = new Node<>(predecessor, value, successor);
        successor.prev = newNode;
        if (predecessor == null) {
            first = newNode;
        } else {
            predecessor.next = newNode;
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
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        deleteNode(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        while (node != null && !areEqual(node.item, object)) {
            node = node.next;
        }
        if (node != null) {
            deleteNode(node);
        }
        return node != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index passed is invalid, index: "
                    + index + ", list size: " + size);
        }
        Node<T> node;
        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void deleteNode(Node<T> node) {
        final Node<T> prev = node.prev;
        final Node<T> next = node.next;
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

    private boolean areEqual(T element1, T element2) {
        return (element1 == element2) || ((element1 != null) && element1.equals(element2));
    }

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }
}
