package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(T item) {
            this.item = item;
        }

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            createFirstNode(new Node<>(value));
            size++;
        } else {
            linkAsLast(new Node<>(value));
            size++;
        }
    }

    @Override
    public void add(T value, int index) throws IndexOutOfBoundsException {
        if (index == 0 && size == 0) {
            createFirstNode(new Node<>(value));
        } else if (index == size) {
            linkAsLast(new Node<>(value));
        } else {
            linkBefore(new Node<>(value), findNodeByIndex(index));
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) throws IndexOutOfBoundsException {
        Node<T> nodeFound = findNodeByIndex(index);
        T previousValue = nodeFound.item;
        nodeFound.item = value;
        return previousValue;
    }

    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        Node<T> nodeToRemove = findNodeByIndex(index);
        unlink(nodeToRemove);
        size--;
        return nodeToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == object
                    || currentNode.item != null && currentNode.item.equals(object)) {
                unlink(currentNode);
                size--;
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

    private void createFirstNode(Node<T> newNode) {
        head = newNode;
        tail = newNode;
    }

    private void linkBefore(Node<T> newNode, Node<T> nextNode) {
        newNode.next = nextNode;
        newNode.prev = nextNode.prev;
        if (nextNode.prev != null) {
            nextNode.prev.next = newNode;
        } else {
            head = newNode;
        }
        nextNode.prev = newNode;
    }

    private void linkAsLast(Node<T> newNode) {
        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null && node.next == null) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            head = node.next;
            head.prev = null;
        } else if (node.next == null) {
            tail = node.prev;
            node.prev.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        validateIndex(index);
        if (index + 1 <= size / 2) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node<T> currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    private void validateIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of bounds for size " + size);
        }
    }
}
