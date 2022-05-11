package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.size = 0;
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
        } else {
            if (index == 0) {
                head.prev = new Node<>(null, value, head);
                head = head.prev;
            } else {
                Node<T> oldNode = node(index);
                Node<T> newNode = new Node<>(oldNode.prev, value, oldNode);
                oldNode.prev.next = newNode;
                oldNode.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> selectedNode = node(index);
        T oldValue = selectedNode.value;
        selectedNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlinkFirst(node(index)).value;
    }

    @Override
    public boolean remove(T object) {
        return unlinkFirst(node(object)) != null;
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
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index: " + index
                    + " for size: " + size);
        }
    }

    private void existIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index: " + index
                    + " for size: " + size);
        }
    }

    private Node<T> node(int index) {
        existIndex(index);
        Node<T> item;
        if (index > size / 2) {
            item = tail;
            for (int i = size - 1; i != index; i--) {
                item = item.prev;
            }
        } else {
            item = head;
            for (int i = 0; i != index; i++) {
                item = item.next;
            }
        }
        return item;
    }

    private Node<T> node(T value) {
        Node<T> item = head;
        while (!(item == null || item.value == value
                || item.value != null
                && item.value.equals(value))) {
            item = item.next;
        }
        return item;
    }

    private Node<T> unlinkFirst(Node<T> node) {
        if (node == null) {
            return null;
        }
        if (size == 1) {
            size--;
            return node;
        }
        if (node == head) {
            head.next.prev = null;
            head = head.next;
            size--;
            return node;
        }
        if (node == tail) {
            tail.prev.next = null;
            tail = tail.prev;
            size--;
            return node;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
        size--;
        return node;
    }
}
