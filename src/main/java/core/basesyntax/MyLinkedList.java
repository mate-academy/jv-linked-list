package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        size++;
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
            return;
        }
        tail.next = new Node<>(tail, value, null);
        tail = tail.next;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
        Node<T> node = getNodeByIndex(index);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node<T> prevNode = node == null ? null : node.prev;
        node.prev = new Node<>(prevNode, value, node);
        size++;
        prevNode.next = node.prev;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T lastValue = node.value;
        node.value = value;
        return lastValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        if (size-- == 1) {
            tail = head = null;
            return node.value;
        }
        if (node.prev == null) {
            head.next.prev = null;
            head = head.next;
            return node.value;
        }
        if (node.next == null) {
            tail.prev.next = null;
            tail = tail.prev;
            return node.value;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        int index = 0;
        while (node != null) {
            if (node.value == object || node.value.equals(object)) {
                remove(index);
                return true;
            }
            node = node.next;
            index++;
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
