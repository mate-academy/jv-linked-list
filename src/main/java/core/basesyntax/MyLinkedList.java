package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> findNode = find(index);
        Node<T> newNode;
        if (findNode.equals(head)) {
            newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            newNode = new Node<>(findNode.prev, value, findNode);
            findNode.prev.next = newNode;
            findNode.prev = newNode;
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
        checkIndex(index);
        Node<T> findNode = find(index);
        return findNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> findNode = find(index);
        T deletedValue = findNode.item;
        findNode.item = value;
        return deletedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> findNode = find(index);
        if (findNode.equals(head) && findNode.equals(tail)) {
            head = tail = null;
        } else if (findNode.equals(head)) {
            head = head.next;
        } else if (findNode.equals(tail)) {
            tail = tail.prev;
        } else {
            findNode.prev.next = findNode.next;
            findNode.next.prev = findNode.prev;
        }
        T deletedValue = findNode.item;
        findNode.item = null;
        size--;
        return deletedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> findNode = head;
        for (int i = 0; i < size; i++) {
            if ((object != null && object.equals(findNode.item)) || (object == findNode.item)) {
                remove(i);
                return true;
            }
            findNode = findNode.next;
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
            throw new IndexOutOfBoundsException("Specified index is invalid");
        }
    }

    private Node<T> find(int index) {
        Node<T> findNode = head;
        if (index <= size / 2) {
            for (int i = 0; i < index; i++) {
                findNode = findNode.next;
            }
        } else {
            findNode = tail;
            for (int i = size - 1; i > index; i--) {
                findNode = findNode.prev;
            }
        }
        return findNode;
    }

    static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}

