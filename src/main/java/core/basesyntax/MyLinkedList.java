package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private Node prev;
        private T item;
        private Node next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (0 > index || index > size) {
            throw new IndexOutOfBoundsException(String.format("Index: %d is incorrect", index));
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
            size++;
        } else {
            Node<T> getNode = getNode(index);
            Node<T> prevNode = getNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, getNode);
            getNode.prev = newNode;
            prevNode.next = newNode;
            size++;
        }
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
        Node<T> node = getNode(index);
        T getNode = node.item;
        node.item = value;
        return getNode;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node.equals(head)) {
            head = node.next;
        } else if (node.equals(tail)) {
            tail = node.prev;
        }
        size--;
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        T getValue = object;
        Node<T> currentNode = head;
        int i = 0;
        while (currentNode != null) {
            if (currentNode.item == null || currentNode.item.equals(getValue)) {
                remove(i);
                return true;
            }
            currentNode = currentNode.next;
            i++;
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

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, size));
        }
        if (index <= size / 2) {
            Node<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }
}
