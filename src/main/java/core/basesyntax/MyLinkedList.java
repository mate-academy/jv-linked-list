package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        tail = new Node<>(tail, value, null);

        if (size == 0) {
            head = tail;
        } else {
            tail.prev.next = tail;
        }

        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            checkIndex(index);

            Node<T> node = getNode(index);
            Node<T> newNode = new Node<>(node.prev, value, node);

            if (newNode.prev == null) {
                head = newNode;
            } else {
                newNode.prev.next = newNode;
            }

            node.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);

        Node<T> node = getNode(index);
        final T oldValue = node.value;
        node.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        Node<T> node = getNode(index);
        return unlinkNode(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNode(object);

        if (node == null) {
            return false;
        }

        unlinkNode(node);

        return true;
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
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size "
                    + size + ".");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> node;

        if (index > size / 2) {
            node = tail;

            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        } else {
            node = head;

            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }

        return node;
    }

    private Node<T> getNode(T value) {
        Node<T> node = head;

        while (node != null) {
            if (value == node.value || value != null && value.equals(node.value)) {
                return node;
            }

            node = node.next;
        }

        return null;
    }

    private T unlinkNode(Node<T> node) {
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

        --size;

        return node.value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
