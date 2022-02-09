package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (isEmpty()) {
            newNode = new Node<>(null, value, null);
            head = tail = newNode;
        } else {
            newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        Node<T> newNode;
        if (isEmpty()) {
            newNode = new Node<>(null, value, null);
            head = tail = newNode;
            size++;
            return;
        }
        if (index == 0) {
            newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = findNodeByIndex(index);
        newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T elements : list) {
            add(elements);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode;
        if (size == 0) {
            newNode = new Node<>(null, value, null);
            head = tail = newNode;
            return null;
        }
        if (index == 0) {
            final Node<T> deleted = head;
            newNode = new Node<>(null, value, head.next);
            head.next.prev = newNode;
            head = newNode;
            return deleted.value;
        }
        Node<T> currentNode = findNodeByIndex(index);
        final Node<T> deleted = currentNode;
        newNode = new Node<>(currentNode.prev, value, currentNode.next);
        currentNode.prev.next = newNode;
        currentNode.next.prev = newNode;
        return deleted.value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> deleted;
        if (index == 0) {
            deleted = head;
            head = head.next;
            if (head == null) {
                tail = null;
            }
            size--;
            return deleted.value;
        }
        if (index == size - 1) {
            deleted = tail;
            tail.prev.next = null;
            size--;
            return deleted.value;
        }
        Node<T> currentNode = findNodeByIndex(index);
        deleted = currentNode;
        erase(currentNode);
        return deleted.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object || currentNode.value.equals(object)) {
                if (currentNode.prev == null) {
                    if (currentNode.next == null) {
                        currentNode.value = null;
                        size--;
                        return true;
                    }
                    currentNode.next.prev = null;
                    head = currentNode.next;
                    size--;
                    return true;
                }
                if (currentNode.next == null) {
                    tail = currentNode.prev;
                    currentNode.prev.next = null;
                    size--;
                    return true;
                }
                erase(currentNode);
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void erase(Node<T> currentNode) {
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
        size--;
    }
}
