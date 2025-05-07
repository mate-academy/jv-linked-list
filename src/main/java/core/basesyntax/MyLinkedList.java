package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> tail;
    private Node<T> head;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<T>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<T>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("From add Index " + index + " is out of scope");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> after = findByIndex(index);
        Node<T> before = after.prev;
        Node<T> newNode = new Node<T>(before, value, after);
        after.prev = newNode;
        if (before != null) {
            before.next = newNode;
        } else {
            head = newNode;
        }
        size++;
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
        return findByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = findByIndex(index);
        T oldValue = newNode.item;
        newNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findByIndex(index));
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

    private Node<T> findByIndex(int index) {
        Node<T> node;
        if (index < (size >> 1)) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        Node<T> next = node.next;
        Node<T> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.item = null;
        size--;
        return element;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds " + index);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
