package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value,null);
            tail = head;
        } else {
            Node<T> currentNode = new Node<>(tail, value, null);
            tail.next = currentNode;
            tail = currentNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        T temp = getNodeByIndex(index).item;
        getNodeByIndex(index).item = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T value = currentNode.item;
        unLink(currentNode);
        return value;
    }

    @Override
    public boolean remove(T object) {

        Node<T> currentNode = head;
        for (int i = 0; currentNode != null; i++) {
            if (compare(currentNode.item, object)) {
                unLink(currentNode);
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

    private void unLink(Node<T> currentNode) {
        if (currentNode.prev == null && currentNode.next == null) {
            tail = head = null;
        } else if (currentNode.next == null) {
            currentNode.item = null;
            currentNode = currentNode.prev;
            currentNode.next.prev = currentNode.next = null;
            tail = currentNode;
        } else if (currentNode.prev == null) {
            currentNode.item = null;
            currentNode = currentNode.next;
            currentNode.prev.next = currentNode.prev = null;
            head = currentNode;
        } else {
            currentNode.item = null;
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndexIsValid(index);
        return index > size / 2 ? startWithTail(index) : startWithHead(index);
    }

    private Node<T> startWithHead(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> startWithTail(int index) {
        Node<T> currentNode = tail;
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private boolean compare(T first, T second) {
        return first == second || first != null && first.equals(second);
    }

    private void checkIndexIsValid(int index) {
        if (!indexIsValid(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is not valid");
        }
    }

    private boolean indexIsValid(int index) {
        return index < size && index >= 0;
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
