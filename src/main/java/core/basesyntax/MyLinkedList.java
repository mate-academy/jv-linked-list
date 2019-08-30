package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> left = tail;
        Node<T> node = new Node<T>(value, null, left);
        tail = node;
        if (left == null) {
            head = node;
        } else {
            left.next = node;
        }
        ++size;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, getNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).item;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = getNode(index);
        node.item = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return removeElement(getNode(index));
    }

    @Override
    public T remove(T t) {
        T value = null;
        if (t == null) {
            for (Node<T> node = head; node != null; node = node.next) {
                if (node.item == t || t.equals(node.item)) {
                    value = removeElement(node);
                }
            }
        } else {
            for (Node<T> node = head; node != null; node = node.next) {
                if (t.equals(node.item)) {
                    value = removeElement(node);
                }
            }
        }
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

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNode(int index) {
        if (size / 2 < index) {
            Node<T> node = head;
            for (int i = 0; i < index; ++i) {
                node = node.next;
            }
            return node;
        }
        if (index + 1 == size) {
            return tail;
        }
        Node<T> node = tail;
        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }
        return node;
    }

    private void linkBefore(T t, Node<T> endNode) {
        Node<T> prevNode = endNode.prev;
        Node<T> node = new Node<T>(t, endNode, prevNode);
        endNode.prev = node;
        if (prevNode == null) {
            head = node;
        } else {
            prevNode.next = node;
        }
        ++size;
    }

    private T removeElement(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        node.next = null;
        node.prev = null;
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
        --size;
        T element = node.item;
        node.item = null;
        return element;
    }
}
