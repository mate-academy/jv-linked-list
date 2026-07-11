package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            listStart(node);
        } else {
            addToEnd(node);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> node = new Node<>(null, value, null);
        checkIndexAdd(index);
        if (head == null) {
            listStart(node);
        } else if (index == 0) {
            node.next = head;
            head.prev = node;
            head = node;
        } else if (index == size) {
            addToEnd(node);
        } else {
            Node<T> current = nodeIterator(index);
            node.prev = current.prev;
            node.next = current;
            current.prev.next = node;
            current.prev = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            return;
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return nodeIterator(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> targetNode = nodeIterator(index);
        T oldValue = targetNode.value;
        targetNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = nodeIterator(index);
        unlink(current);
        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = nodeIterator(object);
        if (current == null) {
            return false;
        }
        unlink(current);
        size --;
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

    private void listStart(Node<T> node) {
        head = node;
        tail = node;
    }

    private void addToEnd(Node<T> node) {
        tail.next = node;
        node.prev = tail;
        tail = node;
    }

    private void checkIndexAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private void checkIndex(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private Node<T> nodeIterator(int index) {
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

    private Node<T> nodeIterator(T value) {
        Node<T> current = head;
        while (current != null) {
            if (current.value == null && value == null
                    || current.value != null
                    && current.value.equals(value)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private void unlink(Node<T> current) {
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
