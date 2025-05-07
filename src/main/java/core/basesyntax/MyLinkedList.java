package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        verifyIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = tail = newNode;
        }
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
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> necessaryNode = findNodeByIndex(index);
        T removedObject = necessaryNode.item;
        necessaryNode.item = value;
        return removedObject;
    }

    @Override
    public T remove(int index) {
        Node<T> current = findNodeByIndex(index);
        unlink(current);
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        int index = findNodeIndexByItem(object);
        if (index < 0) {
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
        return size == 0;
    }

    private void verifyIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds: 0 - " + size);
        }
    }

    private void verifyIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds: 0 - " + size);
        }
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

    private Node<T> findNodeByIndex(int index) {
        verifyIndex(index);
        Node<T> lookUpNode;
        if (index < (size >> 1)) {
            lookUpNode = head;
            for (int i = 0; i < index; i++) {
                lookUpNode = lookUpNode.next;
            }
        } else {
            lookUpNode = tail;
            for (int i = size - 1; i > index; i--) {
                lookUpNode = lookUpNode.prev;
            }
        }
        return lookUpNode;
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
