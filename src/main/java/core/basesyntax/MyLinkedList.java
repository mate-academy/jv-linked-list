package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);

        isValidForAdding(index);

        if (index == 0) {
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
        } else if (index == size) {
            newNode.prev = tail;
            if (tail != null) {
                tail.next = newNode;
            }
            tail = newNode;
        } else {
            Node<T> current = getNodeIndex(index);
            Node<T> previous = current.prev;

            newNode.next = current;
            newNode.prev = previous;

            if (previous != null) {
                previous.next = newNode;
            }
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        T previousValue = current.value;
        current.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        if (index == 0) {
            final T value = head.value;
            head = head.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
            size--;
            return value;
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        if (current.next == null) {
            tail = current.prev;
        } else {
            current.next.prev = current.prev;
            tail = current.next;
        }

        current.prev.next = current.next;
        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false;
        }
        if (head.value == null ? object == null : head.value.equals(object)) {
            head = head.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
            size--;
            return true;
        }

        Node<T> current = head;
        while (current.next != null) {
            if (current.next.value == null ? object == null
                    : current.next.value.equals(object)) {
                current.next = current.next.next;
                tail = current.next;
                size--;
                return true;
            }
            current = current.next;
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

    private void isValidForAdding(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Cannot add element. Index out of bound: " + index);
        }
    }

    private Node<T> getNodeIndex(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot locate element at index "
                    + index + "Index out of bounds.");
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        Node(T value) {
            this.value = value;
        }
    }
}
