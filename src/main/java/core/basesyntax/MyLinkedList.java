package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        linkTail(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            linkHead(value);
            return;
        }
        if (index == size) {
            linkTail(value);
            return;
        }
        Node<T> currentNode = findNode(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        Node<T> currentNode = findNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        T returnValue = get(index);
        if (index == 0) {
            head.next.prev = newNode;
            newNode.next = head.next;
            head = newNode;
        } else {
            Node<T> finderNode = findNode(index);
            finderNode.next.prev = newNode;
            finderNode.prev.next = newNode;
        }

        return returnValue;
    }

    @Override
    public T remove(int index) {
        Node<T> redundantNode = findNode(index);
        unlink(redundantNode);
        return redundantNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> redundantNode = head;
        while (redundantNode != null) {
            if (redundantNode.value == object
                    || redundantNode.value != null && redundantNode.value.equals(object)) {
                unlink(redundantNode);
                return true;
            }
            redundantNode = redundantNode.next;
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

    private Node<T> findNode(int index) {
        checkIndex(index);
        Node<T> finderNode;
        if (index <= (double) size / 2) {
            finderNode = head;
            for (int i = 0; i < index; i++) {
                finderNode = finderNode.next;
            }
        } else {
            index++;
            finderNode = tail;
            for (int i = size; i > index; i--) {
                finderNode = finderNode.prev;
            }
        }
        return finderNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Bad index");
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            if (size != 1) {
                head = node.next;
            }
        } else if (node.next == null) {
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void linkHead(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
            newNode.next.prev = newNode;
        }
        size++;
    }

    private void linkTail(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private final class Node<T> {
        private final T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
