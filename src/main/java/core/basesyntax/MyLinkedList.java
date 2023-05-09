package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null && tail == null) {
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
        } else {
            checkIndex(index);
            Node<T> newNode = new Node<>(value);
            Node<T> currentNode = getNodeByIndex(index);
            if (isBetween(currentNode) || isTail(currentNode)) {
                currentNode.prev.next = newNode;
                newNode.next = currentNode;
                newNode.prev = currentNode.prev;
                currentNode.prev = newNode;
            } else if (isHead(currentNode)) {
                head = newNode;
                head.next = currentNode;
                currentNode.prev = head;
            }
            size++;
        }
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
            throw new IndexOutOfBoundsException("Index must be between 0 and " + (size));
        }
    }

    private boolean isHead(Node<T> node) {
        return node.prev == null && node.next != null;
    }

    private boolean isTail(Node<T> node) {
        return node.prev != null && node.next == null;
    }

    private boolean isBetween(Node<T> node) {
        return node.prev != null && node.next != null;
    }

    private void removeNode(Node<T> removedElement) {
        if (isBetween(removedElement)) {
            removedElement.next.prev = removedElement.prev;
            removedElement.prev.next = removedElement.next;
        } else if (isHead(removedElement)) {
            head = head.next;
        } else if (isTail(removedElement)) {
            tail = tail.prev;
        } else if (size == 1) {
            head = null;
            tail = null;
        }
        removedElement.next = null;
        removedElement.prev = null;
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
}
