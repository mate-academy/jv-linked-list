package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
        } else if (size == 1) {
            Node<T> newNode = new Node<>(head, value, null);
            head.next = newNode;
            tail = newNode;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> indexNode = findNodeByIndex(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node<T> newNode = new Node<>(indexNode.prev, value, indexNode);
        indexNode.prev.next = newNode;
        indexNode.prev = newNode;
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
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> indexNode = findNodeByIndex(index);
        T oldValue = indexNode.item;
        indexNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedElement = findNodeByIndex(index);
        unlink(removedElement);
        return removedElement.item;
    }

    @Override
    public boolean remove(T object) {
        int index = findIndexByValue(object);
        if (index == -1) {
            return false;
        }
        unlink(findNodeByIndex(index));
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> findNodeByIndex(int index) {
        indexCheck(index);
        if (size / 2 <= index) {
            Node<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        }
        Node<T> node = tail;
        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }
        return node;
    }

    private int findIndexByValue(T value) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (value == node.item || value != null && value.equals(node.item)) {
                return i;
            }
            node = node.next;
        }
        return -1;
    }

    private void unlink(Node<T> node) {
        if (node.equals(head)) {
            head = head.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.equals(tail)) {
            tail = tail.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for length " + size);
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
