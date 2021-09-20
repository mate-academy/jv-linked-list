package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> t = tail;
        Node<T> newNode = new Node<>(t, value, null);
        tail = newNode;
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> var = getNode(index).prev;
            Node<T> newNode = new Node<>(var, value, getNode(index));
            getNode(index).prev = newNode;
            if (var == null) {
                head = newNode;
            } else {
                var.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        final T element = getNode(index).value;
        Node<T> var = getNode(index);
        var.value = value;
        return element;
    }

    @Override
    public T remove(int index) {
        final T element = getNode(index).value;
        Node<T> prev = getNode(index).prev;
        Node<T> next = getNode(index).next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return element;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (object == getNode(i).value || (object != null && object.equals(getNode(i).value))) {
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
        return size == 0;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        if (index < (size >> 1)) {
            Node<T> var = head;
            for (int i = 0; i < index; i++) {
                var = var.next;
            }
            return var;
        } else {
            Node<T> var = tail;
            for (int i = size - 1; i > index; i--) {
                var = var.prev;
            }
            return var;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
