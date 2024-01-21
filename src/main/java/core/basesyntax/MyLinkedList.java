package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, true);
        Node<T> newNode = new Node<>(value);

        if (index == size) {
            add(value);
            return;
        }

        Node<T> current = getNodeByIndex(index);
        newNode.next = current;
        newNode.prev = current.prev;
        current.prev = newNode;

        if (current == head) {
            head = newNode;
        } else {
            newNode.prev.next = newNode;
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
        checkIndex(index, false);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, false);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        Node<T> current = getNodeByIndex(index);
        return unlink(current);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value == null
                    && object == null
                    || object != null
                    && object.equals(current.value)) {
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

    private T unlink(Node<T> current) {
        final T element = current.value;
        final Node<T> next = current.next;
        final Node<T> prev = current.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            current.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            current.next = null;
        }

        current.value = null;
        size--;
        return element;
    }

    private Node<T> getNodeFromHead(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> getNodeFromTail(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < size / 2) {
            return getNodeFromHead(index);
        } else {
            return getNodeFromTail(index);
        }
    }

    private void checkIndex(int index, boolean forAdd) {
        if ((forAdd && (index < 0 || index > size))
                || (!forAdd && (index < 0 || index >= size))) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds"
                    + (forAdd ? " for add." : "."));
        }
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
