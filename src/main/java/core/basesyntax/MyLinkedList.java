package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (size == 0) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> node = getNode(index);
            Node<T> newNode = new Node<>(node.prev, value, node);
            node.prev.next = newNode;
            node.prev = newNode;

        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T valueToRemove = node.item;
        node.item = value;
        return valueToRemove;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        if (size == 1) {
            head = null;
            tail = null;
        } else if (index == size - 1) {
            node.prev.next = null;
            tail = node.prev;
        } else if (index == 0) {
            node.next.prev = null;
            head = node.next;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return node.item;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if (getNode(i).item == t || getNode(i).item != null && getNode(i).item.equals(t)) {
                remove(i);
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

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of range, size of array is  "
                    + size);
        }
    }
}
