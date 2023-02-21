package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int currentSize;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (currentSize == 0) {
            addFirstNode(value);
            return;
        }
        Node<T> node = new Node<>(tail, value, null);
        tail.next = node;
        tail = node;
        currentSize++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > currentSize) {
            throw new IndexOutOfBoundsException();
        } else if (currentSize == index) {
            add(value);
            return;
        } else if (index == 0) {
            addNodeToZeroPosition(value);
            return;
        }
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        currentSize++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexBounds(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexBounds(index);
        Node<T> currentNode = getNodeByIndex(index);
        T temp = currentNode.value;
        currentNode.value = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        checkIndexBounds(index);
        if (index == 0 && currentSize == 1) {
            T tempToReturn = head.value;
            unlink();
            return tempToReturn;
        }
        Node<T> currentNode = getNodeByIndex(index);
        remove(currentNode.value);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        if (equals(head.value, object) && currentSize == 1) {
            unlink();
            return true;
        } else if (equals(head.value, object)) {
            headNodeRemote();
            return true;
        }
        while (currentNode != null) {
            if (equals(currentNode.value, object)) {
                currentNode.prev.next = currentNode.next;
                currentNode.next.prev = currentNode.prev;
                currentSize--;
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    private void checkIndexBounds(int index) {
        if (index < 0 || index >= currentSize) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void addFirstNode(T value) {
        Node<T> node = new Node<>(null, value, null);
        head = node;
        tail = node;
        currentSize++;
    }

    private void addNodeToZeroPosition(T value) {
        Node<T> node = new Node<>(null, value, head);
        head.prev = node;
        head = node;
        currentSize++;
    }

    private void unlink() {
        tail.next = null;
        tail.prev = null;
        tail.value = null;
        head = tail;
        currentSize--;
    }

    private void headNodeRemote() {
        head.next.prev = null;
        head = head.next;
        currentSize--;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = currentSize / 2 < index ? head : tail;
        if (currentNode == head) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            for (int i = currentSize - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private boolean equals(T value, T object) {
        return Objects.equals(value, object);
    }

    private class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
