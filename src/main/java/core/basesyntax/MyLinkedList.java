package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
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
        checkIndex(index);
        Node<T> node = getNode(index);
        T result = node.value;
        node.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        int index = 0;
        while (node.next != null || node.equals(head)) {
            T valueNode = node.value;
            if (valueNode != null && valueNode.equals(object) || valueNode == object) {
                unlink(getNode(index));
                return true;
            }
            index++;
            node = node.next;
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
        size--;
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
