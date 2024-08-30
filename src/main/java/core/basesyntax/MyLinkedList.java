package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, true);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = getNodeByIndex(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        if (current.prev == null) {
            head = newNode;
        } else {
            current.prev.next = newNode;
        }
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
        Node<T> current = getNodeByIndex(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, false);
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        Node<T> current = getNodeByIndex(index);

        if (current.prev == null) {
            head = current.next;
        } else {
            current.prev.next = current.next;
        }

        if (current.next == null) {
            tail = current.prev;
        } else {
            current.next.prev = current.prev;
        }

        size--;
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if ((currentNode.item == object)
                    || (currentNode.item != null && currentNode.item.equals(object))) {
                if (currentNode.prev == null) {
                    head = currentNode.next;
                } else {
                    currentNode.prev.next = currentNode.next;
                }

                if (currentNode.next == null) {
                    tail = currentNode.prev;
                } else {
                    currentNode.next.prev = currentNode.prev;
                }

                size--;
                return true;
            }
            currentNode = currentNode.next;
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

    private void checkIndex(int index, boolean allowSize) {
        if (index < 0 || index > size || (!allowSize && index == size)) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }
}
