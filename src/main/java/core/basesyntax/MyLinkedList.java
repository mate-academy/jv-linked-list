package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (isIndexOutForAddMethod(index)) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> current = getNode(index);
            newNode.next = current.next;
            current.next = newNode;
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
        if (isIndexOutForOtherMethods(index)) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> current = getNodeForSetAndGetMethods(index);

        return current.value;
    }

    @Override
    public T set(T value, int index) {
        if (isIndexOutForOtherMethods(index)) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> current = getNodeForSetAndGetMethods(index);

        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (isIndexOutForOtherMethods(index)) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            T removedValue = head.value;
            head = head.next;
            size--;
            return removedValue;
        } else {
            Node<T> current = getNode(index);

            if (current != null && current.next != null) {
                T removedValue = current.next.value;
                current.next = current.next.next;
                size--;
                return removedValue;

            } else {
                throw new NoSuchElementException("Element not found");
            }
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        Node<T> prev = null;

        while (current != null) {
            if ((object == null && current.value == null)
                    || (object != null && object.equals(current.value))) {
                if (prev == null) {
                    head = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
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

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }

    private boolean isIndexOutForAddMethod(int index) {
        return index < 0 || index > size;
    }

    private boolean isIndexOutForOtherMethods(int index) {
        return index < 0 || index >= size;
    }

    private Node<T> getNodeForSetAndGetMethods(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> getNode(int index) {
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        return current;
    }
}
