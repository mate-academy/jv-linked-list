package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> node = tail;
        Node<T> newNode = new Node<>(node, value);
        tail = newNode;
        if (node == null) {
            head = newNode;
        } else {
            node.next = newNode;
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
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        if (node.prev != null) {
            node.prev.next = newNode;
        } else {
            head = newNode;
        }
        node.prev = newNode;
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
        checkElementIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> node = getNode(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.item != null
                    && node.item.equals(object)
                    || node.item == object) {
                unlink(node);
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        Node(Node<T> prev, T element) {
            this.item = element;
            this.prev = prev;
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(indexOutOfBoundsMessage(index));
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(indexOutOfBoundsMessage(index));
        }
    }

    private String indexOutOfBoundsMessage(int index) {
        return "Index: " + index + ", Size: " + size;
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
        size--;
    }

    private Node<T> getNode(int index) {
        Node<T> node;
        if (index <= size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }
}
