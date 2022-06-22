package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(tail, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkingIndex(index);
        Node<T> nodeOfIndex = getNodeOfIndex(index);
        Node<T> prevNode = nodeOfIndex.prev;
        Node<T> newNode = new Node<>(prevNode, value, nodeOfIndex);
        nodeOfIndex.prev = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkingIndex(index);
        return getNodeOfIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkingIndex(index);
        T valueForReturn = getNodeOfIndex(index).value;
        getNodeOfIndex(index).value = value;
        return valueForReturn;
    }

    @Override
    public T remove(int index) {
        checkingIndex(index);
        return unlink(getNodeOfIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; currentNode != null; i++) {
            if (Objects.equals(object, currentNode.value)) {
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

    private int getIndex(T value) {
        Node<T> currentNode = head;
        int index = 0;
        while (currentNode != value) {
            currentNode = currentNode.next;
            index++;
        }
        return -1;
    }

    private void checkingIndex(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is invalid for size " + size);
        }
    }

    private Node<T> getNodeOfIndex(int index) {
        Node<T> buffer = head;
        for (int i = 0; i < index; i++) {
            buffer = buffer.next;
        }
        return buffer;
    }

    private T unlink(Node<T> node) {
        final T removableNode = node.value;
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        size--;
        return removableNode;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
