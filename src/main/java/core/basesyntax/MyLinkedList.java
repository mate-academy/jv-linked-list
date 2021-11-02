package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> oldNode = getNode(index);
        Node<T> node = new Node<>(oldNode.prev, value, oldNode);
        if (oldNode.prev == null) {
            head = node;
        } else {
            oldNode.prev.next = node;
        }
        oldNode.prev = node;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T nodeItem : list) {
            add(nodeItem);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        T result;
        checkIndex(index);
        Node<T> node = getNode(index);
        result = node.value;
        node.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        if (node == null) {
            throw new NullPointerException();
        }
        unlink(node);
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        int index = getIndex(object);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
    }

    public int getIndex(T value) {
        Node<T> node = head;
        int i = 0;
        do {
            T valueNode = node.value;
            if (valueNode != null && valueNode.equals(value) || valueNode == value) {
                return i;
            }
            i++;
            node = node.next;
        } while (node.next != null);
        return -1;
    }

    private Node<T> getNode(int index) {
        Node<T> item;
        if (index <= size / 2) {
            item = head;
            for (int i = 0; i < index; i++) {
                item = item.next;
            }
        } else {
            item = tail;
            for (int i = size - 1; i != index; i--) {
                item = item.prev;
            }
        }
        return item;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is incorrect: " + index);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
