package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private Node<T> iterateToIndex(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null || tail == null) {
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
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException(index + " out of bounds for " + size());
        }
        Node<T> newNode = new Node<>(value);
        if (head == null || tail == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size()) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> oldNode = iterateToIndex(index);
            newNode.next = oldNode;
            newNode.prev = oldNode.prev;
            oldNode.prev.next = newNode;
            oldNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T node: list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(index + " out of bounds for " + size());
        }
        Node<T> node = iterateToIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(index + " out of bounds for " + size());
        }
        T removeNode = remove(index);
        add(value, index);
        return removeNode;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(index + " out of bounds for " + size());
        }
        T removeValue;
        if (index == 0) {
            removeValue = head.value;
            head = head.next;
        } else if (index == size() - 1) {
            removeValue = tail.value;
            tail = tail.prev;
        } else {
            Node<T> oldNode = head;
            for (int i = 0; i < index; i++) {
                oldNode = oldNode.next;
            }
            removeValue = oldNode.value;
            oldNode.prev.next = oldNode.next;
            oldNode.next.prev = oldNode.prev;
        }
        size--;
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size(); i++) {
            if (object == get(i) || object != null && object.equals(get(i))) {
                remove(i);
                return true;
            }
            node = node.next;
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
}
