package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> currentNode;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            tail = head = newNode;
            size++;
            return;
        }
        if (size == 1) {
            tail = newNode;
            newNode.prev = head;
            head.next = newNode;
            newNode.next = null;
            size++;
            return;
        }
        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index, " + index);
        }
        if (index == 0 && isEmpty()
                || index == 1 && size == 1
                || index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        newNode.next = currentNode;
        newNode.prev = currentNode.prev;
        currentNode.prev = newNode;
        currentNode.prev.prev.next = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIfIndexOutOfBounds(index);
        currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIfIndexOutOfBounds(index);
        currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIfIndexOutOfBounds(index);
        if (index == 0) {
            currentNode = head;
            head = currentNode.next;
            size--;
            return currentNode.value;
        }
        if (index == size - 1) {
            currentNode = tail;
            tail = currentNode.prev;
            tail.next = null;
            currentNode.prev = null;
            size--;
            return currentNode.value;
        }
        currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == null && object == null
                    || currentNode.value != null && currentNode.value.equals(object)) {
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
        return size() == 0;
    }

    public void unlink(Node node) {
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
        currentNode.prev = null;
        currentNode.next = null;
        size--;
    }

    private void checkIfIndexOutOfBounds(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
}
