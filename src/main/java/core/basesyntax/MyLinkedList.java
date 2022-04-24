package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            Node<T> node = new Node<>(null, value, null);
            head = node;
            tail = node;
        } else {
            Node<T> node = new Node<>(tail, value, tail.next);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for list size "
                    + size);
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> node = new Node<>(head.prev, value, head);
            head.prev = node;
            head = node;
            size++;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            Node<T> node = new Node<>(current.prev, value, current);
            current.prev.next = node;
            current.prev = node;
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
        if (index == 0) {
            return head.value;
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        checkElement(index);
        T result;
        if (index == 0) {
            result = head.value;
            head.value = value;
            return result;
        }
        if (index == size - 1) {
            result = tail.value;
            tail.value = value;
            return result;
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        result = current.value;
        current.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        checkElement(index);
        if (index == 0) {
            T item = head.value;
            head = head.next;
            size--;
            return item;
        }
        if (index == size - 1) {
            T item = tail.value;
            tail = tail.prev;
            size--;
            return item;
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.next.prev = current.prev;
        current.prev.next = current.next;
        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        if (size == 1) {
            head.value = null;
            size--;
            return true;
        } else {
            for (int i = 0; i < size; i++) {
                if (current.value == object
                        || current.value != null && current.value.equals(object)) {
                    remove(i);
                    return true;
                }
                current = current.next;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for list size "
                    + size);
        }
    }

    private void checkElement(int index) {
        if (index >= size) {
            throw new NoSuchElementException("There is no element in list with index " + index);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
