package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        negativeIndex(index);
        if (tail == null) {
            tail = head = new Node<>(null, value, null);
        } else if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            addTop(value, index);
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
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = getNodeByItem(object);
        if (removedNode == null) {
            return false;
        }
        unlink(removedNode);
        return true;
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
        return (a == null && b == null) || (a != null && a.equals(b));
    }

    private void checkIndexBounds(int index) {
        negativeIndex(index);
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void negativeIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is negative");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndexBounds(index);
        Node<T> current = tail;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private T unlink(Node<T> removedNode) {
        Node<T> next = removedNode.next;
        Node<T> prev = removedNode.prev;
        if (prev == null) {
            tail = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            head = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return removedNode.item;
    }

    private Node<T> getNodeByItem(T item) {
        Node<T> node = tail;
        while (node != null) {
            if (areEqual(node.item, item)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private void addTop(T value, int index) {
        Node<T> current = getNodeByIndex(index);
        current.prev.next = new Node<>(current.prev, value, current);
        current.prev = current.prev.next;
    }

    private void addFirst(T value) {
        tail.prev = new Node<>(null, value, tail);
        tail = tail.prev;
    }

    private void addLast(T value) {
        head.next = new Node<>(head, value, null);
        head = head.next;
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
