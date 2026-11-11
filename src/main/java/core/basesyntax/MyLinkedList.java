package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node head = null;
    private Node tail = null;
    private int size;

    public MyLinkedList() {
        size = 0;
    }


    @Override
    public void add(T value) {
        Node newNode = new Node(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    public void addFirst(T value) {
        Node newNode = new Node(null, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    public void addLast(T value) {
        Node newNode = new Node(null, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;

        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public T getFirst() {
        return head == null ? null : head.data;
    }

    public T getLast() {
        return tail == null ? null : tail.data;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            addFirst(value);
            return;
        }
        if (index == size) {
            addLast(value);
            return;
        }
        Node currentNode = findNodeByIndex(index);
        Node prevNode = currentNode.prev;
        Node newNode = new Node(prevNode, value, currentNode);
        prevNode.next = newNode;
        currentNode.prev = newNode;
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
        return findNodeByIndex(index).data;
    }

    private Node findNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node current = null;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;

            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    @Override
    public T set(T value, int index) {
        Node current = findNodeByIndex(index);
        T oldValue = current.data;
        current.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node current = findNodeByIndex(index);
        T oldValue = current.data;
        unlink(current);
        return oldValue;
    }

    private void unlink(Node node) {
        Node previous = node.prev;
        Node next = node.next;
        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
        }
        if (next == null) {
            tail = previous;
        } else {
            next.prev = previous;
        }
        node.prev = null;
        node.next = null;
        size--;
    }

    @Override
    public boolean remove(T object) {
        Node current = head;
        while (current != null) {
            boolean match = object == null ? current.data == null : object.equals(current.data);
            if (match) {
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

    private class Node {
        private T data;
        private Node next;
        private Node prev;

        public Node(Node prev, T data, Node next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}
