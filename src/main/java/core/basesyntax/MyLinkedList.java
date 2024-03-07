package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = tail = newNode;
            size++;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " must be less than " + size());
        }
        if (head == null) {
            head = tail = newNode;
            size++;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
        } else if (index == size()) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            size++;
        } else {
            Node<T> currentNode = foundElementByIndex(index);
            newNode.next = currentNode;
            newNode.prev = currentNode.prev;
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexValue(index);
        return foundElementByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexValue(index);
        Node<T> currentNode = foundElementByIndex(index);
        T node = currentNode.item;
        currentNode.item = value;
        return node;
    }

    @Override
    public T remove(int index) {
        T removedElement;
        checkIndexValue(index);
        if (index == 0) {
            removedElement = head.item;
            head = head.next;
            if (head == null) {
                tail = null;
            }
            size--;
            return removedElement;
        }
        if (index == size - 1) {
            removedElement = tail.item;
            tail = tail.prev;
            size--;
            return removedElement;
        } else {
            Node<T> currentNode = foundElementByIndex(index);
            removedElement = currentNode.item;
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
            size--;
            return removedElement;
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        if (currentNode.item.equals(object)) {
            head = currentNode.next;
            size--;
            return true;
        }
        for (int i = 0; i < size(); i++) {
            if (currentNode.item == object || currentNode.item != null
                    && currentNode.item.equals(object)) {
                currentNode.prev.next = currentNode.next;
                if (currentNode.next != null) {
                    currentNode.next.prev = currentNode.prev;
                } else {
                    tail = currentNode.prev;
                    tail.next = null;
                }
                size--;
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
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item) {
            this.item = item;
        }
    }

    private void checkIndexValue(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " must be less than " + size());
        }
    }

    private Node<T> foundElementByIndex(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
}
