package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private Node<T> item;
    private Node<T> node;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        node = new Node<>();
        node.value = value;
        if (isEmpty()) {
            head = node;
        } else {
            item = getNode(size - 1);
            link(item, node, null);
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        item = getNode(index);
        if (item == null) {
            add(value);
        } else {
            node = new Node<>();
            node.value = value;
            link(item.prev, node, item);
            if (head.equals(item)) {
                head = node;
            }
            if (tail.equals(node)) {
                tail = node;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T nodeItem: list) {
            add(nodeItem);
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        T result;
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        node = getNode(index);
        result = node.value;
        node.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        node = getNode(index);
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
        if (head.equals(node)) {
            head = node.next;
            if (!tail.equals(node)) {
                node.next.prev = null;
            }
        } else {
            if (tail.equals(node)) {
                node.prev.next = null;
                tail = node.prev;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
        }
    }

    private void link(Node<T> prevNode, Node<T> node, Node<T> nextNode) {
        if (prevNode != null) {
            prevNode.next = node;
        }
        if (nextNode != null) {
            nextNode.prev = node;
        }
        node.prev = prevNode;
        node.next = nextNode;
    }

    public int getIndex(T value) {
        node = head;
        for (int i = 0; i < size; i++) {
            if (node.value != null && node.value.equals(value) || node.value == value) {
                return i;
            }
            if (node.next != null) {
                node = node.next;
            }
        }
        return -1;
    }

    private Node<T> getNode(int index) {
        if (index >= size || index < 0) {
            return null;
        }
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node() {
        }
    }
}
