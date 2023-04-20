package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(tail, value, null);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> node = findByIndex(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        if (node.prev == null) {
            head = newNode;
        } else {
            newNode.prev.next = newNode;
        }
        node.prev = newNode;
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
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findByIndex(index);
        if (node != null) {
            T oldValue = node.value;
            node.value = value;
            return oldValue;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findByIndex(index);
        if (node != null) {
            unlink(node);
            size--;
            return node.value;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        Node<T> previous = null;
        while (current != null) {
            if (object == null ? current.value == null : object.equals(current.value)) {
                if (previous == null) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                if (current.next == null) {
                    tail = previous;
                }
                size--;
                return true;
            }
            previous = current;
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

    private Node<T> findByIndex(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node<T> lastNode = head;
        for (int i = 0; i < index; i++) {
            lastNode = lastNode.next;
        }
        return lastNode;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Input index: " + index + " out of bound: "
                    + size);
        }
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
        node.prev = null;
        node.next = null;
    }

    private class Node<T> {
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

