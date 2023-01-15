package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> temp = tail;
        Node<T> newNode = new Node<>(temp, value, null);
        tail = newNode;
        if (temp == null) {
            head = newNode;
        } else {
            temp.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        final Node<T> curNode = getNode(index);
        final Node<T> prevNode = curNode.prev;
        final Node<T> newNode = new Node<>(prevNode, value, curNode);
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        final Node<T> curNode = getNode(index);
        final T oldValue = curNode.value;
        curNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        final Node<T> curNode = getNode(index);
        final Node<T> next = curNode.next;
        final Node<T> prev = curNode.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return curNode.value;
    }

    @Override
    public boolean remove(T object) {
        final int index = getIndex(object);
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
        return (size == 0);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> curNode = head;
        for (int i = 0; i < index; i++) {
            curNode = curNode.next;
        }
        return curNode;
    }

    private int getIndex(T object) {
        Node<T> curNode = head;
        for (int i = 0; i < size; i++) {
            if (curNode.value == null ? object == null : curNode.value.equals(object)) {
                return i;
            }
            curNode = curNode.next;
        }
        return -1;
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
