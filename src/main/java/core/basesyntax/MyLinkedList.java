package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private Node<T> currentNode;
    private int size;

    @Override
    public void add(T value) {
        currentNode = new Node<>(tail, value, null);
        if (size < 1) {
            head = currentNode;
        } else {
            tail.next = currentNode;
        }
        tail = currentNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        currentNode = findNode(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        if (currentNode.prev != null) {
            currentNode.prev.next = newNode;
        }
        currentNode.prev = newNode;
        if (index == 0) {
            head = newNode;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        currentNode = head;
        currentNode = findNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        currentNode = findNode(index);
        T currentValue = currentNode.value;
        currentNode.value = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        currentNode = findNode(index);
        final Node<T> current = currentNode;
        if (index == 0) {
            head = currentNode.next;
        }
        if (index == size - 1) {
            tail = currentNode.prev;
        }
        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        }
        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        if (size == 1 && Objects.equals(object, head.value)) {
            head.value = null;
            size--;
            return true;
        }
        currentNode = head;
        if (Objects.equals(currentNode.value, object)) {
            currentNode.next.prev = null;
            head = currentNode.next;
            size--;
            return true;
        }
        for (int i = 0; i < size; i++) {
            if (Objects.equals(currentNode.value, object)) {
                currentNode.prev.next = currentNode.next;
                currentNode.next.prev = currentNode.prev;
                size--;
                return true;
            } else {
                currentNode = currentNode.next;
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> findNode(int index) {
        checkIndex(index);
        if (size / 2 > index) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
