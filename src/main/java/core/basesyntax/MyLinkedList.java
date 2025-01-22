package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        protected T value;
        protected Node<T> next;
        protected Node<T> prev;

        protected Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);

        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);

        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(value);
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> current = getNode(index);
            Node<T> newNode = new Node<>(value);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        Node<T> firstNewNode = null;
        Node<T> lastNewNode = null;

        for (T value : list) {
            Node<T> newNode = new Node<>(value);

            if (firstNewNode == null) {
                firstNewNode = newNode;
            } else {
                lastNewNode.next = newNode;
                newNode.prev = lastNewNode;
            }

            lastNewNode = newNode;
        }

        if (head == null) {
            head = firstNewNode;
        } else {
            tail.next = firstNewNode;
            firstNewNode.prev = tail;
        }

        tail = lastNewNode;
        size += list.size();
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        T oldValue = current.value;
        current.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        T value = current.value;
        unlink(current);

        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        while (current != null) {
            if ((object == null && current.value == null)
                    || (object != null && object.equals((current.value)))) {
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

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        size--;
    }

    private Node<T> getNode(int index) {
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }
}
