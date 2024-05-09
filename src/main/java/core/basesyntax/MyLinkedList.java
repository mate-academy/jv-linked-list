package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
            size++;
            return;
        }
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        verifyIndex(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> lookUpNode = findNodeByIndex(index);
            lookUpNode.prev.next = newNode;
            newNode.prev = lookUpNode.prev;
            newNode.next = lookUpNode;
            lookUpNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds: 0 - " + size);
        }
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds: 0 - " + size);
        } else {
            T removedObject = findNodeByIndex(index).item;
            findNodeByIndex(index).item = value;
            return removedObject;
        }
    }

    @Override
    public T remove(int index) {
        verifyIndex(index);
        if (index == size) {
            throw new IndexOutOfBoundsException();
        }
        T value = findNodeByIndex(index).item;
        Node<T> lookUpNode = findNodeByIndex(index);
        if (index == 0) {
            if (size == 1) {
                lookUpNode.item = null;
                size--;
                return value;
            }
            lookUpNode.next.prev = null;
            head = lookUpNode.next;
            size--;
            return value;
        }
        if (index == size - 1) {
            lookUpNode.prev.next = null;
            tail = lookUpNode.prev;
            size--;
            return value;
        } else {
            lookUpNode.prev.next = lookUpNode.next;
            lookUpNode.next.prev = lookUpNode.prev;
            size--;
            return value;
        }
    }

    @Override
    public boolean remove(T object) {
        int index = findNodeIndexByItem(object);
        if (index < 0) {
            return false;
        } else {
            remove(index);
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void verifyIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds: 0 - " + size);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        verifyIndex(index);
        Node<T> lookUpNode = head;
        for (int i = 0; i < index; i++) {
            lookUpNode = lookUpNode.next;
        }
        return lookUpNode;
    }

    private Node<T> findNodeByItem(T lookUpItem) {
        Node<T> lookUpNode = head;
        for (int i = 0; i < size; i++) {
            if (lookUpNode.item == lookUpItem) {
                return lookUpNode;
            }
            lookUpNode = lookUpNode.next;
        }
        return null;
    }

    private int findNodeIndexByItem(T lookUpItem) {
        Node<T> lookUpNode = head;
        for (int i = 0; i < size; i++) {
            if (lookUpNode.item == null && lookUpItem == null
                    || Objects.equals(lookUpNode.item, lookUpItem)) {
                return i;
            }
            lookUpNode = lookUpNode.next;
        }
        return -1;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
