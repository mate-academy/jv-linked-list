package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);

        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexExclusive(index);

        Node<T> newNode = new Node<>(head, value, tail);

        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> currentNode = getNodeAtIndex(index);
            newNode.next = currentNode;
            newNode.prev = currentNode.prev;
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }

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
        checkIndexInclusive(index);

        Node<T> currentNode = getNodeAtIndex(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexInclusive(index);

        Node<T> currentNode = getNodeAtIndex(index);

        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexInclusive(index);

        if (index == 0) {
            return removeFirst();
        }

        if (index == size - 1) {
            return removeLast();
        }

        Node<T> currentNode = getNodeAtIndex(index);
        return removeNode(currentNode) ? currentNode.item : null;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;

        while (currentNode != null) {
            if ((currentNode.item == object)
                    || currentNode.item != null && currentNode.item.equals(object)) {
                return removeNode(currentNode);
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

    private Node<T> getNodeAtIndex(int index) {
        Node<T> currentNode = head;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void checkIndexInclusive(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds " + index);
        }
    }

    private void checkIndexExclusive(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds " + index);
        }
    }

    private T removeFirst() {
        if (head == null) {
            return null;
        }

        final Node<T> removed = head;
        head = head.next;

        if (head != null) {
            head.prev = null;
        }

        size--;
        return removed.item;
    }

    private T removeLast() {
        if (tail == null) {
            return null;
        }

        final Node<T> removed = tail;
        tail = tail.prev;

        if (tail != null) {
            tail.next = null;
        }

        size--;
        return removed.item;
    }

    private boolean removeNode(Node<T> currentNode) {
        if (currentNode == head) {
            return removeFirst() != null;
        } else if (currentNode == tail) {
            return removeLast() != null;
        } else {
            unlink(currentNode);
            size--;
            return true;
        }
    }

    private void unlink(Node<T> currentNode) {
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.item = value;
            this.next = next;
        }
    }
}
