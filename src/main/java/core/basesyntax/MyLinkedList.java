package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        tail = new Node<>(null, null, null);
        head = new Node<>(null, null, null);
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (size == 0) {
            tail = head = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = tail = node;
        } else if (index == 0) {
            node.next = head;
            head.prev = node;
            tail = node;
            head = node;
            size++;
        } else if (index == size) {
            add(value);
        } else {
            Node<T> current = getNodeByIndex(index - 1);
            node.next = current.next;
            node.prev = current;
            current.next = node;
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
        checkEqualsIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkEqualsIndex(index);
        Node<T> node = getNodeByIndex(index);
        T prevValue = node.value;
        node.value = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        checkEqualsIndex(index);
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == current.value || current.value != null && current.value.equals(object)) {
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

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index" + index);
        }
    }

    private void checkEqualsIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index" + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void unlink(Node<T> node) {
        if (node == head && node == tail) {
            tail = head = null;
        } else if (node == head) {
            head = node.next;
            head.prev = null;
        } else if (node == tail) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
