package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    @Override
    public void add(T value) {
        addLast(value);
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (size == index) {
            addLast(value);
        } else {
            Node<T> node = getNode(index);
            addBefore(value, node);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            addLast(value);
            size++;
        }
    }

    @Override
    public T get(int index) {
        checkIndexInclusive(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexInclusive(index);
        Node node = getNode(index);
        T oldValue = (T) node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexInclusive(index);
        Node node = getNode(index);
        T removedValue = (T) node.value;
        removeNode(node);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = firstNode;
        while (currentNode != null) {
            if (currentNode.value == object
                    || (currentNode.value != null && currentNode.value.equals(object))) {
                removeNode(currentNode);
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

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<>(lastNode, value, null);
        if (size == 0) {
            firstNode = newNode;
        } else {
            lastNode.next = newNode;
        }
        lastNode = newNode;
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode = firstNode;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void checkIndexInclusive(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
    }

    private void removeNode(Node<T> node) {
        if (node == firstNode) {
            firstNode = node.next;
        }
        if (node == lastNode) {
            lastNode = node.prev;
        }
        if (node.next != null && node.prev != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void addBefore(T value, Node<T> nextNode) {
        Node<T> prevNode = nextNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, nextNode);
        nextNode.prev = newNode;
        if (prevNode == null) {
            firstNode = newNode;
        } else {
            prevNode.next = newNode;
        }
    }
}
