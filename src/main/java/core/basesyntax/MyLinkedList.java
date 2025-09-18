package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(this.tail, value, null);

        if (this.tail == null) {
            this.head = newNode;
        } else {
            this.tail.next = newNode;
        }

        this.tail = newNode;
        this.size++;
    }

    @Override
    public void add(T value, int index) {
        this.checkIndex(index, true);

        if (this.head == null || index == this.size) {
            this.add(value);
        } else {
            Node<T> currentNode = this.findNodeByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);

            if (currentNode.prev != null) {
                currentNode.prev.next = newNode;
            } else {
                this.head = newNode;
            }

            this.size++;
            currentNode.prev = newNode;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T e: list) {
            this.add(e);
        }
    }

    @Override
    public T get(int index) {
        this.checkIndex(index, false);

        Node<T> currentNode = this.findNodeByIndex(index);

        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        this.checkIndex(index, false);

        Node<T> currentNode = this.findNodeByIndex(index);

        T prevValue = currentNode.value;
        currentNode.value = value;

        return prevValue;
    }

    @Override
    public T remove(int index) {
        this.checkIndex(index, false);
        Node<T> currentNode = this.findNodeByIndex(index);
        T removedValue = currentNode.value;
        this.unlink(currentNode);

        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = this.head;

        while (currentNode != null) {
            if (object == null ? currentNode.value == null : object.equals(currentNode.value)) {
                this.unlink(currentNode);

                return true;
            }

            currentNode = currentNode.next;
        }

        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            this.head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            this.tail = node.prev;
        }

        node.next = null;
        node.prev = null;
        node.value = null;
        this.size--;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < this.size / 2) {
            Node<T> currentNode = this.head;

            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }

            return currentNode;
        } else {
            Node<T> currentNode = this.tail;

            for (int i = this.size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }

            return currentNode;
        }
    }

    private void checkIndex(int index, boolean allowedEnd) {
        if (allowedEnd) {
            if (index < 0 || index > this.size) {
                throw new IndexOutOfBoundsException("Index: " + index);
            }
        } else {
            if (index < 0 || index >= this.size) {
                throw new IndexOutOfBoundsException("Index: " + index);
            }
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
