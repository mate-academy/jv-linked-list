package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node first;
    private Node last;
    private int size;

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Value -" + value + "with index - " + index
                    + "cannot be added");
        }
        if (index == size) {
            addLast(value);
        } else if (index == 0) {
            addFirst(value);
        } else {
            Node newNode = new Node(value);
            Node current = getNode(index);
            Node prevNode = current.prev;
            newNode.prev = prevNode;
            newNode.next = current;
            prevNode.next = newNode;
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            addLast(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndexRange(index);
        return (T) getNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        checkIndexRange(index);
        Node node = getNode(index);
        T oldValue = (T) node.data;
        node.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexRange(index);
        Node node = getNode(index);
        final T removedValue = (T) node.data;
        if (node == first) {
            first = node.next;
        }
        if (node == last) {
            last = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node current = first;
        while (current != null) {
            if (current.data == object || current.data != null && current.data.equals(object)) {
                removeNode(current);
                return true;
            }
            current = current.next;
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
        Node newNode = new Node(value);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }
        size++;
    }

    private void addLast(T value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    private Node getNode(int index) {
        Node current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void removeNode(Node node) {
        if (node == first) {
            first = node.next;
        }
        if (node == last) {
            last = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        size--;
    }

    private static class Node<T> {
        private T data;
        private Node prev;
        private Node next;

        Node(T data) {
            this.data = data;
        }
    }

    private void checkIndexRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Сannot remove the element with the specified"
                    + " index - " + index);
        }
    }
}
