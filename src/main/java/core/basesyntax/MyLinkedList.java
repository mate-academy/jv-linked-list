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
        Node<T> newNode = new Node<>(value);
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " must be less than " + size());
        }
        if (head == null) {
            head = tail = newNode;
            size++;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
        } else if (index == size()) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            size++;
        } else {
            Node<T> currentNode = findElementByIndex(index);
            newNode.next = currentNode;
            newNode.prev = currentNode.prev;
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
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
        checkIndexValue(index);
        return findElementByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexValue(index);
        Node<T> currentNode = findElementByIndex(index);
        T node = currentNode.item;
        currentNode.item = value;
        return node;
    }

    @Override
    public T remove(int index) {
        T removedElement;
        checkIndexValue(index);
        if (index == 0) {
            removedElement = head.item;
            head = head.next;
            if (head == null) {
                tail = null;
            }
            size--;
            return removedElement;
        }
        if (index == size - 1) {
            removedElement = tail.item;
            tail = tail.prev;
            size--;
            return removedElement;
        } else {
            Node<T> currentNode = findElementByIndex(index);
            removedElement = currentNode.item;
            unlink(currentNode);
            return removedElement;
        }
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

    private void checkIndexValue(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " must be less than " + size());
        }
    }

    private Node<T> findElementByIndex(int index) {
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

    private void unlink(Node<T> currentNode) {
        currentNode.prev.next = currentNode.next;
        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        } else {
            tail = currentNode.prev;
            tail.next = null;
        }
        size--;
    }

    private class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(T item) {
            this.item = item;
        }
    }
}
