package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

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

    private Node<T> node(int index) {
        Node<T> find;
        if (index < (size / 2)) {
            find = head;
            for (int i = 0; i < index; i++) {
                find = find.next;
            }
        } else {
            find = tail;
            for (int i = size - 1; i > index; i--) {
                find = find.prev;
            }
        }
        return find;
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);

        if (index == size) {
            add(value);
            return;
        }
        Node<T> actual = node(index);
        Node<T> actualPrev = actual.prev;
        Node<T> newNode = new Node<>(actualPrev, value, actual);
        node(index).prev = newNode;
        if (actualPrev == null) {
            head = newNode;
        } else {
            actualPrev.next = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> current = node(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = head; x != null; x = x.next) {
            if (object == x.item || x.item.equals(object) && x.item != null) {
                unlink(x);
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

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private T unlink(Node<T> actual) {
        final T element = actual.item;
        final Node<T> next = actual.next;
        final Node<T> prev = actual.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            actual.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            actual.next = null;
        }

        actual.item = null;
        size--;
        return element;
    }
}
