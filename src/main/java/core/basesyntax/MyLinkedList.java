package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String MESSAGE = "Index not exits.";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> input;

        if (size == 0) {
            input = new Node<>(null, value, null);
            head = input;
        } else {
            input = new Node<>(tail, value, null);
            tail.next = input;
        }

        tail = input;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        if (index == 0) {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
        } else {
            checkIndex(index);
            Node<T> current = getNodeByIndex(index);
            Node<T> added = new Node<>(current.prev, value, current);
            current.prev.next = added;
            current.prev = added;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removed = getNodeByIndex(index);
        T returnValue = removed.value;
        unlink(removed);
        return returnValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        while (current != null) {
            if (object == current.value
                    || object != null && object.equals(current.value)) {
                unlink(current);
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> startPosition;
        if (size / 2 >= index) {
            startPosition = head;
            for (int i = 0; i < index; i++) {
                startPosition = startPosition.next;
            }
        } else {
            startPosition = tail;
            for (int i = size - 1; i > index; i--) {
                startPosition = startPosition.prev;
            }
        }
        return startPosition;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(MESSAGE);
        }
    }

    private void unlink(Node<T> node) {
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

        node.value = null;
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}

