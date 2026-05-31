package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirstEl(value);
            return;
        }
        Node<T> newNode = new Node<>(tail, value, null);
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
        if (index == 0) {
            addElTo0Pos(value);
            return;
        }
        checkIndex(index);
        Node<T> currentNode = searchNodeByIndex(index);
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
        checkIndex(index);
        return searchNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = searchNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T value;
        if (index == 0 && size == 1) {
            value = head.value;
            unlinkLastNode();
            return value;
        }
        if (index == 0) {
            value = head.value;
            unlinkFromHead();
            return value;
        }
        if (index == size - 1) {
            value = tail.value;
            unlinkFromTail();
            return value;
        }
        Node<T> currentNode = searchNodeByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = searchNodeByValue(object);
        if (currentNode == null) {
            return false;
        }
        if (currentNode == head && currentNode == tail) {
            unlinkLastNode();
            return true;
        }
        if (currentNode == head) {
            unlinkFromHead();
            return true;
        }
        if (currentNode == tail) {
            unlinkFromTail();
            return true;
        }
        unlink(currentNode);
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

    private void addFirstEl(T value) {
        Node<T> node = new Node<>(null, value, null);
        head = tail = node;
        size = 1;
    }

    private void addElTo0Pos(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        head.prev = newNode;
        head = newNode;
        size++;
    }

    private Node<T> searchNodeFromHead(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> searchNodeFromTail(int index) {
        Node<T> currentNode = tail;
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private Node<T> searchNodeByIndex(int index) {
        if (index <= size / 2) {
            return searchNodeFromHead(index);
        }
        return searchNodeFromTail(index);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> searchNodeByValue(T value) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (compareValues(currentNode.value, value)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private boolean compareValues(T currentValue, T value) {
        if (currentValue == null) {
            return value == null;
        }
        return currentValue.equals(value);
    }

    private void unlinkFromHead() {
        head.prev = null;
        head = head.next;
        size--;
    }

    private void unlinkFromTail() {
        tail.prev.next = null;
        tail = tail.prev;
        size--;
    }

    private void unlink(Node<T> currentNode) {
        currentNode.next.prev = currentNode.prev;
        currentNode.prev.next = currentNode.next;
        size--;
    }

    private void unlinkLastNode() {
        head = tail = null;
        size = 0;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
