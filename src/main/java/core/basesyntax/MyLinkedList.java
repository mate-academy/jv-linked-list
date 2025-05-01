package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    private final String exceptionMessage = "The index %s"
            + " is not valid for size: %d";

    @Override
    public void add(T value) {
        final Node<T> lastNode = tail;
        final Node<T> newNode = new Node<>(lastNode, value, null);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndexValidity(index);
        Node<T> newNext = getElement(index);
        Node<T> newPref = newNext.prev;
        Node<T> newNode = new Node<>(newPref, value, newNext);
        newNext.prev = newNode;
        if (newPref == null) {
            head = newNode;
        } else {
            newPref.next = newNode;
        }
        size++;
    }

    private Node<T> getElement(int index) {
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

    @Override
    public void addAll(List<T> list) {
        for (T obj : list) {
            add(obj);
        }
    }

    @Override
    public T get(int index) {
        checkIndexValidity(index);
        return getElement(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexValidity(index);
        Node<T> node = getElement(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexValidity(index);
        return unlink(getElement(index)).value;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.value == object || (object != null
                    && object.equals(node.value))) {
                unlink(node);
                return true;
            }
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

    private void checkIndexValidity(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format(exceptionMessage, index, size));
        }
    }

    private Node<T> unlink(Node<T> node) {
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
        size--;
        return node;
    }

    private static class Node<T> {
        private T value;
        Node<T> prev;
        Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
