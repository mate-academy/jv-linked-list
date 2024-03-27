package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;

    private class Node {
        private T data;
        private Node prev;
        private Node next;

        public Node(T data) {
            this.data = data;
        }
    }

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
            newNode.prev = current;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative: " + index);
        }
        Node newNode = new Node(value);
        if (index == 0) {
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                if (current == null) {
                    throw new IndexOutOfBoundsException("Index out of bounds: " + index);
                }
                current = current.next;
            }
            if (current == null) {
                throw new IndexOutOfBoundsException("Index out of bounds: " + index);
            }
            newNode.next = current.next;
            if (current.next != null) {
                current.next.prev = newNode;
            }
            current.next = newNode;
            newNode.prev = current;
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
        Node current = getNodeByIndex(index);
        return current.data;
    }

    @Override
    public T set(T value, int index) {
        Node current = getNodeByIndex(index);
        T removedData = current.data;
        current.data = value;
        return removedData;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index is out of bounds: " + index);
        }
        if (head == null) {
            throw new RuntimeException("Cannot remove from an empty list");
        }
        Node current = getNodeByIndex(index);
        if (current == head) {
            head = current.next;
        } else {
            unlink(current);
        }
        return current.data;
    }

    @Override
    public boolean remove(T object) {
        Node current = head;
        while (current != null) {
            if (object == null && current.data == null) {
                unlink(current);
                return true;
            } else if (object != null && object.equals(current.data)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node getNodeByIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index cannot be negative: " + index);
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            if (current == null) {
                throw new IndexOutOfBoundsException("Index out of bounds: " + index);
            }
            current = current.next;
        }
        if (current == null) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return current;
    }

    private void unlink(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
    }
}
