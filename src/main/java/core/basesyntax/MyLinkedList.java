package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
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
        if (size == 0) {
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
        Node<T> newNode = new Node<>(value);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of list bound");
        }

        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> prevNode = currentNode.prev;
            newNode.next = currentNode;
            currentNode.prev = newNode;
            newNode.prev = prevNode;
            prevNode.next = newNode;
        }
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
        checkIndexRange(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndexRange(index);
        Node<T> currentNode = getNodeByIndex(index);
        T changedElement = currentNode.element;
        currentNode.element = value;
        return changedElement;
    }

    @Override
    public T remove(int index) {
        checkIndexRange(index);
        Node<T> removedNode = getNodeByIndex(index);
        T value = null;

        if (index == 0) {
            value = removedNode.element;
            head = removedNode.next;
            if (head == null) {
                tail = null;
            }
        } else if (index == size - 1) {
            value = removedNode.element;
            tail = removedNode.prev;
            removedNode.prev = null;
            tail.next = null;
        } else {
            value = removedNode.element;
            Node<T> prevNode = removedNode.prev;
            Node<T> nextNode = removedNode.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.element != null && currentNode.element.equals(object)
                    || currentNode.element == object) {
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkIndexRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of list bound");
        }
    }
}
