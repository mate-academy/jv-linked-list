package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> newNode = new Node<>(value);

        if (index == size) {
            add(value);
            return;
        }

        Node<T> currentNode = getNodeByIndex(index);
        newNode.prev = currentNode.prev;
        newNode.next = currentNode;

        if (currentNode.prev != null) {
            currentNode.prev.next = newNode;
        } else {
            head = newNode;
        }

        if (currentNode.next != null) {
            currentNode.next.prev = newNode;
        } else {
            tail = newNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> toDelete = getNodeByIndex(index);

        if (toDelete.prev != null) {
            toDelete.prev.next = toDelete.next;
        } else {
            head = toDelete.next;
        }

        if (toDelete.next != null) {
            toDelete.next.prev = toDelete.prev;
        } else {
            tail = toDelete.prev;
        }

        size--;

        return toDelete.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> toDelete = getNode(object);
        if (toDelete == null) {
            return false;
        }

        if (toDelete.prev != null) {
            toDelete.prev.next = toDelete.next;
        } else {
            head = toDelete.next;
        }

        if (toDelete.next != null) {
            toDelete.next.prev = toDelete.prev;
        } else {
            tail = toDelete.prev;
        }
        size--;

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

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> current = head;

        for (int i = 1; i <= index; i++) {
            current = current.next;
        }

        return current;
    }

    private Node<T> getNode(T value) {
        if (size == 0) {
            return null;
        }

        Node<T> current = head;

        while (current != null
                && value != current.value
                && (value == null || !value.equals(current.value))) {
            current = current.next;
        }

        return current;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }
}
