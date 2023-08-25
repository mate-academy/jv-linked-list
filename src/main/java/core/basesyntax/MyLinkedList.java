package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (size == 0) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> node = new Node<>(value);
        checkIndex(index, size);
        if (size == index) {
            add(value);
            return;
        }
        if (index == 0) {
            node.next = head;
            head.prev = node;
            head = node;
        } else {
            Node<T> current = getNodeByIndex(index);
            current.prev.next = node;
            current.prev = node;
            node.next = current;
            node.prev = current.prev;
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
        checkIndex(index, size - 1);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size - 1);
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size - 1);
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.value;
        unlink(current);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null
                && !(object == current.value || object != null && object.equals(current.value))) {
            current = current.next;
        }
        if (current == null) {
            return false;
        }
        unlink(current);
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
        Node<T> current;
        if (index > size / 2) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        } else {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T value) {
            this.value = value;
        }

    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index" + index);
        }
    }

    private void unlink(Node<T> current) {
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return;
        }
        if (current == head) {
            head = current.next;
            current.next = null;
            head.prev = null;
            size--;
            return;
        }
        if (current == tail) {
            tail = current.prev;
            current.prev = null;
            tail.next = null;
            size--;
            return;
        }
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
    }
}
