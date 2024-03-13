package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> current = getNodeAtIndex(index);
            Node<T> prevNode = current.prev;

            prevNode.next = newNode;
            newNode.prev = prevNode;

            newNode.next = current;
            current.prev = newNode;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return getNodeAtIndex(index).data;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        Node<T> node = getNodeAtIndex(index);
        T oldValue = node.data;
        node.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        Node<T> removedNode = getNodeAtIndex(index);
        removeAtIndex(index, removedNode);

        return removedNode.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(object, current.data)) {
                unlink(current);
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private void removeAtIndex(int index, Node<T> removedNode) {
        unlink(removedNode);

        if (index == 0) {
            head = removedNode.next;
        } else {
            Node<T> prevNode = removedNode.prev;
            prevNode.next = removedNode.next;
        }

        if (index == size - 1) {
            tail = removedNode.prev;
        } else {
            Node<T> nextNode = removedNode.next;
            nextNode.prev = removedNode.prev;
        }

        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNodeAtIndex(int index) {
        Node<T> operating = head;
        for (int i = 0; i < index; i++) {
            operating = operating.next;
        }
        return operating;
    }

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;

        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }

        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
    }

    private static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }
}
