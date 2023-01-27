package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
            size++;
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        Node<T> currentNode = tail;
        tail = newNode;
        tail.prev = currentNode;
        currentNode.next = tail;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> node = getNode(index);
        addBefore(value, node);
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
        checkIndex(index);
        Node<T> node = getNode(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        unLink(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (object == null
                    ? node.item == null
                    : object.equals(node.item)) {
                unLink(node);
                return true;
            }
            node = node.next;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format(
                    "Can't operate with the value, index %s is out of bounds, size is %s",
                    index, size));
        }
    }

    private void addBefore(T value, Node<T> currentNode) {
        Node<T> newNode = new Node<>(null, value, null);
        if (currentNode == head) {
            newNode.next = currentNode;
            currentNode.prev = newNode;
            head = newNode;
            return;
        }
        Node<T> node = currentNode.prev;
        newNode.next = currentNode;
        newNode.prev = node;
        currentNode.prev = newNode;
        node.next = newNode;
    }

    private Node<T> getNode(int index) {
        Node<T> node;
        if (index >= size / 2) {
            node = tail;
            for (int i = size - 2; i >= index; i--) {
                node = node.prev;
            }
        }
        node = head;
        for (int i = 1; i <= index; i++) {
            node = node.next;
        }
        return node;
    }

    private void unLink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
