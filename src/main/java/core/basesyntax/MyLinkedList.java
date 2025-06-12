package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexExclusive(index);

        if (index == size) {
            add(value);
            return;
        }

        Node<T> current = nodeByIndex(index);
        Node<T> previous = current.prev;

        Node<T> newNode = new Node<>(previous, value, current);

        current.prev = newNode;
        if (previous != null) {
            previous.next = newNode;
        } else {
            head = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("Input list is null");
        }
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndexIclusive(index);
        return nodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexIclusive(index);
        Node<T> node = nodeByIndex(index);
        T old = node.item;
        node.item = value;
        return old;
    }

    @Override
    public T remove(int index) {
        checkIndexIclusive(index);
        Node<T> current = nodeByIndex(index);
        return unlink(current);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        while (current != null) {
            if (Objects.equals(current.item, object)) {
                unlink(current);
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

    private void checkIndexIclusive(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private void checkIndexExclusive(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private T unlink(Node<T> node) {
        if (node == null) {
            throw new NullPointerException("Node is null");
        }

        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }

        if (next != null) {
            next.prev = prev;
        } else {
            tail = prev;
        }

        size--;
        return node.item;
    }

    private class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> nodeByIndex(int index) {

        Node<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current;
    }
}
