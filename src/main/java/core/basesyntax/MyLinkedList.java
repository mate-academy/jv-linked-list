package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> currentNode;
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

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

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        if (index == 0) {
            addFirst(value);
            return;
        }
        if (index == size) {
            addLast(value);
            return;
        }
        addMiddle(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            addLast(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size);
        return (T) findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> currentNode = findNode(index);
        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> currentNode = findNode(index);
        T removeValue = currentNode.item;
        unlink(currentNode);
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (object == currentNode.item || object != null && object.equals(currentNode.item)) {
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

    private void addFirst(T value) {
        Node<T> newNode = new Node<T>(null, value, head);
        size++;
        head = newNode;
        if (newNode.next != null) {
            newNode.next.prev = newNode;
        }
        if (tail == null) {
            tail = newNode;
        }
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<T>(tail, value, null);
        size++;
        tail = newNode;
        if (newNode.prev != null) {
            newNode.prev.next = newNode;
        }
        if (head == null) {
            head = newNode;
        }
    }

    private void addMiddle(T value, int index) {
        Node<T> currentNode = findNode(index);
        Node<T> newNode = new Node<T>(currentNode.prev, value, currentNode);
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
        size++;
    }

    private void unlink(Node node) {
        if (node == head && node == tail) {
            head = null;
            tail = null;
            size--;
            return;
        }
        if (node == head) {
            head = node.next;
            head.prev = null;
            size--;
            return;
        }
        if (node == tail) {
            tail = node.prev;
            tail.next = null;
            size--;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    private void checkIndex(int index, int size) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is invalid." + index);
        }
    }

    private Node findNode(int index) {
        checkIndex(index, size);
        if (index >= size / 2) {
            Node node = tail;
            for (int i = size - 1; i >= size / 2; i--) {
                if (i == index) {
                    return node;
                }
                node = node.prev;
            }
        }
        Node node = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return node;
            }
            node = node.next;
        }
        return null;
    }
}

