package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndexForAddition(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode;
        if (index == 0) {
            newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> previousToNew = findNodeAtIndex(index - 1);
            newNode = new Node<>(previousToNew, value, previousToNew.next);
            previousToNew.next.prev = newNode;
            previousToNew.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        Node<T> node = findNodeAtIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> node = findNodeAtIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> node = findNodeAtIndex(index);
        return unlink(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if ((node.value == null && object == null)
                    || (node.value != null && node.value.equals(object))) {
                unlink(node);
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

    private Node<T> findNodeAtIndex(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unlink(Node<T> node) {
        final T element = node.value;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;
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

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index " + index
                + " is out of bounds for the current list size");
        }
    }

    private void validateIndexForAddition(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index " + index
                + " is out of bounds for the current list size");
        }
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
