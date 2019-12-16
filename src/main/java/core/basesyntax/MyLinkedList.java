package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> next = findByIndex(index);
        Node<T> previous = next.prev;
        Node<T> node = new Node<>(previous, value, next);
        if (previous == null) {
            head = node;
        } else {
            previous.next = node;
        }
        next.prev = node;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findByIndex(index).value;
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        findByIndex(index).value = value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeForRemove = findByIndex(index);
        Node<T> previous = nodeForRemove.prev;
        Node<T> next = nodeForRemove.next;
        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
        }
        if (next == null) {
            tail = previous;
        } else {
            next.prev = previous;
        }
        size--;
        return nodeForRemove.value;
    }

    @Override
    public T remove(T t) {
        Node<T> nodeForRemove = head;
        for (int i = 0; i < size; i++) {
            if (t == nodeForRemove.value || t != null && t.equals(nodeForRemove.value)) {
                return remove(i);
            } else {
                nodeForRemove = nodeForRemove.next;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> findByIndex(int index) {
        Node<T> node;
        if (index > size / 2) {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        } else {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        private Node(E value) {
            prev = null;
            this.value = value;
            next = null;
        }

        public Node(Node<E> prev, E value, Node<E> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}

