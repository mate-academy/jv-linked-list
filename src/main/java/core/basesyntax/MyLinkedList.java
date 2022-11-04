package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node(tail, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
           add(value);
           return;
        }
        Node<T> current = getNodeByIndex(index);
        Node<T> prev = current.prev;
        Node<T> newNode = new Node(prev, value, current);
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        current.prev = newNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T prevValue = node.value;
        node.value = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlinc(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (node.value == object || node.value != null && node.value.equals(object)) {
                index = i;
                break;
            } else {
                node = node.next;
            }
        }
        if (index == -1) {
            return false;
        }
        remove(index);
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private T unlinc(Node<T> node) {
        final T element = node.value;
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.value = null;
        size--;
        return element;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is incorrect for size " + size);
        }
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
