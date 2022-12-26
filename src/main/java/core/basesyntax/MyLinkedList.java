package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(value);

        if (size == 0) {
            head = newNode;
            tail = newNode;
            size++;
            return;
        }

        Node<T> prevTail = tail;
        newNode.prev = tail;
        tail = newNode;
        prevTail.next = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (size == 0) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            size++;
            return;
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index - 1; i++) {
            if (currentNode == null) {
                add(value);
                return;
            }
            currentNode = currentNode.next;
        }
        if (currentNode == null || currentNode.next == null) {
            add(value);
            return;
        }
        newNode.next = currentNode.next;
        newNode.prev = currentNode;
        currentNode.next = newNode;
        newNode.next.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            if (currentNode == null || currentNode.next == null) {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }
            currentNode = currentNode.next;
        }
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        Node<T> newValue = new Node<>(value);

        for (int i = 0; i < index; i++) {
            if (currentNode == null || currentNode.next == null) {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }
            currentNode = currentNode.next;
        }

        if (index == 0) {
            newValue.next = currentNode.next;
            if (currentNode.next != null) {
                currentNode.next.prev = newValue;
            }
            head = newValue;
            return currentNode.item;
        }

        currentNode.prev.next = newValue;
        newValue.prev = currentNode.prev;
        newValue.next = currentNode.next;
        if (currentNode.next != null) {
            currentNode.next.prev = newValue;
        }

        return currentNode.item;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            if (currentNode == null || currentNode.next == null) {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }
            currentNode = currentNode.next;
        }
        if (index == 0) {
            if (head.next == null) {
                T val = head.item;
                head = null;
                size--;
                return val;
            }
            head = head.next;
            head.prev = null;
            size--;
            return currentNode.item;
        }
        currentNode.prev.next = currentNode.next;
        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(object, currentNode.item)) {
                if (currentNode.prev != null) {
                    currentNode.prev.next = currentNode.next;
                } else {
                    head = currentNode.next;
                }
                if (currentNode.next != null) {
                    currentNode.next.prev = currentNode.prev;
                } else {
                    tail = tail.prev;
                }
                size--;
                return true;
            }
            currentNode = currentNode.next;
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

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
    }

    private static class Node<T> {
        private final T item;
        private Node<T> next;
        private Node<T> prev;

        Node(T element) {
            this.item = element;
        }
    }
}
