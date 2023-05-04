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
        if (index < 0 || size < index) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            newNode.next = firstNode;
            firstNode.prev = newNode;
            firstNode = newNode;
            size++;
        } else {
            Node<T> oldNode = getNodebyIndex(index);
            Node<T> newNode = new Node<>(null, value, null);
            newNode.prev = oldNode.prev;
            newNode.next = oldNode;
            oldNode.prev.next = newNode;
            oldNode.prev = newNode;
            size++;
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
        checkIndex(index);
        Node<T> result = getNodebyIndex(index);
        return result.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
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
        return (T) result.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = firstNode;
        while (node != null) {
            if (node.value == object
                    || (node.value != null
                    && node.value.equals(object))) {
                if (node == firstNode) {
                    firstNode = node.next;
                } else {
                    node.prev.next = node.next;
                }
                if (node == lastNode) {
                    lastNode = node.prev;
                } else {
                    node.next.prev = node.prev;
                }
                size--;
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
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

    private void checkIndex(int index) {
        if (index < 0 || size <= index) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
    }
}
