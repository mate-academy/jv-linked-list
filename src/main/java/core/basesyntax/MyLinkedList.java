package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> current = tail;
        Node<T> newNode = new Node<>(current, value,null);
        tail = newNode;
        if (current == null) {
            head = newNode;
        } else {
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> current = findNodeByIndex(index);
            Node<T> prev = current.prev;
            Node<T> newNode = new Node<>(prev, value, current);
            current.prev = newNode;
            if (prev == null) {
                head = newNode;
            } else {
                prev.next = newNode;
            }
            size++;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < (size >> 1)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        Node<T> x = findNodeByIndex(index);
        T oldValue = x.value;
        x.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        Node<T> current = findNodeByIndex(index);
        removeNode(current);
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current.next != null) {
            if ((current.value == object)
                    || (current.value != null && current.value.equals(object))) {
                break;
            }
            current = current.next;
        }
        if (Objects.equals(current.value, object)) {
            removeNode(current);
            return true;
        }
        return false;
    }

    private void removeNode(Node<T> current) {
        Node<T> prev = current.prev;
        Node<T> next = current.next;
        size--;
        if (next == null && prev == null) {
            tail = null;
            head = null;
        } else if (prev == null) {
            head = next;
            next.prev = null;
        } else if (next == null) {
            tail = prev;
            prev.next = null;
        } else {
            prev.next = next;
            next.prev = prev;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
