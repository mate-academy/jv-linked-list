package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    public MyLinkedList() {
        firstNode = null;
        lastNode = null;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            firstNode = newNode;
            lastNode = newNode;
        } else {
            newNode.prev = lastNode;
            lastNode.next = newNode;
            lastNode = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            newNode.next = firstNode;
            firstNode.prev = newNode;
            firstNode = newNode;
            size++;
        } else {
            addNodeInside(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        Node<T> result = getNodebyIndex(index);
        return result.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> result = getNodebyIndex(index);
        T oldVal = result.value;
        result.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (size == 0) {
            throw new RuntimeException("Cannot remove from an empty list.");
        }
        return unlink(index).value;
    }

    @Override
    public boolean remove(T object) {
        int i = 0;
        for (Node<T> x = firstNode; x != null; x = x.next) {
            if ((x.value == object || (x.value != null && x.value.equals(object)))) {
                unlink(i);
                return true;
            }
            i++;
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
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<T> getNodebyIndex(int index) {
        checkIndex(index);
        Node<T> node = firstNode;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private Node<T> unlink(int index) {
        Node<T> result = getNodebyIndex(index);
        if (result == firstNode) {
            firstNode = result.next;
        } else if (result == lastNode) {
            lastNode = result.prev;
        } else {
            result.prev.next = result.next;
            result.next.prev = result.prev;
        }
        size--;
        return result;
    }

    private void addNodeInside(T value, int index) {
        Node<T> oldNode = getNodebyIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        newNode.prev = oldNode.prev;
        newNode.next = oldNode;
        oldNode.prev.next = newNode;
        oldNode.prev = newNode;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || size <= index) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
    }
}
