package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static final int FIRST_INDEX = 0;
    private static final int INDEX_STEP = 1;

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);

        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);

        if (index == size) {
            add(value);
            return;
        }

        Node<T> current = getNode(index);
        Node<T> newNode = new Node<>(value);

        newNode.next = current;
        newNode.prev = current.prev;

        if (current.prev == null) {
            head = newNode;
        } else {
            current.prev.next = newNode;
        }

        current.prev = newNode;
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
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);

        T oldValue = node.value;
        node.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        T removedValue = node.value;

        unlink(node);

        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        while (current != null) {
            if (object == null
                    ? current.value == null
                    : object.equals(current.value)) {

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
        return size == FIRST_INDEX;
    }

    private Node<T> getNode(int index) {
        checkElementIndex(index);

        if (index < size / 2) {
            Node<T> current = head;

            for (int i = FIRST_INDEX; i < index; i += INDEX_STEP) {
                current = current.next;
            }

            return current;
        }

        Node<T> current = tail;

        for (int i = size - INDEX_STEP; i > index; i -= INDEX_STEP) {
            current = current.prev;
        }

        return current;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }

        size--;
    }

    private void checkElementIndex(int index) {
        if (index < FIRST_INDEX || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", size: " + size
            );
        }
    }

    private void checkPositionIndex(int index) {
        if (index < FIRST_INDEX || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", size: " + size
            );
        }
    }

    private static class Node<T> {

        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }
}
