package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        Node<T> currentNode = findNode(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);

        if (currentNode.equals(head)) {
            head = newNode;
        } else {
            currentNode.prev.next = newNode;
        }

        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        Node<T> node = findNode(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = findNode(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = findNode(object);
        if (node == null) {
            return false;
        }
        unlink(node);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't find an element by index ["
                    + index + "]. Index is out of bounds");
        }
    }

    private Node<T> findNode(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = 1; i < size - index; i++) {
                currentNode = currentNode.prev;
            }
        }

        return currentNode;
    }

    private Node<T> findNode(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.value != null && node.value.equals(object) || object == node.value) {
                return node;
            }
        }

        return null;
    }

    private void unlink(Node<T> node) {
        size--;

        if (node.equals(head) && node.equals(tail)) {
            head = null;
            tail = null;
            return;
        }

        if (node.equals(head)) {
            head = node.next;
            node.next.prev = node.prev;
            return;
        }

        if (node.equals(tail)) {
            tail = node.prev;
            node.prev.next = node.next;
            return;
        }

        node.next.prev = node.prev;
        node.prev.next = node.next;
    }
}
