package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node first;
    private Node last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> addNode;
        if (size == 0) {
            addNode = new Node<>(null, value, null);
            first = addNode;

        } else {
            addNode = new Node<>(last, value, null);
            last.next = addNode;
        }
        last = addNode;
        size++;
    }

    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> oldFirst = first;
            Node<T> newNode = new Node<>(null, value, oldFirst);
            first = newNode;
            if (oldFirst == null) {
                last = newNode;
            } else {
                oldFirst.prev = newNode;
            }
        } else {
            Node<T> currentNode = findNodeByIndex(index);
            Node<T> prevNode = currentNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, currentNode);
            prevNode.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.value;
        unlink(currentNode);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (currentNode.value == null && object == null || object != null
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
        return size() == 0;
    }

    private void unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.value = null;
        size--;
    }

    private void indexValidation(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index
                + ", and size = " + size());
        }
    }

    private Node<T> findNodeByIndex(int index) {
        indexValidation(index);
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
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
}
