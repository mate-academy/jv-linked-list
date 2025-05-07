package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> lastNode = tail;
        Node<T> newNode = new Node<>(lastNode, value, null);
        tail = newNode;
        if (head == null) {
            head = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index != size) {
            checkForIndexBound(index);
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            addFirst(value, index);
        } else {
            addMiddle(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkForIndexBound(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkForIndexBound(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkForIndexBound(index);
        Node<T> nodeForRemove = getNodeByIndex(index);
        unlink(nodeForRemove);
        return nodeForRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (object == currentNode.value || object != null
                    && object.equals(currentNode.value)) {
                unlink(currentNode);
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

    private void addFirst(T value, int index) {
        Node<T> actual = getNodeByIndex(index);
        Node<T> newNode = new Node<>(null, value, actual);
        actual.prev = newNode;
        head = newNode;
        size++;
    }

    private void addMiddle(T value, int index) {
        Node<T> actual = getNodeByIndex(index);
        Node<T> newNode = new Node<>(actual.prev, value, actual.next);
        newNode.prev = actual.prev;
        actual.prev.next = newNode;
        actual.prev = newNode;
        newNode.next = actual;
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> indexNode;
        if (index < size / 2) {
            indexNode = searchFromHead(index);
        } else {
            indexNode = searchFromTail(index);
        }
        return indexNode;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> indexNode;
        indexNode = tail;
        for (int i = size - 1; i > index; i--) {
            indexNode = indexNode.prev;
        }
        return indexNode;
    }

    private Node<T> searchFromHead(int index) {
        Node<T> indexNode;
        indexNode = head;
        for (int i = 0; i < index; i++) {
            indexNode = indexNode.next;
        }
        return indexNode;
    }

    private void unlink(Node<T> node) {
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

    private void checkForIndexBound(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + ", size " + size);
        }
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
