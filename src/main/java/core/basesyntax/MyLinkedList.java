package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    Node<T> firstNode;
    Node<T> lastNode;

    @Override
    public void add(T value) {
        if (firstNode == null) {
            Node<T> newNode = new Node<>(null, value, null);
            firstNode = newNode;
            lastNode = newNode;
        } else {
            lastNode.next = new Node<>(lastNode, value, null);
            lastNode = lastNode.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, firstNode);
            if (firstNode != null) {
                firstNode.prev = newNode;
            }
            firstNode = newNode;
            if (lastNode == null) {
                lastNode = newNode;
            }
        } else {
            Node<T> current = firstNode;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            Node<T> nextNode = current.next;
            Node<T> newNode = new Node<>(current, value, nextNode);
            current.next = newNode;
            if (nextNode != null) {
                nextNode.prev = newNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = firstNode;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;

    }

    @Override
    public T set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = firstNode;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) {
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

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
