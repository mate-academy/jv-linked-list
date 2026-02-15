package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private transient int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, lastNode, null);
        if (isEmpty()) {
            firstNode = newNode;
        } else {
            lastNode.next = newNode;
        }
        lastNode = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexToAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            insert(value);
            return;
        }
        addNode(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexToGet(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexToGet(index);
        Node<T> currentNode = getNode(index);
        T replaceValue = currentNode.value;
        currentNode.value = value;
        return replaceValue;
    }

    @Override
    public T remove(int index) {
        checkIndexToGet(index);
        Node<T> currentNode = getNode(index);
        unlink(currentNode);
        size--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = getNode(object);
        if (currentNode == null) {
            return false;
        }
        unlink(currentNode);
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void insert(T value) {
        Node<T> newNode = new Node<>(value, null, firstNode);
        if (isEmpty()) {
            firstNode = newNode;
            lastNode = newNode;
        } else {
            firstNode.prev = newNode;
        }
        firstNode = newNode;
        size++;
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        } else {
            firstNode = currentNode.next;
        }
        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        } else {
            lastNode = currentNode.prev;
        }
    }

    private void addNode(T value, int index) {
        Node<T> currentNode = getNode(index);
        Node<T> newNode = new Node<>(value, currentNode.prev, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    private Node<T> getNode(int index) {
        boolean indexVector = index < size / 2;
        int currentIndex = indexVector ? 0 : size - 1;
        Node<T> currentNode = indexVector ? firstNode : lastNode;
        while (currentIndex != index) {
            currentNode = indexVector ? currentNode.next : currentNode.prev;
            currentIndex = indexVector ? currentIndex + 1 : currentIndex - 1;
        }
        return currentNode;
    }

    private Node<T> getNode(T object) {
        Node<T> currentNode = firstNode;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private void checkIndexToAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void checkIndexToGet(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
