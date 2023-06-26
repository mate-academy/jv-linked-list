package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
        }
        tail = newNode;
        size++;
    }

    public void add(T value, int index) {
        validateIndexForAdd(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(value);
            Node<T> nodeAtIndex = getNode(index);
            linkBefore(newNode, nodeAtIndex);
            size++;
        }
    }

    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    public T get(int index) {
        validateIndex(index);
        Node<T> nodeAtIndex = getNode(index);
        return nodeAtIndex.value;
    }

    public T set(T value, int index) {
        validateIndex(index);
        Node<T> nodeAtIndex = getNode(index);
        T oldValue = nodeAtIndex.value;
        nodeAtIndex.value = value;
        return oldValue;
    }

    public T remove(int index) {
        validateIndex(index);
        Node<T> nodeAtIndex = getNode(index);
        unlink(nodeAtIndex);
        size--;
        return nodeAtIndex.value;
    }

    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.value, object)) {
                unlink(current);
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private void validateIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void unlink(Node<T> node) {
        Node<T> prev = node.previous;
        Node<T> next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.previous = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.previous = prev;
            node.next = null;
        }
    }

    private void linkBefore(Node<T> newNode, Node<T> node) {
        Node<T> prev = node.previous;
        newNode.next = node;
        newNode.previous = prev;
        node.previous = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        private Node(T value) {
            this.value = value;
        }
    }
}
