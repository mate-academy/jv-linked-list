package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        private T data;
        private Node next;
        private Node prev;

        Node(T data, Node prev, Node next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node lastNode = tail;
        Node newNode = new Node(value, lastNode, null);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            Node lastNode = tail;
            Node newNode = new Node(value, lastNode, null);
            tail = newNode;
            if (lastNode == null) {
                head = newNode;
            } else {
                lastNode.next = newNode;
            }
        } else {
            Node nodeAtIndex = head;
            for (int i = 0; i < index; i++) {
                nodeAtIndex = nodeAtIndex.next;
            }
            Node prev = nodeAtIndex.prev;
            Node newNode = new Node(value, prev, nodeAtIndex);
            nodeAtIndex.prev = newNode;
            if (prev == null) {
                head = newNode;
            } else {
                prev.next = newNode;
            }
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
        checkIndex(index);
        Node nodeAtIndex = head;
        for (int i = 0; i < index; i++) {
            nodeAtIndex = nodeAtIndex.next;
        }
        return nodeAtIndex.data;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node nodeAtIndex = head;
        for (int i = 0; i < index; i++) {
            nodeAtIndex = nodeAtIndex.next;
        }
        T oldValue = nodeAtIndex.data;
        nodeAtIndex.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node nodeAtIndex = head;
        for (int i = 0; i < index; i++) {
            nodeAtIndex = nodeAtIndex.next;
        }
        return unlink(nodeAtIndex);
    }

    @Override
    public boolean remove(T object) {
        Node current = head;
        while (current != null) {
            if (object == null ? current.data == null : object.equals(current.data)) {
                unlink(current);
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

    private T unlink(Node node) {
        final T element = node.data;
        Node next = node.next;
        Node prev = node.prev;

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

        node.data = null;
        size--;
        return element;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
