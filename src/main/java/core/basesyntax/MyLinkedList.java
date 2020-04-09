package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public boolean add(T value) {
        if (head == null) {
            Node<T> newNode = new Node<>(value, null, null);
            head = newNode;
            size++;
            return true;
        }

        Node<T> currentNode = head;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }

        Node<T> newNode = new Node<>(value, null, currentNode);
        currentNode.next = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (size == 0) {
            Node<T> newNode = new Node<>(value, null, null);
            head = newNode;
            size++;
            return;
        }

        Node<T> currentNode = head;
        for (int i = 0; i < index - 1; i++) {
            currentNode = currentNode.next;
        }

        Node<T> newNode = new Node<>(value, currentNode.next, currentNode);
        currentNode.next = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (head == null) {
            return null;
        }

        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode.data;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        T oldData = currentNode.data;
        currentNode.data = value;

        return oldData;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        T result = null;

        if (index == 0) {
            result = head.data;
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
        } else {
            Node<T> currentNode = head;

            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }

            if (currentNode.next != null) {
                currentNode.next.prev = currentNode.prev;
            }

            currentNode.prev.next = currentNode.next;
            result = currentNode.data;
        }

        size--;
        return result;
    }

    @Override
    public boolean remove(T t) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.data == t || currentNode.data.equals(t)) {
                remove(i);
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

    private class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(T data, Node<T> next, Node<T> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}
