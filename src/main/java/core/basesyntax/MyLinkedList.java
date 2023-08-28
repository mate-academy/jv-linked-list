package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = tail = new Node<>(null, value, null);
        } else {
        Node<T> newNode = new Node<>(tail, value, tail.next);
        tail.next = newNode;
        tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (size == index) {
            add(value);
            return;
        }
        if (index == 0) {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
            size++;
            return;
        }
        Node<T> current = findByIndex(index);
        current.prev.next = new Node<>(current.prev, value, current);
        current.prev = current.prev.next;
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
        checkIndex(index, size - 1);
        return findByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size - 1);
        Node<T> current = findByIndex(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size - 1);
        Node<T> current = findByIndex(index);
        return unlink(current);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (isEqual(object, current.item)) {
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

    private boolean isEqual(T object, T currentObject) {
        return object == currentObject || object != null && object.equals(currentObject);
    }

    private T unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
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
        T value = node.item;
        node.item = null;
        size--;
        return value;
    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private Node<T> findByIndex(int index) {
        Node<T> current;
        if (index > size / 2) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
        current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
