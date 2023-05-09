package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
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
        if (index == size) {
            add(value);
        } else {
            final Node<T> previous = getNodeByIndex(index).prev;
            final Node<T> newNode = new Node<>(previous, value, getNodeByIndex(index));
            getNodeByIndex(index).prev = newNode;
            if (previous == null) {
                head = newNode;
            } else {
                previous.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
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
        T valueNode = currentNode.value;
        currentNode.value = value;
        return valueNode;
    }

    @Override
    public T remove(int index) {
        Node<T> remoteNode = getNodeByIndex(index);
        unlink(remoteNode);
        return remoteNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> remoteNode = head;
        while (remoteNode != null) {
            if (remoteNode.value == object
                    || remoteNode.value != null
                    && remoteNode.value.equals(object)) {
                unlink(remoteNode);
                return true;
            }
            remoteNode = remoteNode.next;
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

    public Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not correct: "
                            + index + ", for Size: " + size);
        }
        if (index < (size >> 1)) {
            Node<T> head = this.head;
            for (int i = 0; i < index; i++) {
                head = head.next;
            }
            return head;
        } else {
            Node<T> tail = this.tail;
            for (int i = size - 1; i > index; i--) {
                tail = tail.prev;
            }
            return tail;
        }
    }

    public void unlink(Node node) {
        if (node == head) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
