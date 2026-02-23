package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);

        if (size == 0) {
            tail = newNode;
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
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

        Node<T> next = getNode(index);
        Node<T> prev = next.prev;

        Node<T> newNode = new Node<>(prev, value, next);

        next.prev = newNode;

        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
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
        return getNode(index).item;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + " doesn't exist");
        }

        Node<T> current;

        if (index < size / 2) {
            current = head;
            for (int i = 0; index > i; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; index < i; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T old = node.item;
        node.item = value;
        return old;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        final T removed = node.item;

        unlink(node);

        return removed;
    }

    @Override
    public boolean remove(T object) {
        Node<T> curr = head;

        while (curr != null) {

            if ((curr.item == null && object == null)
                    || (object != null && object.equals(curr.item))) {
                unlink(curr);
                return true;
            }
            curr = curr.next;
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

    private static class Node<E> {
        private Node<E> next;
        private Node<E> prev;
        private E item;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void unlink(Node<T> node) {
        Node<T> previous = node.prev;
        Node<T> next = node.next;

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
    }
}
