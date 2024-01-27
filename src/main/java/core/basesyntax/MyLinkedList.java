package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;

    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
            size++;
            return;
        }
        Node<T> addNode = new Node<>(null, value, null);
        Node<T> currentNode = tail;
        tail = addNode;
        tail.prev = currentNode;
        currentNode.next = tail;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("invalid index");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = getNode(index);
        linkBefore(value, node);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T el : list) {
            add(el);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node;
        node = getNode(index);
        unLink(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (object == null ? node.item == null : object.equals(node.item)) {
                unLink(node);
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
        return size == 0;
    }

    private void unLink(Node<T> node) {
        if (size == 1) {
            head = new Node<>(null, null, null);
            size--;
            return;
        }
        if (node == head) {
            head = head.next;
            node.next.prev = null;
            size--;
            return;
        }
        if (node == tail) {
            node.prev.next = null;
            size--;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    private void linkBefore(T value, Node<T> target) {
        Node<T> newNode = new Node<>(null, value, null);
        if (target == head) {
            newNode.next = target;
            target.prev = newNode;
            head = newNode;
            return;
        }
        Node<T> node = target.prev;
        newNode.next = target;
        target.prev = newNode;
        node.next = newNode;
        newNode.prev = node;
    }

    private Node<T> getNode(int index) {
        int point = size / 2;
        Node<T> currentNode;
        if (point < index) {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
