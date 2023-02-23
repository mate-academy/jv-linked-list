package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index != size) {
            indexValidation(index);
        }
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
        for (T listElement : list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T removedValue = currentNode.value;
        currentNode.value = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (Objects.equals(currentNode.value, object)) {
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

    private void indexValidation(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index value."
                    + " No such index or index is less than 0");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        indexValidation(index);
        return (index >= size / 2) ? countFromTail(index) : countFromHead(index);
    }

    private Node<T> countFromHead(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> countFromTail(int index) {
        Node<T> currentNode = tail;
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private void unlink(Node<T> removedNode) {
        size--;
        if (removedNode.prev == null
                && removedNode.next == null) {
            head = null;
            tail = null;
            return;
        }
        if (removedNode.prev == null) {
            removedNode.next.prev = null;
            head = removedNode.next;
            return;
        }
        if (removedNode.next == null) {
            removedNode.prev.next = null;
            tail = removedNode.prev;
            return;
        }
        removedNode.prev.next = removedNode.next;
        removedNode.next.prev = removedNode.prev;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev,T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
