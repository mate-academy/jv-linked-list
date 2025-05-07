package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
        } else {
            newNode.prev = tail;
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

        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = head.prev;
            size++;
            return;
        }

        Node<T> iteratorNode = iterator(index);
        Node<T> newNode = new Node<>(iteratorNode.prev, value, iteratorNode);
        iteratorNode.prev.next = newNode;
        iteratorNode.prev = newNode;
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
        return iterator(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = iterator(index);
        T currentValue = currentNode.value;
        currentNode.value = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = iterator(index);
        T currentValue = currentNode.value;
        unlink(currentNode);
        return currentValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;

        while (currentNode != null) {
            if (currentNode.value == object || currentNode.value != null
                    && currentNode.value.equals(object)) {
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

    private Node<T> iterator(int index) {
        checkCorrectIndex(index);
        Node<T> currentNode = head;

        for (int i = 0; i != index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkCorrectIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        Node(Node<T> prev,T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
