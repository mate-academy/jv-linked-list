package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    Node<T> head;
    Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("invalid index " + index);
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(value);
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
            size++;
        } else if (index == size) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(value);
            Node<T> currentNode = head;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.next;
            }
            newNode.next = currentNode.next;
            newNode.prev = currentNode;
            currentNode.next.prev = newNode;
            currentNode.next = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T elementOfList : list) {
            add(elementOfList);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("invalid index " + index);
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkingIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkingIndex(index);
        if (index == 0) {
            T oldValue = head.value;
            head = head.next;
            head.prev = null;
            size--;
            return oldValue;
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        T oldValue = currentNode.value;
        unlink(currentNode);
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == null ? object == null : currentNode.value.equals(object)) {
                unlink(currentNode);
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

    private void checkingIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("invalid index " + index);
        }
    }

    private void unlink(Node<T> node) {
        if (node.next != null && node.prev != null) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        if (node.next == null && node.prev == null) {
            head = null;
            tail = null;
        }
        if (node.prev == null) {
            node = node.next;
            node.prev = null;
            head = node;
        }
    }

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }
}
