package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            this.head = newNode;
        } else {
            tail.next = newNode;
        }
        this.tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> node = findValueByIndex(index);
        Node<T> newNode;
        if (node == head) {
            newNode = new Node<>(null, value, node);
            head = newNode;
        } else {
            newNode = new Node<>(node.prev, value, node);
            node.prev.next = newNode;
        }
        node.prev = newNode;
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
        checkIndex(index);
        return findValueByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findValueByIndex(index);
        T previousValue = node.value;
        node.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findValueByIndex(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == object || node.value != null && node.value.equals(object)) {
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

    private void unlink(Node<T> node) {
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return;
        }
        if (node.prev == null) {
            node.next.prev = null;
            this.head = head.next;
        } else if (node.next == null) {
            node.prev.next = null;
            this.tail = tail.prev;

        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of boundaries!");
        }
    }

    private Node<T> findValueByIndex(int index) {
        Node<T> value;
        if (index < size / 2) {
            value = head;
            for (int i = 0; i < index; i++) {
                value = value.next;
            }
        } else {
            value = tail;
            for (int i = size - 1; i > index; i--) {
                value = value.prev;
            }
        }
        return value;
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
}
