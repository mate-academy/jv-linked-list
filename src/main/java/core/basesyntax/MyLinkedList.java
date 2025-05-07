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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            if (isEmpty()) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
            size++;
        } else if (index == size) {
            add(value);
        } else {
            Node<T> current = findNodeByIndex(index);
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;
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
        validateIndex(index);
        Node<T> current = findNodeByIndex(index);
        return current.data;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> current = findNodeByIndex(index);
        T oldValue = current.data;
        current.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> current = findNodeByIndex(index);
        unlink(current);
        return current.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == current.data || object != null && object.equals(current.data)) {
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void unlink(Node<T> node) {
        if (node.equals(head)) {
            head = head.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.equals(tail)) {
            tail = tail.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        private Node(T data) {
            this.data = data;
        }
    }
}
