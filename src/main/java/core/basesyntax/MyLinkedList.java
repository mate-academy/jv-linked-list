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
            head = newNode;
        } else {
            tail.next = newNode;
        }
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
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            addInMiddle(index,value);
        }
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
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T replacedValue = findNodeByIndex(index).item;
        findNodeByIndex(index).item = value;
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = findNodeByIndex(index);
        T removedValue = current.item;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            unlink(index, current);
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == object
                    || currentNode.item != null && currentNode.item.equals(object)) {
                remove(i);
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.next = next;
            this.item = item;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is invalid for size " + size);
        }
    }

    private void addInMiddle(int index, T value) {
        checkIndex(index);
        Node<T> currentNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i != index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i != index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    public void unlink(int index, Node<T> current) {
        if (index == 0) {
            current.next.prev = null;
            head.prev = null;
            head = current.next;
        } else if (index == size - 1) {
            current.prev.next = null;
            tail = current.prev;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
    }
}
