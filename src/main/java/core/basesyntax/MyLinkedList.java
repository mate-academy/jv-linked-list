package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        addToTail(value);
    }

    @Override
    public void add(T value, int index) {
        checkBounds(index);
        if (index == size) {
            addToTail(value);
        } else {
            addWithShift(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (final T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNode(index);
        if (node.prev == null && node.next == null) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            head = node.next;
            head.prev = null;
        } else if (node.next == null) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (object == node.value || node.value != null && node.value.equals(object)) {
                remove(i);
                return true;
            }
            node = node.next;
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

    private Node<T> findNode(int index) {
        checkBounds(index);
        Node<T> node;
        if (index > size >>> 1) {
            node = tail;
            for (int i = size - 1; i >= 0; i--) {
                if (index == i) {
                    break;
                }
                node = node.prev;
            }
        } else {
            node = head;
            for (int i = 0; i < size; i++) {
                if (index == i) {
                    break;
                }
                node = node.next;
            }
        }
        return node;
    }

    private void checkBounds(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of Bounds");
        }
    }

    private void checkIndex(int index) {
        checkBounds(index);
        if (index == size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of Bounds");
        }
    }

    private void addToTail(T value) {
        Node<T> newNode;
        if (size == 0) {
            newNode = new Node<>(value, null, null);
            head = newNode;
        } else {
            Node<T> lastNode = tail;
            newNode = new Node<>(value, null, lastNode);
            lastNode.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private void addWithShift(T value, int index) {
        Node<T> node = findNode(index);
        Node<T> newNode;
        if (node.prev == null) {
            newNode = new Node<>(value, node, null);
            node.prev = newNode;
            head = newNode;
        } else {
            newNode = new Node<>(value, node, node.prev);
            node.prev.next = newNode;
            node.prev = newNode;
        }
        size++;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value, final Node<T> next, final Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
