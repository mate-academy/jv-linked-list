package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> newNode = new Node<>(value);
        Node<T> currentNode = getNodeByIndex(index);
        if (currentNode == head) {
            head = newNode;
            head.next = currentNode;
            currentNode.prev = head;
        } else {
            currentNode.prev.next = newNode;
            newNode.next = currentNode;
            newNode.prev = currentNode.prev;
            currentNode.prev = newNode;
        }
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedElement = getNodeByIndex(index);
        removeNode(removedElement);
        return removedElement.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(object, currentNode.value)) {
                removeNode(currentNode);
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

    private void checkIndex(int index) {
        if (index < 0 || index > (size - 1)) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + (size - 1));
        }
    }

    private void removeNode(Node<T> removedElement) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (removedElement == head) {
            head = head.next;
            head.prev = null;
        } else if (removedElement == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            removedElement.next.prev = removedElement.prev;
            removedElement.prev.next = removedElement.next;
        }
        size--;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}
