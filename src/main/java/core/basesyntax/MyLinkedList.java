package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int ONE = 1;
    private static final int ZERO = 0;

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node;
        if (isEmpty()) {
            size++;
            node = new Node<T>(null, value, null);
            tail = node;
            head = node;
            return;
        }
        node = new Node<T>(tail, value, null);
        tail.next = node;
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == ZERO && index == ZERO || index == size) {
            add(value);
            return;
        }
        if (index == size - ONE) {
            T last = remove(size - ONE);
            add(value);
            add(last);
            return;
        }
        if (index == ZERO && size != ZERO) {
            Node<T> node = new Node(null, value, head);
            head.prev = node;
            head = node;
            size++;
            return;
        }
        checkIndex(index);
        Node<T> current = getNode(index);
        Node<T> node = new Node(current.prev, value, current);
        current.prev.next = node;
        current.prev = node;
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
        Node<T> current = getNode(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        T oldValue = current.value;
        if (index == ZERO) {
            Node<T> node = new Node(null, value, head.next);
            head.next.prev = node;
            head = node;
            return oldValue;
        }
        if (index == size - ONE) {
            Node<T> node = new Node(tail.prev, value, null);
            tail.prev.next = node;
            tail = node;
            return oldValue;
        }
        Node<T> node = new Node(current.prev, value, current.next);
        current.prev.next = node;
        current.next.prev = node;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNode(index);
        if (index == ZERO && size == ONE) {
            head = null;
            tail = null;
            size--;
            return current.value;
        }
        if (index == ZERO && size != ZERO) {
            head = current.next;
            current.next.prev = null;
            size--;
            return current.value;
        }
        if (index == size - ONE) {
            tail = current.prev;
            current.prev.next = null;
            size--;
            return current.value;
        }
        checkIndex(index);
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        int index = ZERO;
        do {
            if ((current.value == object)
                    || current.value != null && current.value.equals(object)) {
                remove(index);
                return true;
            }
            index++;
            current = current.next;

        } while (current.next != null);
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
        Node<T> current = head;
        int i = index;
        while (current.next != null && i != ZERO) {
            current = current.next;
            i--;
        }
        return current;
    }

    private void checkIndex(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        private Node next;
        private Node prev;
        private T value;

        public Node(Node<T> prev,T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
