package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node head;
    private Node tail;
    private Node currentNode;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
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
            size++;
            return;
        }

        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head = newNode;
            size++;
            return;
        }

        Node<T> iteratorNode = iterator(index);
        Node<T> newNode = new Node<>(iteratorNode.prev, value, iteratorNode);
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
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
        checkCorrectIndex(index);
        return iterator(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        iterator(index).value = value;
        return value;
    }

    @Override
    public T remove(int index) {
        checkCorrectIndex(index);
        unlink(iterator(index));
        size--;
        return iterator(index).value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode;
        int i = size;

        currentNode = head;
        while (i > 0) {
            if (currentNode.value == object || object != null && currentNode.value.equals(object)) {
                unlink(currentNode);
                size--;
                return true;
            }
            i--;
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
        Node<T> currentNode;

        currentNode = head;
        while (index > 0) {
            if (currentNode.next == tail) {
                return currentNode;
            }
            currentNode = currentNode.next;
            index--;
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
    }

    private void checkCorrectIndex(int index) {
        if (index < 0 || index > size) {
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
