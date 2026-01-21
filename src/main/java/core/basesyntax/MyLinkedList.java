package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);

        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index + ", size: " + size
            );
        }

        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> nodeAtIndex = node(index);
            Node<T> newNode = new Node<>(nodeAtIndex.prev, value, nodeAtIndex);
            nodeAtIndex.prev.next = newNode;
            nodeAtIndex.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T e : list) {
            add(e);
        }
    }

    @Override
    public T get(int index) {
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index + ", valid range is 0 to " + size
            );
        }

        Node<T> nodeAtIndex = node(index);
        T oldItem = nodeAtIndex.item;
        nodeAtIndex.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> searchNode = head;

        while (searchNode != null) {
            if (object == null ? searchNode.item == null
                    : object.equals(searchNode.item)) {
                unlink(searchNode);
                return true;
            } else {
                searchNode = searchNode.next;
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

    private Node<T> node(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index + ", valid range is 0 to " + size
            );
        }

        Node<T> node;

        if (index < size / 2) {
            node = head;

            for (int i = 0; i < index; i++) {
                node = node.next;
            }

            return node;
        } else {
            node = tail;

            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }

            return node;
        }
    }

    private T unlink(Node<T> node) {
        final T item = node.item;
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }

        size--;
        return item;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
