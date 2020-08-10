package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public boolean add(T value) {
        if (isEmpty()) {
            Node<T> node = new Node<>(value, null, null);
            head = node;
            tail = node;
        } else {
            Node<T> node = new Node<>(value, null, tail);
            tail.next = node;
            tail = node;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(value, head, null);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node<T> node = getAllNode(index);
        Node<T> newNode = new Node<>(value, node, node.prev);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getAllNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getAllNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getAllNode(index);
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return node.value;
        }
        if (index == 0) {
            node.next.prev = null;
            head = node.next;
            size--;
            return node.value;
        }
        if (index == size - 1) {
            node.prev.next = null;
            tail = node.prev;
            size--;
            return node.value;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if (t == getAllNode(i).value || getAllNode(i).value.equals(t)) {
                remove(i);
                return true;
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
        if (size == 0) {
            return true;
        }
        return false;
    }

    private Node<T> getAllNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        int i = 0;
        Node<T> node = head;
        while (i < index) {
            node = node.next;
            i++;
        }
        return node;
    }
}
