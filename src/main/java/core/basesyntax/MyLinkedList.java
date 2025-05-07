package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T data) {
        addLast(data);
    }

    @Override
    public void add(T data, int index) {
        if (index == size) {
            addLast(data);
            return;
        }
        if (index == 0) {
            Node newNode = new Node(null, data, head);
            head.prev = newNode;
            head = newNode;
        } else {
            Node prevNode = getNode(index - 1);
            Node newNode = new Node(prevNode, data, prevNode.next);
            prevNode.next.prev = newNode;
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        Node node = getNode(index);
        T oldValue = node.data;
        node.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        final Node node = getNode(index);
        final T removedValue = node.data;
        unlink(node);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        for (Node current = head; current != null; current = current.next) {
            if ((object == null && current.data == null) || (current.data != null
                    && current.data.equals(object))) {
                unlink(current);
                return true;
            }
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
        private Node prev;
        private Node next;

        public Node(Node prev, T data, Node next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    private void addLast(T data) {
        Node newNode = new Node(tail, data, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index - " + index
                    + " is incorrect. Check Your index");
        }
        Node current;
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

    private void unlink(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
    }
}
