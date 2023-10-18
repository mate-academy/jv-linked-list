package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        size++;
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
        } else {
            head.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = getNodeAtIndex(index);
        Node<T> newNode = new Node<>(node, value, node.next);
        node.next.prev = newNode;
        node.next = newNode;
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
        Node<T> node = getNodeAtIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeAtIndex(index);
        T oldValue = node.value;
        node.value = value;

        if (index == 0) {
            head = node;
        } else if (index == size - 1) {
            tail = node;
        }
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeAtIndex(index);
        T removedValue = node.value;
        unlink(node);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
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
            throw new IndexOutOfBoundsException("Index: " + index + " is out of bounds!");
        }
    }

    private Node<T> getNodeAtIndex(int index) {
        checkIndex(index);
        Node<T> currentNode;

        if (index >= size / 2) {
            currentNode = tail;
            for (int i = size - 1; i > index / 2; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
