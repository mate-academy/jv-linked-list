package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (size == 0) {
            head = node;
        } else {
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
        checkIndex(index);
        if (index == 0) {
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
        } else {
            Node<T> current = getNode(index);
            Node<T> node = new Node<>(current.prev, value, current);
            current.prev.next = node;
            current.prev = node;
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
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNode(index);
        T removedValue = current.value;
        unlink(current);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (current.value == object
                    || (current.value != null && current.value.equals(object))) {
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Your index " + index + " is not valid.");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
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

    private void unlink(Node<T> removedElement) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (removedElement == head) {
            head.next.prev = null;
            head = removedElement.next;
        } else if (removedElement == tail) {
            tail.prev.next = null;
            tail = removedElement.prev;
        } else {
            removedElement.next.prev = removedElement.prev;
            removedElement.prev.next = removedElement.next;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
