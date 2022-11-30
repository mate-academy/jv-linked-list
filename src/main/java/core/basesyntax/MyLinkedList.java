package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
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
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> newNode = new Node<T>(currentNode.prev, value, currentNode);
        if (currentNode.prev == null) {
            head = newNode;
        } else {
            currentNode.prev.next = newNode;
        }
        currentNode.prev = newNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> replacedNode = getNodeByIndex(index);
        T oldValue = replacedNode.value;
        replacedNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getNodeByIndex(index);
        T removedItem = removedNode.value;
        unlink(removedNode);
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object || object != null && object.equals(currentNode.value)) {
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

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index < (size >> 1)) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: "
                    + index + " not found, current size: " + size);
        }
    }

    private void unlink(Node<T> removedNode) {
        if (removedNode == head) {
            head = removedNode.next;
        } else if (removedNode == tail) {
            tail = removedNode.prev;
        } else {
            removedNode.next.prev = removedNode.prev;
            removedNode.prev.next = removedNode.next;
        }
        size--;
    }
}
