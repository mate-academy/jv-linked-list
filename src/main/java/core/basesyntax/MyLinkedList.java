package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (size == 0) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = findNodeByIndex(index);
        Node<T> node = new Node<>(currentNode.prev, value, currentNode);
        if (index == 0) {
            head.prev = node;
            head = node;
        } else {
            currentNode.prev.next = node;
            currentNode.prev = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size);
        Node<T> node = findNodeByIndex(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> node = findNodeByIndex(index);
        T previousValue = node.item;
        node.item = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> node = findNodeByIndex(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.item == object || node.item != null && node.item.equals(object)) {
                unlink(node);
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

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev,T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void unlink(Node node) {
        if (size == 1) {
            size--;
            head = null;
            tail = null;
            return;
        }
        if (node == head) {
            head = node.next;
            head.prev = null;
            size--;
            return;
        }
        if (node == tail) {
            tail = node.prev;
            tail.next = null;
            size--;
            return;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " was not found!");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = 0; i < size - index - 1; i++) {
                node = node.prev;
            }
        }
        return node;
    }
}
