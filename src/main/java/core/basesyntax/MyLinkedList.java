package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    Node<T> head;
    Node<T> tail;
    int size = 0;

    private static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (head == null) {
            Node<T> newNode = new Node<>(null, value, null);
            head = tail = newNode;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        if (size == index) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> previousNode = searchNode(index);
            Node<T> newNode = new Node<>(previousNode.prev, value, previousNode);
            previousNode.prev.next = newNode;
            previousNode.prev = newNode;
            size++;
        }

    }

    private Node<T> searchNode(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return searchNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = searchNode(index);
        T lastValue = currentNode.value;
        currentNode.value = value;
        return lastValue;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = searchNode(index);
        removeService(current);
        return current.value;
    }

    private void removeService(Node<T> current) {
        Node<T> prev = current.prev;
        Node<T> next = current.next;
        if (size == 1) {
            tail = head = null;
        } else if (prev == null) {
            head.next.prev = null;
            head = head.next;
        } else if (next == null) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            prev.next = next;
            next.prev = prev;
        }
        size--;
    }


    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(object, current.value)) {
                removeService(current);
                return true;
            }
            current = current.next;
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


}
