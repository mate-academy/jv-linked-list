package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firstNode;
    private Node<T> lastNode;

    @Override
    public boolean add(T value) {
        if (isEmpty()) {
            Node<T> newNode = new Node<>(value, null, null);
            firstNode = newNode;
            lastNode = newNode;
        } else {
            Node<T> newNode = new Node<>(value, null, lastNode);
            lastNode.next = newNode;
            lastNode = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(value, firstNode, lastNode);
            firstNode.prev = newNode;
            firstNode = newNode;
            size++;
            return;
        }
        Node<T> nodeToBeShifted = findNodeByIndex(index);
        Node<T> newNode = new Node<>(value, nodeToBeShifted, nodeToBeShifted.prev);
        nodeToBeShifted.prev.next = newNode;
        nodeToBeShifted.prev = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeToBeChanged = findNodeByIndex(index);
        T oldValue = nodeToBeChanged.value;
        nodeToBeChanged.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        if (size == 1) {
            firstNode = null;
            lastNode = null;
            size--;
            return node.value;
        }
        if (index == 0) {
            node.next.prev = null;
            firstNode = node.next;
            size--;
            return node.value;
        }
        if (index == size - 1) {
            node.prev.next = null;
            lastNode = node.prev;
            size--;
            return node.value;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if (t == get(i) || t != null && findNodeByIndex(i).value.equals(t)) {
                remove(i);
                return true;
            }
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

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        int i = 0;
        Node<T> node = firstNode;
        while (i < index) {
            node = node.next;
            i++;
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index is out of bounds.");
        }
    }
}

