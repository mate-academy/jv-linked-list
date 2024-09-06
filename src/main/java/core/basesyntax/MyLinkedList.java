package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if ((index == size || size == 0) && index >= 0) {
            add(value);
        } else {
            Node<T> node = findNodeAtIndex(index);
            if (node.equals(head)) {
                node.prev = new Node<T>(null, value, node);
                head = node.prev;
                size++;
            } else {
                node.prev.next = new Node<T>(node.prev, value, node);
                size++;
            }
        }

    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Node<T> node = findNodeAtIndex(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        T oldValue;
        Node<T> node = findNodeAtIndex(index);
        oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = findNodeAtIndex(index);
        T removedValue;
        if (size == 1) {
            removedValue = head.item;
            head = null;
            tail = null;
        } else {
            if (node.equals(head)) {
                removedValue = head.item;
                head = head.next;
                head.prev = null;
            } else {
                if (node.equals(tail)) {
                    removedValue = tail.item;
                    tail = tail.prev;
                    tail.next = null;
                } else {
                    removedValue = node.item;
                    node.next.prev = node.prev;
                    node.prev.next = node.next;
                }
            }
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        int index = 0;
        found: {
                for (; index < size; index++) {
                    if (node.item == null) {
                        if (object == null) {
                            break found;
                        }
                    } else {
                        if (node.item.equals(object)) {
                            break found;
                        }
                    }
                    if (node.next != null) {
                        node = node.next;
                    }
                }
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> findNodeAtIndex(int index) {
        validIndexCheck(index);
        Node<T> node = head;
        if (index == size - 1) {
            return tail;
        }
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void validIndexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
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


