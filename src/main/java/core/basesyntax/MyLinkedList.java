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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> var = node(index).prev;
            Node<T> newNode = new Node<>(var, value, node(index));
            node(index).prev = newNode;
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
        checkIndex(index);
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        final T element = node(index).value;
        Node<T> var = node(index);
        var.value = value;
        return element;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        final T element = node(index).value;
        Node<T> prev = node(index).prev;
        Node<T> next = node(index).next;
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
        if (object == null) {
            for (int i = 0; i < size; i++) {
                if (node(i).value == null) {
                    remove(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (object.equals(node(i).value)) {
                    remove(i);
                    return true;
                }
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

    private Node<T> node(int index) {
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
}
