package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            linkHead(value);
        } else {
            linkNewNode(value, size);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            linkHead(value);
        } else {
            checkIndexToAdd(index);
            linkNewNode(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNode(index);
        T value = node.item;
        unlink(node);
        return value;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = head; x != null; x = x.next) {
            if (x.item == object || x.item != null && x.item.equals(object)) {
                unlink(x);
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

    private void linkHead(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    private void linkNewNode(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        if (index == size) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> nodeAtIndex = findNode(index);
            newNode.prev = nodeAtIndex.prev;
            newNode.prev.next = newNode;
            newNode.next = nodeAtIndex;
            nodeAtIndex.prev = newNode;
        }
        size++;
    }

    private Node<T> findNode(int index) {
        Node<T> node;
        if (index < (size >> 1)) {
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
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private void checkIndexToAdd(int index) {
        if (index <= 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private void unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
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
        size--;
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
