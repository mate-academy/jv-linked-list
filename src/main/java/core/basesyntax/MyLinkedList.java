package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> heard;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            heard = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> node = findNodeByIndex(index);
        if (index == size()) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            heard = new Node<>(null, value, node);
            node.prev = heard;
            size++;
            return;
        }
        Node<T> newNode = new Node<>(node.prev, value, node);
        node.prev.next = newNode;
        node.prev = newNode;
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
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findNodeByIndex(index);
        checkIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedNode = findNodeByIndex(index);
        final T value = removedNode.item;
        Node<T> prev = removedNode.prev;
        Node<T> next = removedNode.next;
        if (prev == null) {
            heard = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> value = heard;
        for (int i = 0; i < size(); i++) {
            if (value.item == object || value.item.equals(object)) {
                remove(i);
                return true;
            }
            value = value.next;
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
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException("Out of bound exception for index: " + index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> node;
        if (size() / 2 >= index) {
            node = heard;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size() - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }
}
