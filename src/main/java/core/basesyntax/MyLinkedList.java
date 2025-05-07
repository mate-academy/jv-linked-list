package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = newNode;
        }
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == 0 && index == 0 || index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        Node<T> requestedNode = getNode(index);
        link(requestedNode, newNode);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> requestedNode = getNode(index);
        T oldItem = requestedNode.item;
        requestedNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> requestedNode = getNode(index);
        unlink(requestedNode);
        size--;
        return requestedNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> requestedNode = head;
        for (int i = 0; i < size; i++) {
            if (requestedNode.item == null || requestedNode.item.equals(object)) {
                remove(i);
                return true;
            }
            requestedNode = requestedNode.next;
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

    private void link(Node<T> requestedNode, Node<T> newNode) {
        if (requestedNode.prev != null) {
            requestedNode.prev.next = newNode;
        }
        newNode.prev = requestedNode.prev;
        requestedNode.prev = newNode;
        newNode.next = requestedNode;
        if (newNode.prev == null) {
            head = newNode;
        }
    }

    private void unlink(Node<T> node) {
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
    }

    private Node<T> getNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> requestedNode;
        if (index <= size / 2) {
            requestedNode = head;
            for (int i = 0; i < index; i++) {
                requestedNode = requestedNode.next;
            }
        } else {
            requestedNode = tail;
            for (int i = size - 1; i > index; i--) {
                requestedNode = requestedNode.prev;
            }
        }
        return requestedNode;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
