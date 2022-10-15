package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
        } else {
            node.prev = tail;
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = findByIndex(index);
        Node<T> newNode = new Node<>(value);
        if (head == node) {
            head = newNode;
        } else {
            node.prev.next = newNode;
        }
        newNode.prev = node.prev;
        newNode.next = node;
        node.prev = newNode;
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
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(findByIndex(index)).value;
    }

    @Override
    public boolean remove(T object) {
        return unlink(findByObject(object)) != null;
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index: " + index);
        }
    }

    private Node<T> findByIndex(int index) {
        checkIndex(index);
        Node<T> element;
        if (index < size / 2) {
            element = head;
            for (int i = 0; i < index; i++) {
                element = element.next;
            }
        } else {
            element = tail;
            for (int i = size - 1; i > index; i--) {
                element = element.prev;
            }
        }
        return element;
    }

    private Node<T> findByObject(T object) {
        Node<T> element = head;
        do {
            T value = element.value;
            if (value == object || value != null && value.equals(object)) {
                return element;
            }
            element = element.next;
        } while (element.next != null);
        return null;
    }

    private Node<T> unlink(Node<T> node) {
        if (node == null) {
            return null;
        }
        if (node.next == node.prev) {
            head = null;
            tail = null;
        } else if (node.next == null) {
            node.prev.next = null;
            tail = node.prev;
        } else if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
        return node;
    }
}
