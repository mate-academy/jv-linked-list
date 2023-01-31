package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, null, value);
        if (head == null) {
            head = newNode;
        }
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size()) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(null, head, value);
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, currentNode, value);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> pointer = getNode(index);
        T oldValue = null;
        if (pointer != null) {
            oldValue = pointer.value;
            pointer.value = value;
        }
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> pointer = getNode(index);
        if (pointer != null) {
            T result = pointer.value;
            unlink(pointer);
            return result;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node<T> pointer = head;
        while (pointer != null) {
            if (pointer.value == object || pointer.value != null
                    && (pointer.value.equals(object))) {
                unlink(pointer);
                return true;
            }
            pointer = pointer.next;
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

    private Node<T> getNode(int index) {
        checkIndex(index);
        int count = 0;
        if (index < size / 2) {
            Node<T> pointer = head;
            for (int i = 0; i < index; i++) {
                pointer = pointer.next;
            }
            return pointer;
        } else {
            Node<T> pointer = tail;
            for (int i = index + 1; i < size; i++) {
                pointer = pointer.prev;
            }
            return pointer;
        }
    }

    private void checkIndex(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("index: " + index + " outside size: " + size);
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, Node<T> next, T value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
