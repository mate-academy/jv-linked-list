package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = this.tail = new Node<>(null);
        this.size = 0;
    }

    private class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        newNode.prev = tail;
        newNode.next = tail.prev;
        tail.next = null;
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("No such index " + index);
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        Node<T> newNode = new Node<>(value);
        newNode.prev = currentNode;
        newNode.next = currentNode.next;
        if (currentNode.next != null) {
            currentNode.next.prev = newNode;
        }
        currentNode.next = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        Node<T> currentNode = getNodeByIndexWithValidation(index);
        return currentNode.element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndexWithValidation(index);

        T oldValue = currentNode.element;
        currentNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndexWithValidation(index);

        if (currentNode.prev == null) {
            head = currentNode.next;
            size--;
        } else if (currentNode.next == null) {
            tail = currentNode.prev;
            size--;
        } else {
            currentNode.next.prev = currentNode.prev;
            currentNode.prev.next = currentNode.next;
            size--;
        }
        return currentNode.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            node = node.next;
            if (Objects.equals(node.element, object)) {
                if (node.prev == null && node.next == null) {
                    head = tail = null;
                } else if (node.prev == null) {
                    head = node.next;
                } else if (node.next == null) {
                    tail = node.prev;
                } else {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                }
                size--;
                return true;
            }
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

    private Node<T> getNodeByIndexWithValidation(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("No such index " + index);
        }
        return getNodeByIndex(index);
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i <= index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }
}
