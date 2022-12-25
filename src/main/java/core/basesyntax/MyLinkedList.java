package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        }
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkBounds(index);
        Node<T> node = getNodeByIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            addFirst(newNode);
            return;
        }
        Node<T> prevNode = node.prev;
        newNode.next = node;
        newNode.prev = prevNode;
        prevNode.next = newNode;
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
        checkBounds(index);
        Node<T> node = getNodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkBounds(index);
        Node<T> node = getNodeByIndex(index);
        T lastValue = node.value;
        node.value = value;
        return lastValue;
    }

    @Override
    public T remove(int index) {
        checkBounds(index);
        Node<T> node = getNodeByIndex(index);
        T value = node.value;
        unlink(node);
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.value
                    || (object != null && object.equals(currentNode.value))) {
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

    private void unlink(Node<T> node) {
        if (node == head || (node != null && node.equals(head))) {
            removeFirst();
            return;
        }
        if (node == tail || (node != null && node.equals(tail))) {
            removeLast();
            return;
        }
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        nextNode.prev = prevNode;
        prevNode.next = nextNode;
        size--;
    }

    private void addFirst(Node<T> node) {
        head.prev = node;
        node.next = head;
        head = node;
        size++;
    }

    private void removeFirst() {
        Node<T> currentNode = head.next;
        currentNode.prev = null;
        head = currentNode;
        size--;
    }

    private void removeLast() {
        Node<T> currentNode = tail.prev;
        currentNode.next = null;
        tail = currentNode;
        size--;
    }

    private void checkBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index: " + index + " is out of bounds");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node;
        if (index >= (double) size / 2) {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        } else {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

}
