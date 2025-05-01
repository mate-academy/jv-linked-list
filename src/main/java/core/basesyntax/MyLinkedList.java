package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = tail = newNode;
            size++;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size()) {
            add(value);
            return;
        }
        Node<T> node = findElementByIndex(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        if (newNode.prev == null) {
            head = newNode;
        } else {
            newNode.prev.next = newNode;
        }
        node.prev = newNode;
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
        return findElementByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findElementByIndex(index);
        T node = currentNode.item;
        currentNode.item = value;
        return node;
    }

    @Override
    public T remove(int index) {
        return unlink(findElementByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        if (currentNode.item.equals(object)) {
            head = currentNode.next;
            size--;
            return true;
        }
        for (int i = 0; i < size(); i++) {
            if (currentNode.item == object || currentNode.item != null
                    && currentNode.item.equals(object)) {
                unlink(currentNode);
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

    private Node<T> findElementByIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " must be less than " + size());
        }
        Node<T> fromHead = head;
        Node<T> fromTail = tail;
        int halfSize = size() / 2;
        int lastElementIndex = size() - 1;
        if (index < halfSize) {
            for (int i = 0; i < index; i++) {
                fromHead = fromHead.next;
            }
            return fromHead;
        } else {
            for (int i = lastElementIndex; i > index; i--) {
                fromTail = fromTail.prev;
            }
            return fromTail;
        }
    }

    private T unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
        return node.item;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(T item) {
            this.item = item;
        }

        private Node(Node prev, T item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
