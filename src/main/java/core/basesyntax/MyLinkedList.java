package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        if (size == 0) {
            linkFirst(value);
        } else {
            linkLast(value);
        }

        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        checkIndexForAdd(index);
        Node<T> node = findNodeByIndex(index);

        if (index == 0) {
            linkFirst(value);
        } else {
            linkBefore(node, value);
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
        checkIndex(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        unlink(node);

        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = findNodeByObject(object);

        if (node == null) {
            return false;
        }

        unlink(node);
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

    private Node<T> findNodeByObject(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == object || (node.value != null && node.value.equals(object))) {
                return node;
            }
            node = node.next;
        }

        return null;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current;
        if (index >= size / 2) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        } else {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }

        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index out of bounds: " + index + ", size: " + size
            );
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index out of bounds: " + index + ", size: " + size
            );
        }
    }

    private void linkFirst(T value) {
        Node<T> newNode = new Node<>(null, value, head);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            head.prev = newNode;
            head = newNode;
        }
    }

    private void linkBefore(Node<T> node, T value) {
        Node<T> newNode = new Node<>(node.prev, value, node);

        node.prev = newNode;
        newNode.prev.next = newNode;
    }

    private void linkLast(T value) {
        Node<T> newNode = new Node<>(tail, value, null);

        tail.next = newNode;
        tail = newNode;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null && node.next == null) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            head = node.next;
            node.next.prev = null;
        } else if (node.next == null) {
            tail = node.prev;
            node.prev.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
