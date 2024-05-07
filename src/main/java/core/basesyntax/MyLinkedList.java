package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> currentNode = findNodeByIndex(index);
            newNode.prev = currentNode.prev;
            newNode.next = currentNode;
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item: list) {
            add(item);
        }
    }
    @Override
    public T get(int index) {
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = findNodeByIndex(index);
        unlink(currentNode);
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = findNodeByValue(object);
        if (currentNode != null) {
            unlink(currentNode);
            return true;
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

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode;
    }

    private Node<T> findNodeByValue(T value) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.item == value || value != null
                    && value.equals(currentNode.item)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode == null) {
            return;
        }

        Node<T> prev = currentNode.prev;
        Node<T> next = currentNode.next;

        head = (prev == null) ? next : head;
        tail = (next == null) ? prev : tail;

        if (prev != null) {
            prev.next = next;
            currentNode.prev = null;
        }

        if (next != null) {
            next.prev = prev;
            currentNode.next = null;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
