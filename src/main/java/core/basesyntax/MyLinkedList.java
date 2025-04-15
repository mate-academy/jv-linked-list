package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private class Node {
        T value;
        Node next;
        Node prev;

        Node(T value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);

        if (index == size) {
            add(value);
            return;
        }

        Node newNode = new Node(value);
        Node current = getNode(index);

        if (current.prev == null) {
            newNode.next = current;
            current.prev = newNode;
            head = newNode;
        } else {
            Node prevNode = current.prev;
            prevNode.next = newNode;
            newNode.prev = prevNode;
            newNode.next = current;
            current.prev = newNode;
        }

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
        checkElementIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node current = getNode(index);
        T old = current.value;
        current.value = value;
        return old;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node toRemove = getNode(index);
        return unlink(toRemove);
    }

    @Override
    public boolean remove(T object) {
        Node current = head;
        while (current != null) {
            if ((object == null && current.value == null) ||
                    (object != null && object.equals(current.value))) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private T unlink(Node node) {
        T value = node.value;
        Node prev = node.prev;
        Node next = node.next;

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

        node.value = null; // for GC
        size--;
        return value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node getNode(int index) {
        checkElementIndex(index);
        Node current;
        if (index < (size / 2)) {
            current = head;
            for (int i = 0; i < index; i++) current = current.next;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) current = current.prev;
        }
        return current;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }
}